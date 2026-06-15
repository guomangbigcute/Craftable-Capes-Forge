package com.craftablecapes.integration;

import com.craftablecapes.items.CapeItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Optional;

/**
 * Helper class for Curios API integration
 */
public class CuriosIntegration {
    private static final Logger LOGGER = LogManager.getLogger();
    
    /**
     * Get the currently equipped cape from the player's curios cape slot
     * @param player The player to check
     * @return Optional containing the CapeItem if equipped, empty otherwise
     */
    public static Optional<CapeItem> getEquippedCape(Player player) {
        try {
            // Get the Curios helper and iterate through all equipped curios
            var optionalCurios = CuriosApi.getCuriosHelper().getEquippedCurios(player).resolve();
            
            if (optionalCurios.isPresent()) {
                var curios = optionalCurios.get();
                // Iterate through all slots
                for (int i = 0; i < curios.getSlots(); i++) {
                    ItemStack stack = curios.getStackInSlot(i);
                    if (!stack.isEmpty() && stack.getItem() instanceof CapeItem) {
                        return Optional.of((CapeItem) stack.getItem());
                    }
                }
            }
            
            return Optional.<CapeItem>empty();
        } catch (Exception e) {
            LOGGER.error("Error getting equipped cape from Curios", e);
            return Optional.<CapeItem>empty();
        }
    }
    
    /**
     * Get the texture location of the currently equipped cape
     * @param player The player to check
     * @return ResourceLocation of the cape texture, or null if no cape equipped
     */
    public static ResourceLocation getEquippedCapeTexture(Player player) {
        Optional<CapeItem> cape = getEquippedCape(player);
        return cape.map(CapeItem::getTextureLocation).orElse(null);
    }
    
    /**
     * Check if the player has any cape equipped in the curios slot
     * @param player The player to check
     * @return true if a cape is equipped, false otherwise
     */
    public static boolean hasCapeEquipped(Player player) {
        return getEquippedCape(player).isPresent();
    }
}
