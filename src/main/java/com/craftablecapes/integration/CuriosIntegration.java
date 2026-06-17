package com.craftablecapes.integration;

import com.craftablecapes.items.CapeItem;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Optional;

public class CuriosIntegration {
    private static final Logger LOGGER = LoggerFactory.getLogger(CuriosIntegration.class);

    public static Optional<CapeItem> getEquippedCape(Player player) {
        try {
            var handler = CuriosApi.getCuriosInventory(player);
            if (handler.isEmpty()) return Optional.empty();

            var stacksHandler = handler.get().getStacksHandler("cape");
            if (stacksHandler.isPresent()) {
                var stacks = stacksHandler.get().getStacks();
                for (int j = 0; j < stacks.getSlots(); j++) {
                    ItemStack stack = stacks.getStackInSlot(j);
                    if (!stack.isEmpty() && stack.getItem() instanceof CapeItem cape) {
                        return Optional.of(cape);
                    }
                }
            }
            return Optional.empty();
        } catch (Exception e) {
            LOGGER.error("Error getting equipped cape from Curios", e);
            return Optional.empty();
        }
    }

    public static Identifier getEquippedCapeTexture(Player player) {
        Optional<CapeItem> cape = getEquippedCape(player);
        return cape.map(c -> {
            String name = c.getTextureName();
            // ClientAsset.ResourceTexture auto-adds "textures/" prefix and ".png" suffix
            return Identifier.fromNamespaceAndPath("craftablecapes", "capes/" + name);
        }).orElse(null);
    }

    public static boolean hasCapeEquipped(Player player) {
        return getEquippedCape(player).isPresent();
    }
}
