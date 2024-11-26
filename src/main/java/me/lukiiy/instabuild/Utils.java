package me.lukiiy.instabuild;

import org.bukkit.Material;

import java.util.Collections;
import java.util.HashSet;

public class Utils {
    public static String formattedCoolID(Material material, byte data, int amount) {
        return amount == 0 ? "" : amount + "x " + material.name() + " (" + material.getId() + ":" + data + ")";
    }

    public static HashSet<Byte> ignoreAir() {return new HashSet<>(Collections.singleton((byte) 0));}
}
