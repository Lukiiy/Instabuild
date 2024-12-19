package me.lukiiy.instabuild.listeners;

import me.lukiiy.instabuild.Instabuild;
import me.lukiiy.instabuild.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("deprecation")
public class PlayerEcho extends PlayerListener {
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        Instabuild.getInstance().getBuilders().remove(p);
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (Instabuild.getInstance().getBuilders().contains(p)) {
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

                        Block main = p.getTargetBlock(Utils.ignoreAirAndLiquids(), 32);
                        if (main.isEmpty()) return;

                        Block above = main.getRelative(BlockFace.UP);
                        Location loc = above.getLocation().add(.5, 0, .5);
                        loc.setPitch(p.getLocation().getPitch());
                        loc.setYaw(p.getLocation().getYaw());

                        if (above.isEmpty()) p.teleport(loc);
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
                if (e.getItem().getType().name().contains("SWORD")) return;
                e.setUseInteractedBlock(Event.Result.DENY);
                e.setUseItemInHand(Event.Result.DENY);
                e.setCancelled(true);
                e.getClickedBlock().setType(Material.AIR);
            }
        }
    }
}
