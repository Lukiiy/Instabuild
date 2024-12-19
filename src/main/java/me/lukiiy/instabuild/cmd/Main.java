package me.lukiiy.instabuild.cmd;

import me.lukiiy.instabuild.Instabuild;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Main implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        List<Player> builders = Instabuild.getInstance().getBuilders();

        switch (strings.length == 0 ? " " : strings[0].toLowerCase()) {
            case "list": {
                if (builders.isEmpty()) {
                    commandSender.sendMessage("§cNo instabuilders active.");
                    return true;
                }

                commandSender.sendMessage("§aInstabuilders:");
                builders.forEach(b -> commandSender.sendMessage(". " + b.getName()));
                return true;
            }
            case "getid": {
                if (!(commandSender instanceof Player)) return false;
                Player p = (Player) commandSender;

                Block b = p.getTargetBlock(new HashSet<>(), 5);
                p.sendMessage("§eTarget block: §f" + b.getType().name() + " (" + b.getTypeId() + ":" + b.getData() + ")");

                ItemStack hand = p.getItemInHand();
                if (hand != null) p.sendMessage("§eHand item: §f" + hand.getType().name() + " (" + hand.getTypeId() + ":" + hand.getData() + ")");
                return true;
            }
            case "listids": {
                commandSender.sendMessage("§aAvailable Item & IDs:");
                Arrays.stream(Material.values()).forEach(m -> {
                    commandSender.sendMessage(m.name() + " (" + m.getId() + ")");
                });
                return true;
            }
        }

        if (!(commandSender instanceof Player)) return false;
        Player p = (Player) commandSender;

        if (!builders.contains(p)) {
            p.sendMessage("Instabuild §aenabled§f!");
            builders.add(p);
        } else {
            p.sendMessage("Instabuild §cdisabled§f!");
            builders.remove(p);
        }
        return true;
    }
}
