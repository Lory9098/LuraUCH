package me.nettychannell.lurauhc.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import me.nettychannell.lurauhc.arena.Arena;
import me.nettychannell.lurauhc.arena.state.GameState;
import org.bukkit.entity.Player;

@CommandAlias("start")
public class StartCMD extends BaseCommand {

    @Default
    public void onStart(Player player) {
        if (!player.hasPermission("lurauhc.start")) {
            return;
        }

        if (Arena.getInstance().getGameState() != GameState.WAITING) {
            player.sendMessage("Gioco già iniziato");
            return;
        }

        if (Arena.getInstance().getTeams().size() < 2) {
            player.sendMessage("Non ci sono abbastanza giocatori");
            return;
        }

        Arena.getInstance().startCountdown();
    }

}
