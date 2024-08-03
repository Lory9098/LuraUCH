package me.nettychannell.lurauhc.listeners;

import me.nettychannell.lurauhc.LuraUHC;
import me.nettychannell.lurauhc.arena.Arena;
import me.nettychannell.lurauhc.arena.state.GameState;
import me.nettychannell.lurauhc.utils.IngotUtils;
import me.nettychannell.lurauhc.utils.WorldUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class GameListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        Arena arena = Arena.getInstance();

        if (!arena.isJoinable()) {
            player.kick(Component.text("Il gioco è già iniziato"));
            return;
        }

        arena.addPlayer(player);
        player.getInventory().clear();
        player.setHealth(20);
        player.setFoodLevel(20);
        player.teleport(arena.getSpawn());
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.showPlayer(LuraUHC.getInstance(), player);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();

        Arena arena = Arena.getInstance();

        if (arena.getGameState() != GameState.INGAME) {
            e.setCancelled(true);
            return;
        }

        Block block = e.getBlock();

        if (block.getType().name().contains("LEAVES")) {
            Random random = new Random();
            int chance = random.nextInt(100);
            if (chance < 30) {
                player.getInventory().addItem(new ItemStack(Material.APPLE));
            }
            return;
        }

        switch (block.getType()) {
            case GRASS -> {
                player.getInventory().addItem(new ItemStack(Material.WHEAT));
            }
            case SAND -> {
                player.getInventory().addItem(new ItemStack(Material.GLASS));
            }
            case GRAVEL -> {
                player.getInventory().addItem(new ItemStack(Material.ARROW));
            }
            case IRON_ORE, GOLD_ORE, DIAMOND_ORE, EMERALD_ORE -> {
                player.getInventory().addItem(new ItemStack(IngotUtils.getIngot(block.getType()), 3));
            }
            case REDSTONE_ORE, LAPIS_ORE -> {
                int amount;
                Random random = new Random();
                amount = random.nextInt(5) + 8;
                player.getInventory().addItem(new ItemStack(IngotUtils.getIngot(block.getType()), amount));
                player.setLevel(player.getLevel() + 3);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Arena arena = Arena.getInstance();

        if (arena.getGameState() != GameState.INGAME) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDie(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player player)) {
            return;
        }

        if (!(e.getDamager() instanceof Player damager)) {
            return;
        }

        if (!Arena.getInstance().isPvPEnabled()) {
            e.setCancelled(true);
            return;
        }

        if (player.getHealth() - e.getFinalDamage() <= 0) {
            handleDie(e, player, damager);
        }
    }

    @EventHandler
    public void onPlayerDie(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player player)) {
            return;
        }

        if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            return;
        }

        if (!Arena.getInstance().isPvPEnabled()) {
            e.setCancelled(true);
            return;
        }

        if (player.getHealth() - e.getFinalDamage() <= 0) {
            handleDie(e, player, null);
        }
    }

    public void handleDie(Cancellable e, Player player, Player damager) {
        e.setCancelled(true);

        Arena arena = Arena.getInstance();

        if (arena.getGameState() != GameState.INGAME) {
            player.teleport(Arena.getInstance().getSpawn());
            return;
        }

        arena.setSpectator(player.getUniqueId());

        if (damager != null) arena.addKill(damager.getUniqueId());

        player.setHealth(20);
        player.teleport(Arena.getInstance().getSpawn());
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.hidePlayer(LuraUHC.getInstance(), player);
        }
        player.setAllowFlight(true);

        if (arena.getRemainingPlayers() == 1) {
            arena.setGameState(GameState.ENDING);
            Player winner = arena.getWinner();

            if (winner == null) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Bukkit.getServer().shutdown();
                    }
                }.runTaskLater(LuraUHC.getInstance(), 20);
                throw new IllegalStateException("Winner is null, lol");
            }
            AtomicInteger count = new AtomicInteger(0);

            new BukkitRunnable() {
                @Override
                public void run() {
                    count.getAndIncrement();
                    if (count.get() == 12) {
                        // TODO: Send everyone to lobby
                        Bukkit.getServer().shutdown();
                        cancel();
                    } else {
                        WorldUtils.spawnFireworks(winner.getLocation(), 5);
                    }
                }
            }.runTaskTimer(LuraUHC.getInstance(), 1, 30);
        }
    }
}
