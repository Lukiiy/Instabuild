package me.lukiiy.instabuild.listeners;

import me.lukiiy.instabuild.Instabuild;
import me.lukiiy.instabuild.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

public class PlayerEcho extends PlayerListener {
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        Instabuild.getInstance().getBuilders().remove(e.getPlayer());
        Instabuild.getInstance().carpets.remove(p);
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (!Instabuild.getInstance().getBuilders().contains(p)) return;

        if (!e.isBlockInHand()) {
            if (e.getItem() == null) return;

            ItemStack hand = e.getItem();
            hand.setDurability((short) 0);

            switch (hand.getType()) {
                case BOW:
                    e.setUseItemInHand(Event.Result.DENY);
                    e.setCancelled(true);
                    p.shootArrow();
                    break;

                case COMPASS:
                    e.setUseItemInHand(Event.Result.DENY);
                    e.setCancelled(true);

                    Block target = p.getTargetBlock(Utils.ignoreAirAndLiquids(), 128);
                    if (target.isEmpty()) return;

                    Block above = target.getRelative(BlockFace.UP);

                    if (!above.isEmpty()) {
                        World world = target.getWorld();
                        int maxHeight = world.getMaxHeight();
                        Block current = target;

                        while (current.getY() < maxHeight - 1) {
                            Block next = current.getRelative(BlockFace.UP);
                            if (!next.isEmpty()) break;

                            above = next;
                            current = next;
                        }
                    }

                    if (!above.isEmpty()) return;

                    Location loc = above.getLocation().add(0.5, 0, 0.5);

                    loc.setPitch(p.getLocation().getPitch());
                    loc.setYaw(p.getLocation().getYaw());

                    p.teleport(loc);
                    return;

                case EGG:
                case SNOW_BALL:
                    if (hand.getAmount() == hand.getMaxStackSize()) return;

                    p.getInventory().addItem(new ItemStack(hand.getType(), 1));
                    break;
            }

            p.updateInventory();
            return;
        }

        if (e.getAction() == Action.LEFT_CLICK_BLOCK && e.getClickedBlock() != null) {
            if (e.getItem().getType().name().contains("SWORD") || Instabuild.getInstance().carpets.getAllCarpets().contains(e.getClickedBlock())) return;

            e.setUseInteractedBlock(Event.Result.DENY);
            e.setUseItemInHand(Event.Result.DENY);
            e.setCancelled(true);
            e.getClickedBlock().setType(Material.AIR);
        }
    }

    @Override
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Instabuild instabuild = Instabuild.getInstance();

        if (!instabuild.carpets.has(p)) return;

        Location from = e.getFrom();
        Location to = e.getTo();

        if (from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ()) {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(instabuild, () -> instabuild.carpets.create(p, 0));
        }
    }

    @Override
    public void onPlayerToggleSneak(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();
        Instabuild instabuild = Instabuild.getInstance();

        if (!e.isSneaking() || !instabuild.carpets.has(p)) return;

        instabuild.carpets.create(p, -1);
    }
}
