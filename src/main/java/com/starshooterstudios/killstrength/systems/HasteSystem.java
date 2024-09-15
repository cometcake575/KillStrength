package com.starshooterstudios.killstrength.systems;

import com.starshooterstudios.killstrength.Main;
import com.starshooterstudios.killstrength.System;
import org.bukkit.NamespacedKey;
import org.bukkit.potion.PotionEffectType;

public class HasteSystem  extends System {
    @Override
    public NamespacedKey getDataKey() {
        return new NamespacedKey(Main.getInstance(), "haste-num");
    }

    @Override
    public NamespacedKey getEffectKey() {
        return new NamespacedKey(Main.getInstance(), "haste");
    }

    @Override
    public PotionEffectType getStrongEffectType() {
        return PotionEffectType.HASTE;
    }

    @Override
    public PotionEffectType getWeakEffectType() {
        return PotionEffectType.MINING_FATIGUE;
    }

    @Override
    public String getSystemName() {
        return "haste";
    }
}
