package me.nettychannell.lurauhc.arena;

import lombok.Data;
import lombok.Getter;
import me.nettychannell.lurauhc.LuraUHC;
import me.nettychannell.lurauhc.arena.state.GameState;
import me.nettychannell.lurauhc.tasks.GameStartingTask;
import me.nettychannell.lurauhc.team.TeamColor;
import me.nettychannell.lurauhc.utils.ChatUtil;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Data
public class Arena {

    @Getter
    private static Arena instance;
    private final int maxPlayers, countdownSeconds;
    private GameState gameState = GameState.WAITING;
    private final World world;
    private final HashMap<TeamColor, Set<Player>> teams = new HashMap<>();
    private final HashMap<TeamColor, Location> spawns;
    private long exceptedStartTime = -101, pvpStartTime = -101;
    private final HashMap<UUID, Integer> kills = new HashMap<>();
    private final Location spawn;
    private final List<UUID> spectators = new ArrayList<>();

    public Arena(World world, int maxPlayers, int countdownSeconds, HashMap<TeamColor, Location> spawns, Location spawn) {
        instance = this;
        this.maxPlayers = maxPlayers;
        this.countdownSeconds = countdownSeconds;
        this.world = world;
        this.spawns = spawns;
        this.spawn = spawn;
    }

    public void addPlayer(Player player) {
        if (teams.size() == 0) {
            teams.put(TeamColor.RED, Set.of(player));

            System.out.println(teams);
            return;
        }

        if (teams.size() < spawns.size()) {
            teams.put(TeamColor.values()[teams.size()], Set.of(player));

            System.out.println(teams);
            return;
        }

        TeamColor team = TeamColor.RED;
        int players = teams.get(team).size();

        for (TeamColor color : teams.keySet()) {
            if (teams.get(color).size() < players) {
                team = color;
                players = teams.get(color).size();
            }
        }

        teams.get(team).add(player);
    }

    public void start() {
        gameState = GameState.INGAME;
        pvpStartTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(15);

        teams.forEach((color, players) -> {
            players.forEach(player -> {
                char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
                char character = letters[color.ordinal()];
                String characterString = String.valueOf(character);
                player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                TabPlayer tabPlayer = TabAPI.getInstance().getPlayer(player.getUniqueId());
                player.setPlayerListName(ChatUtil.color("&" + color.getColorCode() + characterString + " " + player.getName()));

                player.teleport(spawns.get(color));
            });
        });

        new BukkitRunnable() {
            @Override
            public void run() {
                world.getWorldBorder().setSize(1, TimeUnit.MINUTES, 1);
            }
        }.runTaskLater(LuraUHC.getInstance(), 6000);
    }

    public int getRemainingPlayers() {
        Set<Player> remainingPlayers = getTeams().values().stream().reduce(new HashSet<>(), (players, team) -> {
            players.addAll(team);
            return players;
        });

        return (int) remainingPlayers.stream().filter(player -> !isSpectator(player.getUniqueId())).count();
    }

    public int getKills(UUID uuid) {
        return kills.getOrDefault(uuid, 0);
    }

    public void setKills(UUID uuid, int kills) {
        this.kills.put(uuid, kills);
    }

    public void addKill(UUID uuid) {
        kills.put(uuid, kills.getOrDefault(uuid, 0) + 1);
    }

    public boolean isJoinable() {
        return (gameState == GameState.WAITING || gameState == GameState.STARTING) && getRemainingPlayers() < maxPlayers;
    }

    public void setSpectator(UUID uuid) {
        spectators.add(uuid);
    }

    public boolean isSpectator(UUID uuid) {
        return spectators.contains(uuid);
    }

    public Player getWinner() {
        if (getRemainingPlayers() != 1) {
            return null;
        }
        return teams.values().stream().reduce(new HashSet<>(), (players, team) -> {
            players.addAll(team);
            return players;
        }).stream().findFirst().orElse(null);
    }

    public void startCountdown() {
        exceptedStartTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(countdownSeconds);
        setGameState(GameState.STARTING);
        new GameStartingTask(this, countdownSeconds).runTaskTimer(LuraUHC.getInstance(), 0, 20);
    }

    public void broadcast(Component component) {
        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(component));
    }

    public boolean isPvPEnabled() {
        return pvpStartTime != -101 && System.currentTimeMillis() >= pvpStartTime;
    }

}
