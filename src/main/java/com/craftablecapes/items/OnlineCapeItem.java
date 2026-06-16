package com.craftablecapes.items;

import com.craftablecapes.CraftableCapes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Online cape item - texture is pre-loaded during ClientSetup via HttpTexture.
 * getTextureLocation() returns the registered ResourceLocation immediately.
 */
public class OnlineCapeItem extends CapeItem {
    public static final List<OnlineCapeItem> ONLINE_CAPES = new ArrayList<>();
    
    private final String hash;
    private final ResourceLocation textureLocation;
    
    public OnlineCapeItem(String hash, Properties properties) {
        super("placeholder", properties);
        this.hash = hash;
        
        // Create a deterministic texture path
        String cacheName = "online_" + hash.substring(0, Math.min(8, hash.length()));
        this.textureLocation = ResourceLocation.fromNamespaceAndPath(CraftableCapes.MOD_ID, "capes/" + cacheName);
        
        ONLINE_CAPES.add(this);
    }
    
    @Override
    public ResourceLocation getTextureLocation() {
        return textureLocation;
    }
    
    public String getHash() {
        return hash;
    }
}
