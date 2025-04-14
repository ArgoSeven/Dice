package org.argoseven.dice;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.player.PlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Dice implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("dice");
    @Override
    public void onInitialize() {
        RegisterItem.registerModItem();
        RegisterEntity.registerModEnities();
        RegisterTradesAndLoot.registerTrades();
        RegisterTradesAndLoot.appendVanillaLootTable();
    }
}
