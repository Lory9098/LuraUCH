package me.nettychannell.lurauhc.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import me.nettychannell.lurauhc.utils.ChatUtil;
import org.bukkit.entity.Player;

@CommandAlias("uhc")
public class UHCCmd extends BaseCommand {

    @Default
    public void onDefault(Player player) {
        player.sendMessage(ChatUtil.colorComponent("&aStai giocando su LuraUHC Sviluppate da &b@HikariConfig"));
    }

}
