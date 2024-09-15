package com.starshooterstudios.killstrength.systems;

import com.starshooterstudios.killstrength.Main;
import com.starshooterstudios.killstrength.System;
import org.bukkit.NamespacedKey;
import org.bukkit.potion.PotionEffectType;

public class JumpSystem extends System {
    @Override
    public NamespacedKey getDataKey() {
        return new NamespacedKey(Main.getInstance(), "jump-num");
    }

    @Override
    public NamespacedKey getEffectKey() {
        return new NamespacedKey(Main.getInstance(), "jump");
    }

    @Override
    public PotionEffectType getStrongEffectType() {
        return PotionEffectType.JUMP_BOOST;
    }

    @Override
    public PotionEffectType getWeakEffectType() {
        return PotionEffectType.NAUSEA;
    }

    @Override
    public String getSystemName() {
        return "jump";
    }
}
