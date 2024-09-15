package com.starshooterstudios.killstrength.systems;

import com.starshooterstudios.killstrength.Main;
import com.starshooterstudios.killstrength.System;
import org.bukkit.NamespacedKey;
import org.bukkit.potion.PotionEffectType;

public class RegenSystem  extends System {
    @Override
    public NamespacedKey getDataKey() {
        return new NamespacedKey(Main.getInstance(), "regen-num");
    }

    @Override
    public NamespacedKey getEffectKey() {
        return new NamespacedKey(Main.getInstance(), "regen");
    }

    @Override
    public PotionEffectType getStrongEffectType() {
        return PotionEffectType.REGENERATION;
    }

    @Override
    public PotionEffectType getWeakEffectType() {
        return PotionEffectType.POISON;
    }

    @Override
    public String getSystemName() {
        return "regen";
    }
}
