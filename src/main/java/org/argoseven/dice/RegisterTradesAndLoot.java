package org.argoseven.dice;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;

public class    RegisterTradesAndLoot {
    private static final TradeOffer DICE_TRADE = new TradeOffer(new ItemStack(Items.GOLD_NUGGET, 1), new ItemStack(RegisterItem.BASIC_DICE, 1), 20, 5, 0.1f);
    private static final TradeOffer WEIGHTED_DICE_TRADE = new TradeOffer(new ItemStack(Items.GOLD_NUGGET, 1), new ItemStack(RegisterItem.WEIGHTED_DICE, 1), 20, 5, 0.1f);
    private static final Identifier SHIPWRECK_TREASURE = new Identifier("minecraft", "chests/shipwreck_treasure");

    public static void registerTrades() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 1, factories -> {
            factories.add((entity, random) -> DICE_TRADE);
            factories.add((entity, random) -> WEIGHTED_DICE_TRADE);
        });

        TradeOfferHelper.registerWanderingTraderOffers(1, factories -> {
            factories.add((entity, random) -> DICE_TRADE);
            factories.add((entity, random) -> WEIGHTED_DICE_TRADE);
        });
        Dice.LOGGER.info("Register Trades");
    }

    public static  void appendVanillaLootTable(){
        LootTableEvents.MODIFY.register((resourceManager, lootManager, identifier, builder, lootTableSource) -> {
            if(SHIPWRECK_TREASURE.equals(identifier)){
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(RegisterItem.BASIC_DICE))
                        .with(ItemEntry.builder(RegisterItem.WEIGHTED_DICE))
                        .conditionally(RandomChanceLootCondition.builder(0.5f))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,1.0f)));

                builder.pool(poolBuilder);
            }
        });
    }
}
