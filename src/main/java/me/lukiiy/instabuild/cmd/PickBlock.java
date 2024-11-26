package me.lukiiy.instabuild.cmd;

import me.lukiiy.instabuild.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class PickBlock implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("§cThis command can only be used by players.");
            return true;
        }
        Player p = (Player) commandSender;

        Block b = p.getTargetBlock(Utils.ignoreAir(), 5);
        if (b == null || b.getType() == Material.AIR) {
            p.sendMessage("§cYou are not targeting a valid block.");
            return true;
        }

        Material material = b.getType();

        ItemStack item = new ItemStack(material, material.getMaxStackSize(), (short) 0, b.getData());
        p.getInventory().addItem(item);

        MaterialData mData = item.getData();
        byte data = (mData != null) ? mData.getData() : 0;
        p.sendMessage("§eGot §f" + Utils.formattedCoolID(item.getType(), data, item.getAmount()));
        return true;
    }
}