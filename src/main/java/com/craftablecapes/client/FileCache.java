package com.craftablecapes.client;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

@OnlyIn(Dist.CLIENT)
public class FileCache {
    public static FileCache capeCache;

    private final Path directory;

    public FileCache(Path directory) {
        this.directory = directory;
    }

    public CompletableFuture<ResourceLocation> get(MinecraftProfileTexture texture) {
        String hash = texture.getHash();
        ResourceLocation location = ResourceLocation.fromNamespaceAndPath("craftablecapes", "capes/" + hash);
        return CompletableFuture.completedFuture(location);
    }
}
