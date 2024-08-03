package me.nettychannell.lurauhc;


import co.aikar.commands.BukkitCommandManager;
import lombok.Getter;
import me.nettychannell.lurauhc.arena.Arena;
import me.nettychannell.lurauhc.commands.StartCMD;
import me.nettychannell.lurauhc.commands.UHCCmd;
import me.nettychannell.lurauhc.config.Config;
import me.nettychannell.lurauhc.crafting.CustomCraftings;
import me.nettychannell.lurauhc.hooks.PlaceholderAPIHook;
import me.nettychannell.lurauhc.listeners.GameListeners;
import me.nettychannell.lurauhc.scoreboard.LuraUHCScoreboard;
import me.nettychannell.lurauhc.team.TeamColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;

@Getter
public final class LuraUHC extends JavaPlugin { // I miei fan mi chiamano Surry

    @Getter
    private static LuraUHC instance;
    private Config scoreboardConfig;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        this.scoreboardConfig = new Config(this, "scoreboard.yml");

        World world = Bukkit.getWorld(getConfig().getString("arena.world"));

        world.getWorldBorder().setSize(500);

        HashMap<TeamColor, Location> spawns = new HashMap<>();

        getConfig().getConfigurationSection("arena.spawns").getKeys(false).forEach(color -> {
            spawns.put(
                    TeamColor.valueOf(color.toUpperCase()),
                    new Location(
                            world,
                            getConfig().getDouble("arena.spawns." + color + ".x"),
                            getConfig().getDouble("arena.spawns." + color + ".y"),
                            getConfig().getDouble("arena.spawns." + color + ".z"),
                            (float) getConfig().getDouble("arena.spawns." + color + ".yaw"),
                            (float) getConfig().getDouble("arena.spawns." + color + ".pitch")
                    )
            );
        });

        Location spawn = new Location(
                world,
                getConfig().getDouble("arena.spawn.x"),
                getConfig().getDouble("arena.spawn.y"),
                getConfig().getDouble("arena.spawn.z"),
                (float) getConfig().getDouble("arena.spawn.yaw"),
                (float) getConfig().getDouble("arena.spawn.pitch")
        );

        new Arena(
                world,
                getConfig().getInt("arena.max-players"),
                getConfig().getInt("arena.countdown-seconds"),
                spawns,
                spawn
        );

        new PlaceholderAPIHook().register();

        new LuraUHCScoreboard(this).runTaskTimer(this, 0, 10);

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        CustomCraftings.registerCraftings();

        BukkitCommandManager commandManager = new BukkitCommandManager(this);

        List.of(
                new StartCMD(),
                new UHCCmd()
        ).forEach(commandManager::registerCommand);

        List.of(
                new GameListeners()
        ).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));

    }

    @Override
    public void onDisable() {
        getServer().getMessenger().unregisterOutgoingPluginChannel(this, "BungeeCord");
    }
}
