package com.starshooterstudios.killstrength.systems;

import com.starshooterstudios.killstrength.Main;
import com.starshooterstudios.killstrength.System;
import org.bukkit.NamespacedKey;
import org.bukkit.potion.PotionEffectType;

public class StrengthSystem extends System {
    @Override
    public NamespacedKey getDataKey() {
        return new NamespacedKey(Main.getInstance(), "strength-num");
    }

    @Override
    public NamespacedKey getEffectKey() {
        return new NamespacedKey(Main.getInstance(), "strength");
    }

    @Override
    public PotionEffectType getStrongEffectType() {
        return PotionEffectType.STRENGTH;
    }

    @Override
    public PotionEffectType getWeakEffectType() {
        return PotionEffectType.WEAKNESS;
    }

    @Override
    public String getSystemName() {
        return "strength";
    }
}
