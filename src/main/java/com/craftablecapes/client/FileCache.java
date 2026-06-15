package com.craftablecapes.client;

import com.google.common.hash.Hashing;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * File cache system for managing downloaded cape textures
 * Adapted from Fabric version to work with Forge 1.18.2
 */
@OnlyIn(Dist.CLIENT)
public class FileCache {
    public static FileCache capeCache;
    
    private final Map<String, CompletableFuture<ResourceLocation>> hashToTexture = new Object2ObjectOpenHashMap<>();
    private final Path directory;
    
    public FileCache(Path directory) {
        this.directory = directory;
    }
    
    /**
     * Get or load a cached texture by its profile texture
     * @param texture The Minecraft profile texture containing hash and URL
     * @return CompletableFuture that will complete with the ResourceLocation
     */
    public CompletableFuture<ResourceLocation> get(MinecraftProfileTexture texture) {
        String hash = texture.getHash();
        CompletableFuture<ResourceLocation> future = hashToTexture.get(hash);
        
        if (future == null) {
            future = store(texture);
            hashToTexture.put(hash, future);
        }
        
        return future;
    }
    
    /**
     * Store/download a texture and register it with the texture manager
     * @param texture The profile texture to download
     * @return CompletableFuture with the registered ResourceLocation
     */
    private CompletableFuture<ResourceLocation> store(MinecraftProfileTexture texture) {
        // Use SkinManager to load the texture (1.18.2 API)
        Minecraft mc = Minecraft.getInstance();
        SkinManager skinManager = mc.getSkinManager();
        
        // Register the texture - returns ResourceLocation directly
        ResourceLocation rl = skinManager.registerTexture(texture, MinecraftProfileTexture.Type.CAPE);
        
        return CompletableFuture.completedFuture(rl);
    }
    
    /**
     * Generate a ResourceLocation for a texture hash
     * @param hash The SHA1 hash of the texture
     * @return ResourceLocation in format "craftablecapes:capes/{hash}"
     */
    private ResourceLocation getTexturePath(String hash) {
        return new ResourceLocation("craftablecapes", "capes/" + hash);
    }
}
