package com.craftablecapes.items;

import java.util.ArrayList;
import java.util.List;

public class OnlineCapeItem extends CapeItem {
    public static final List<OnlineCapeItem> ONLINE_CAPES = new ArrayList<>();

    private final String hash;

    public OnlineCapeItem(String hash, Properties properties) {
        super("placeholder", properties);
        this.hash = hash;
        ONLINE_CAPES.add(this);
    }

    public String getHash() {
        return hash;
    }
}
