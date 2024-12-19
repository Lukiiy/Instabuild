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
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("§cThis command can only be used by players.");
            return true;
        }
        Player p = (Player) commandSender;

        if (strings.length == 0) {
            commandSender.sendMessage("§cInvalid use. /ibg <id:data> [amount] or /ibg <id> <data> [amount]");
            return true;
        }

        int id;
        byte data = 0;
        int amount;

        try {
            if (strings[0].contains(":")) {
                String[] parts = strings[0].split(":");
                id = Integer.parseInt(parts[0]);
                data = Byte.parseByte(parts[1]);
            } else id = Integer.parseInt(strings[0]);

            amount = (strings.length > 1) ? Integer.parseInt(strings[1]) : Material.getMaterial(id).getMaxStackSize();
        } catch (NumberFormatException | NullPointerException e) {
            commandSender.sendMessage("§cInvalid use. /ibg <id:data> [amount] or /ibg <id> <data> [amount]");
            return true;
        }

        if (amount <= 0) {
            commandSender.sendMessage("§cInvalid amount. Must be greater than 0.");
            return true;
        }

        ItemStack item = new ItemStack(Material.getMaterial(id), amount, (short) 0, data);
        p.getInventory().addItem(item);
        p.updateInventory();
        p.sendMessage("§eGot §f" + Utils.formattedCoolID(item.getType(), item.getData().getData(), item.getAmount()));
        return true;
    }
}
