package org.argoseven.dice;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.world.World;

import java.util.Random;


public class DiceItem extends Item implements DyeableItem {
    private final int maxDiceFace;
    protected static final float THROW_SPEED = 0.3f;
    public static final DispenserBehavior DISPENSER_BEHAVIOR = new ItemDispenserBehavior(){
        @Override
        protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
            DiceItem diceItem = (DiceItem) stack.getItem();
            super.dispenseSilently(pointer, diceItem.roll(stack));
            stack.decrement(1);
            return stack;
        }
    };

    public DiceItem(int maxDiceFace) {
        super(new FabricItemSettings().maxCount(64).group(ItemGroup.COMBAT).rarity(Rarity.UNCOMMON));
        this.maxDiceFace = maxDiceFace;
        DispenserBlock.registerBehavior(this, DISPENSER_BEHAVIOR);
    }

    public ItemStack roll(ItemStack stack){
        ItemStack copy = stack.copy();
        copy.getOrCreateNbt().putDouble("Dice", diceRandomValue(maxDiceFace));
        return copy;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.getItemCooldownManager().set(this, 20);
        user.spawnSweepAttackParticles();
        if (!world.isClient) {
            DiceEntity diceEntity = new DiceEntity(user, world);
            diceEntity.setItem(roll(itemStack));
            diceEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, THROW_SPEED, 0.3f);
            world.spawnEntity(diceEntity);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }
        return TypedActionResult.success(itemStack, world.isClient());
    }

    public static double diceRandomValue(int maxValue){
        return (double) (new Random().nextInt(maxValue) + 1) / 10;
    }
}
