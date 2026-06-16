package com.craftablecapes.integration;

import com.craftablecapes.items.CapeItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.Optional;

public class CuriosIntegration {
    private static final Logger LOGGER = LoggerFactory.getLogger(CuriosIntegration.class);

    public static Optional<CapeItem> getEquippedCape(Player player) {
        try {
            var optionalHandler = CuriosApi.getCuriosHelper().getCuriosHandler(player);
            if (optionalHandler.isEmpty()) return Optional.empty();

            ICuriosItemHandler handler = optionalHandler.get();
            var stacksHandler = handler.getStacksHandler("cape");
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

    public static ResourceLocation getEquippedCapeTexture(Player player) {
        Optional<CapeItem> cape = getEquippedCape(player);
        return cape.map(CapeItem::getTextureLocation).orElse(null);
    }

    public static boolean hasCapeEquipped(Player player) {
        return getEquippedCape(player).isPresent();
    }
}
