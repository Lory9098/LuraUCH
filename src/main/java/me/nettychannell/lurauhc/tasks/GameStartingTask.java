package me.nettychannell.lurauhc.tasks;

import lombok.AllArgsConstructor;
import me.nettychannell.lurauhc.arena.Arena;
import me.nettychannell.lurauhc.utils.ChatUtil;
import org.bukkit.scheduler.BukkitRunnable;

@AllArgsConstructor
public class GameStartingTask extends BukkitRunnable {
    private final Arena arena;
    private int i;

    @Override
    public void run() {
        if (i == 0) {
            arena.start();
            arena.broadcast(ChatUtil.colorComponent("&aIl gioco è iniziato!"));
            cancel();
            return;
        }

        if (i % 10 == 0 || i <= 5) {
            arena.broadcast(ChatUtil.colorComponent("&aIl gioco inizierà tra " + i + " secondi"));
        }

        i--;
    }
}
