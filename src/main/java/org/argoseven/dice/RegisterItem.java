package org.argoseven.dice;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RegisterItem {
    public static final Item BASIC_DICE = registryItem("dice", new DiceItem(6));
    public static final Item WEIGHTED_DICE = registryItem("weighted_dice", new WeightedDiceItem(6));

    private static Item registryItem(String name, Item item){
        return Registry.register(Registry.ITEM, new Identifier("dice", name), item);
    }
    public static void registerModItem(){
        System.out.println("Register Item");
    }
}
