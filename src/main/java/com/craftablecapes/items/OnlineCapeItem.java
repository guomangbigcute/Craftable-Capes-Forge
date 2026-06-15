package com.craftablecapes.items;

import com.craftablecapes.client.FileCache;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Online cape item class for capes downloaded from Minecraft servers
 * Textures are cached using FileCache system
 */
public class OnlineCapeItem extends CapeItem {
    public static final List<OnlineCapeItem> ONLINE_CAPES = new ArrayList<>();
    
    private final String hash;
    private ResourceLocation cachedTexture;
    
    public OnlineCapeItem(String hash, Properties properties) {
        // Pass null texture name - online capes don't use local textures
        super("placeholder", properties);
        this.hash = hash;
        
        ONLINE_CAPES.add(this);
    }
    
    /**
     * Get the texture location for this online cape
     * Downloads and caches the texture if not already cached
     * @return ResourceLocation pointing to the cached cape texture
     */
    @Override
    public ResourceLocation getTextureLocation() {
        if (cachedTexture == null && FileCache.capeCache != null) {
            // Create a MinecraftProfileTexture with the official texture URL
            MinecraftProfileTexture profileTexture = new MinecraftProfileTexture(
                "http://textures.minecraft.net/texture/" + hash, 
                null
            );
            
            // Get or load the cached texture
            FileCache.capeCache.get(profileTexture).thenAccept(location -> {
                this.cachedTexture = location;
            });
        }
        
        return cachedTexture;
    }
    
    /**
     * Get the hash identifier for this online cape
     * @return SHA1 hash of the cape texture
     */
    public String getHash() {
        return hash;
    }
}
