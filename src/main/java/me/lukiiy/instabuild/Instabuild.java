package me.lukiiy.instabuild;

import me.lukiiy.instabuild.cmd.Give;
import me.lukiiy.instabuild.cmd.Main;
import me.lukiiy.instabuild.cmd.PickBlock;
import me.lukiiy.instabuild.listeners.BlockEcho;
import me.lukiiy.instabuild.listeners.EntityEcho;
import me.lukiiy.instabuild.listeners.PlayerEcho;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Instabuild extends JavaPlugin {
    private static Instabuild instance;

    private final List<Player> builders = new ArrayList<>();
    public final MagicCarpets carpets = new MagicCarpets();

    @Override
    public void onEnable() {
        instance = this;

        getCommand("instabuild").setExecutor(new Main());
        getCommand("pickblock").setExecutor(new PickBlock());
        getCommand("instabuildgive").setExecutor(new Give());

        PluginManager pm = getServer().getPluginManager();
        PlayerEcho pListener = new PlayerEcho();
        BlockEcho bListener = new BlockEcho();
        EntityEcho eListener = new EntityEcho();

        pm.registerEvent(Event.Type.PLAYER_QUIT, pListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_INTERACT, pListener, Event.Priority.Lowest, this);
        pm.registerEvent(Event.Type.PLAYER_MOVE, pListener, Event.Priority.Lowest, this);
        pm.registerEvent(Event.Type.PLAYER_TOGGLE_SNEAK, pListener, Event.Priority.Lowest, this);
        pm.registerEvent(Event.Type.BLOCK_PLACE, bListener, Event.Priority.Lowest, this);
        pm.registerEvent(Event.Type.ENTITY_DAMAGE, eListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_TARGET, eListener, Event.Priority.Normal, this);
    }

    @Override
    public void onDisable() {}

    public static Instabuild getInstance() {
        return instance;
    }

    public List<Player> getBuilders() {
        return builders;
    }
}
