package me.lukiiy.instabuild.cmd;

import me.lukiiy.instabuild.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Give implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by players.");
            return true;
        }

        String usage = "§cInvalid use. /ibg <id:data|material[:data]> [amount]";
        if (args.length == 0) {
            sender.sendMessage(usage);
            return true;
        }

        try {
            String[] parts = args[0].split(":");
            String materialPart = parts[0];
            byte data = parts.length > 1 ? Byte.parseByte(parts[1]) : 0;

            Material material = Material.getMaterial(materialPart.toUpperCase());
            if (material == null) material = Material.getMaterial(Integer.parseInt(materialPart));

            if (material == null) {
                sender.sendMessage("§cInvalid material or ID.");
                return true;
            }

            int amount = args.length > 1 ? Integer.parseInt(args[1]) : material.getMaxStackSize();
            if (amount <= 0) {
                sender.sendMessage("§cInvalid amount. Must be greater than 0.");
                return true;
            }

            Player p = (Player) sender;
            ItemStack item = new ItemStack(material, amount, (short) 0, data);

            p.sendMessage("§aGot §f" + Utils.formattedCoolID(item.getType(), data, item.getAmount()));
            p.getInventory().addItem(item);
            p.updateInventory();
        } catch (NumberFormatException e) {
            sender.sendMessage(usage);
        }

        return true;
    }
}
