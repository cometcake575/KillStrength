package com.starshooterstudios.killstrength.systems;

import com.starshooterstudios.killstrength.Main;
import com.starshooterstudios.killstrength.System;
import org.bukkit.NamespacedKey;
import org.bukkit.potion.PotionEffectType;

public class SpeedSystem extends System {
    @Override
    public NamespacedKey getDataKey() {
        return new NamespacedKey(Main.getInstance(), "speed-num");
    }

    @Override
    public NamespacedKey getEffectKey() {
        return new NamespacedKey(Main.getInstance(), "speed");
    }

    @Override
    public PotionEffectType getStrongEffectType() {
        return PotionEffectType.SPEED;
    }

    @Override
    public PotionEffectType getWeakEffectType() {
        return PotionEffectType.SLOWNESS;
    }

    @Override
    public String getSystemName() {
        return "speed";
    }
}
