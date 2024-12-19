package me.lukiiy.instabuild;

import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashSet;

public class Utils {
    public static String formattedCoolID(Material material, byte data, int amount) {
        return amount == 0 ? "" : amount + "x " + material.name() + " (" + material.getId() + ":" + data + ")";
    }

    public static HashSet<Byte> ignoreAirAndLiquids() {
        return new HashSet<>(Arrays.asList((byte) 0, (byte) 8, (byte) 9, (byte) 10, (byte) 11));
    }
}
