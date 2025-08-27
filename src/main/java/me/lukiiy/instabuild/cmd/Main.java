package me.lukiiy.instabuild.cmd;

import me.lukiiy.instabuild.Instabuild;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Main implements CommandExecutor {
    private static final Map<String, String> subCmds = new LinkedHashMap<>();

    static {
        subCmds.put("list", "Lists players using LNB");
        subCmds.put("getid", "Gets the ID for the targeted block and the item you're holding");
        subCmds.put("listids", "Lists every item's id");
        subCmds.put("carpet", "Toggle flying carpet");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        List<Player> builders = Instabuild.getInstance().getBuilders();
        String arg = strings.length < 1 ? " " : strings[0].toLowerCase();

        switch (arg) {
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
                Arrays.stream(Material.values()).forEach(m -> commandSender.sendMessage(m.name() + " (" + m.getId() + ")"));
                return true;
            }

            case "carpet": {
                if (!(commandSender instanceof Player)) {
                    commandSender.sendMessage("§cOnly in-game players can toggle the magic carpet!");
                    return true;
                }
                Player p = (Player) commandSender;
                Instabuild instabuild = Instabuild.getInstance();

                if (!instabuild.carpets.has(p)) {
                    instabuild.carpets.create(p, 0);
                    p.sendMessage("Magic Carpet §aenabled§f!");
                    return true;
                }

                instabuild.carpets.remove(p);
                p.sendMessage("Magic Carpet §cdisabled§f!");
                return true;
            }

            case "help": {
                commandSender.sendMessage("§eInstabuild Subcommands:");
                subCmds.forEach((cmd, desc) -> commandSender.sendMessage(" §e" + cmd + "§f -> §7" + desc));
                return true;
            }

            default: {
                if (!(commandSender instanceof Player)) {
                    commandSender.sendMessage("§cOnly in-game players can toggle Instabuild mode!");
                    return true;
                }
                Player p = (Player) commandSender;

                if (!builders.contains(p)) {
                    builders.add(p);
                    p.setSleepingIgnored(true);
                    p.sendMessage("Instabuild §aenabled§f!");
                    return true;
                }

                builders.remove(p);
                p.setSleepingIgnored(false);
                p.sendMessage("Instabuild §cdisabled§f!");
                return true;
            }
        }
    }
}
