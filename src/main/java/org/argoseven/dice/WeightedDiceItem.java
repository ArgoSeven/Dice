package org.argoseven.dice;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Random;


public class WeightedDiceItem extends DiceItem {
    int[] weightedValues = {6, 6, 6, 6, 5, 5, 5, 4, 4, 3, 2, 1};

    //DELETE TOOLTIP
    public WeightedDiceItem(int maxDiceFace) {
        super(maxDiceFace);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.getItemCooldownManager().set(this, 20);
        user.spawnSweepAttackParticles();
        if (!world.isClient) {
            DiceEntity diceEntity = new DiceEntity(user, world);
            diceEntity.setItem(roll(itemStack));
            diceEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 0.5f, 0.3f);
            world.spawnEntity(diceEntity);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }
        return TypedActionResult.success(itemStack, world.isClient());
    }

    @Override
    public ItemStack roll(ItemStack stack) {
        ItemStack copy = stack.copy();
        copy.getOrCreateNbt().putDouble("Dice", weightedDiceRandomValue(weightedValues));
        return copy;
    }

    public static Double weightedDiceRandomValue(int[] weight) {
        int index = new Random().nextInt(weight.length);
        System.out.println("Piero");
        int result = weight[index];
        return ((double) result / 10);
    }
}
