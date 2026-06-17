package com.craftablecapes.client;

import com.craftablecapes.items.CapeItem;
import com.craftablecapes.client.renderer.CapeCurioRenderer;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class ClientSetup {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientSetup.class);

    public static void onClientSetup(FMLClientSetupEvent event) {
        for (CapeItem cape : CapeItem.ALL_CAPES) {
            ICurioRenderer.register(cape, CapeCurioRenderer::new);
        }
        LOGGER.info("Registered {} cape renderers via ICurioRenderer.register()", CapeItem.ALL_CAPES.size());
    }
}
