package me.lukiiy.instabuild.listeners;

import me.lukiiy.instabuild.Instabuild;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityTargetEvent;

public class EntityEcho extends EntityListener {
    @Override
    public void onEntityDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;

        Player p = (Player) e.getEntity();
        if (!Instabuild.getInstance().getBuilders().contains(p)) return;

        e.setCancelled(true);
    }

    @Override
    public void onEntityTarget(EntityTargetEvent e) {
        if (!(e.getTarget() instanceof Player)) return;

        Player p = (Player) e.getTarget();
        if (!Instabuild.getInstance().getBuilders().contains(p)) return;

        e.setCancelled(true);
    }
}
