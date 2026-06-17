package com.craftablecapes.items;

import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;

public class OnlineCapeItem extends CapeItem {
    public static final List<OnlineCapeItem> ONLINE_CAPES = new ArrayList<>();

    private final String hash;

    public OnlineCapeItem(String hash, Properties properties) {
        super("online_" + hash.substring(0, Math.min(8, hash.length())), properties);
        this.hash = hash;
        ONLINE_CAPES.add(this);
    }

    public String getHash() {
        return hash;
    }

    /** Mojang texture download URL for this cape */
    public String getTextureUrl() {
        return "https://textures.minecraft.net/texture/" + hash;
    }
}
