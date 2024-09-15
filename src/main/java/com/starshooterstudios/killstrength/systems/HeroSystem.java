package com.starshooterstudios.killstrength.systems;

import com.starshooterstudios.killstrength.Main;
import com.starshooterstudios.killstrength.System;
import org.bukkit.NamespacedKey;
import org.bukkit.potion.PotionEffectType;

public class HeroSystem  extends System {
    @Override
    public NamespacedKey getDataKey() {
        return new NamespacedKey(Main.getInstance(), "hero-num");
    }

    @Override
    public NamespacedKey getEffectKey() {
        return new NamespacedKey(Main.getInstance(), "hero");
    }

    @Override
    public PotionEffectType getStrongEffectType() {
        return PotionEffectType.HERO_OF_THE_VILLAGE;
    }

    @Override
    public PotionEffectType getWeakEffectType() {
        return PotionEffectType.GLOWING;
    }

    @Override
    public String getSystemName() {
        return "hero";
    }
}
