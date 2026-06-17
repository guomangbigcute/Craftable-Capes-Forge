package com.craftablecapes.client;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.resources.Identifier;

import java.nio.file.Path;

/**
 * Provides Identifier mapping for cape textures.
 * The cape textures are downloaded and cached by OnlineCapeLoader,
 * this class helps resolve MinecraftProfileTexture hashes to our local identifiers.
 */
public class FileCache {
    /** Singleton cape cache instance, initialized during client setup */
    public static FileCache capeCache;

    private final Path directory;

    public FileCache(Path directory) {
        this.directory = directory;
    }

    public Path getDirectory() {
        return directory;
    }

    /**
     * Resolve a Minecraft profile texture to our local cape texture identifier.
     * Uses the full hash as the texture name.
     */
    public Identifier get(MinecraftProfileTexture texture) {
        String hash = texture.getHash();
        return Identifier.fromNamespaceAndPath("craftablecapes", "textures/capes/" + hash + ".png");
    }
}
