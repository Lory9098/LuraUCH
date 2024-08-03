package me.nettychannell.lurauhc.hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.nettychannell.lurauhc.arena.Arena;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class PlaceholderAPIHook extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "uhc";
    }

    @Override
    public @NotNull String getAuthor() {
        return "NettyChannell";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) {
            return "";
        }

        switch (params) {
            case "remaining_players" -> {
                return String.valueOf(Arena.getInstance().getRemainingPlayers());
            }
            case "starting_seconds" -> {
                long exceptedStartTime = Arena.getInstance().getExceptedStartTime();

                if (exceptedStartTime == -101) {
                    return String.valueOf(Arena.getInstance().getCountdownSeconds());
                }

                return String.valueOf(TimeUnit.MILLISECONDS.toSeconds(exceptedStartTime - System.currentTimeMillis()));
            }
            case "pvp_time" -> {
                long pvpStartTime = Arena.getInstance().getPvpStartTime();

                if (pvpStartTime == -101) {
                    return "0";
                }

                return String.valueOf(TimeUnit.MILLISECONDS.toSeconds(pvpStartTime - System.currentTimeMillis()) + 1);
            }
            case "max_players" -> {
                return String.valueOf(Arena.getInstance().getMaxPlayers());
            }
            case "kills" -> {
                return String.valueOf(Arena.getInstance().getKills(player.getUniqueId()));
            }
            case "border" -> {
                return String.valueOf(Arena.getInstance().getWorld().getWorldBorder().getSize());
            }
        }

        return "";
    }

}