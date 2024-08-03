package me.nettychannell.lurauhc.scoreboard;

import lombok.AllArgsConstructor;
import me.clip.placeholderapi.PlaceholderAPI;
import me.nettychannell.lurauhc.LuraUHC;
import me.nettychannell.lurauhc.arena.Arena;
import me.nettychannell.lurauhc.arena.state.GameState;
import me.nettychannell.lurauhc.utils.ChatUtil;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.scoreboard.Scoreboard;
import me.neznamy.tab.api.scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class LuraUHCScoreboard extends BukkitRunnable {
    private final LuraUHC instance;

    public void update(Player player) {
        TabPlayer tabPlayer = TabAPI.getInstance().getPlayer(player.getUniqueId());

        if (tabPlayer == null) {
            return;
        }

        Arena arena = Arena.getInstance();

        ConfigurationSection scoreboardSection = instance.getScoreboardConfig().getFileConfiguration().getConfigurationSection("scoreboards");
        GameState state = arena.getGameState();

        ConfigurationSection section = (state == GameState.WAITING || state == GameState.STARTING) ? scoreboardSection.getConfigurationSection("waiting") : scoreboardSection.getConfigurationSection("game");

        String title = section.getString("title");
        List<String> lines = section.getStringList("lines");

        if (title == null) {
            return;
        }

        title = PlaceholderAPI.setPlaceholders(player, title);
        lines = PlaceholderAPI.setPlaceholders(player, lines);

        String date = new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis());

        lines = lines.stream().map(line -> line.replace("%date%", date).replace("%player_name%", player.getName())).toList();

        ScoreboardManager scoreboardManager = TabAPI.getInstance().getScoreboardManager();
        Scoreboard scoreboard = scoreboardManager.createScoreboard(
                "scoreboard",
                ChatUtil.color(title),
                ChatUtil.color(lines)
        );
        scoreboardManager.showScoreboard(tabPlayer, scoreboard);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            update(player);
        }
    }
}
