package me.lukiiy.instabuild.listeners;

import me.lukiiy.instabuild.Instabuild;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BlockEcho extends BlockListener {
    @Override
    public void onBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();

        if (!Instabuild.getInstance().getBuilders().contains(p)) return;

        ItemStack item = p.getItemInHand();
        if (item.getAmount() > 0) item.setAmount(item.getAmount() + 1);
        else p.getInventory().addItem(item.clone());

        Block b = e.getBlockPlaced();
        if (b.getType() == Material.LEAVES) b.setData((byte) (b.getData() - 8));
    }
}
