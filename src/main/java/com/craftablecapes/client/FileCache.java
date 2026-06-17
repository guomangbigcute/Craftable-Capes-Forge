package com.craftablecapes.client;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.resources.Identifier;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class FileCache {
    public static FileCache capeCache;

    private final Path directory;

    public FileCache(Path directory) {
        this.directory = directory;
    }

    public CompletableFuture<Identifier> get(MinecraftProfileTexture texture) {
        String hash = texture.getHash();
        Identifier location = Identifier.fromNamespaceAndPath("craftablecapes", "capes/" + hash);
        return CompletableFuture.completedFuture(location);
    }
}
