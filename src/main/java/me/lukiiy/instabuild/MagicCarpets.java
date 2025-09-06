package me.lukiiy.instabuild;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MagicCarpets {
    private final Map<Player, Set<Block>> carpets = new HashMap<>();
    private final Material block = Material.GLASS;
    private final int size = 1;

    public void create(Player p, int yOffset) {
        Location baseLoc = p.getLocation().clone().add(0, yOffset - 1, 0);
        Block center = baseLoc.getBlock();

        remove(p);

        Set<Block> placed = new HashSet<>();
        IntStream.rangeClosed(-size, size).forEach(x ->
                IntStream.rangeClosed(-size, size).forEach(z -> {
                    Block b = center.getRelative(x, 0, z);

                    if (b.getType() == Material.AIR) {
                        b.setType(block);
                        placed.add(b);
                    }
                })
        );

        carpets.put(p, placed);
    }

    public void remove(Player p) {
        Set<Block> old = carpets.remove(p);
        if (old == null || old.isEmpty()) return;

        for (Block b : old) if (b.getType() == Material.GLASS) b.setType(Material.AIR);
    }

    public boolean has(Player p) {
        return carpets.containsKey(p);
    }

    public Set<Block> getCarpet(Player p) {
        if (!has(p)) return new HashSet<>();

        return carpets.get(p);
    }

    public Set<Block> getAllCarpets() {
        return carpets.values().stream().flatMap(Set::stream).collect(Collectors.toSet());
    }
}
