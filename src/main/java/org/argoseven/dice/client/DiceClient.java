package org.argoseven.dice.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.item.DyeableItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.argoseven.dice.DiceItem;
import org.argoseven.dice.RegisterEntity;
import org.argoseven.dice.RegisterItem;
import org.argoseven.dice.WeightedDiceItem;

public class DiceClient implements ClientModInitializer, DyeableItem {
    public static final int DEFAULT_TINT = 0xe2c986;
    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register(ItemTooltipCallbackEvent::register);
        
        EntityRendererRegistry.register(RegisterEntity.THROWABLE_DICE, FlyingItemEntityRenderer::new);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            NbtCompound nbtCompound = stack.getSubNbt("display");
            if (nbtCompound != null) {
                return nbtCompound.getInt("color");
            }
            return DEFAULT_TINT;
        }, RegisterItem.BASIC_DICE);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            NbtCompound nbtCompound = stack.getSubNbt("display");
            if (nbtCompound != null) {
                return nbtCompound.getInt("color");
            }
            return DEFAULT_TINT;
        }, RegisterItem.WEIGHTED_DICE);



        ModelPredicateProviderRegistry.register(RegisterItem.BASIC_DICE, new Identifier("dice_value"), (stack, world, entity, seed) -> {
            if ((stack.getItem() instanceof DiceItem && stack.getNbt() != null)) {
                return (float) stack.getNbt().getDouble("Dice");
            } else {
                return 0.0F;
            }
        });

        ModelPredicateProviderRegistry.register(RegisterItem.WEIGHTED_DICE, new Identifier("dice_value"), (stack, world, entity,seed) -> {
            if ((stack.getItem() instanceof WeightedDiceItem && stack.getNbt() != null)) {
                return (float) stack.getNbt().getDouble("Dice");
            } else {
                return 0.6F;
            }
        });
    }
}
