package org.argoseven.dice.client;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.argoseven.dice.DiceItem;

import java.util.List;

public class ItemTooltipCallbackEvent {
    public static void register(ItemStack itemStack, TooltipContext tooltipContext, List<Text> texts ) {
        if (itemStack.getItem() instanceof DiceItem) {
            texts.clear();
            Text name = itemStack.getName();
            texts.add(name);
            texts.add(Text.translatable("tooltip.item.dice.dice.tooltip").formatted(Formatting.GRAY));
        }
    }
}
