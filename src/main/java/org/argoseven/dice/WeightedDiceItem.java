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

    //DELATE TOOLTIP
    public WeightedDiceItem(int maxDiceFace) {
        super(maxDiceFace);
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = super.getDefaultStack();
        stack.setNbt(weightedDiceRandomValue(weightedValues));
        return stack;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.getItemCooldownManager().set(this, 20);
        user.spawnSweepAttackParticles();
        if (!world.isClient) {
            DiceEntity diceEntity = new DiceEntity(user, world);
            diceEntity.setItem(itemStack.getItem().getDefaultStack());
            diceEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 0.5f, 0.3f);
            world.spawnEntity(diceEntity);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }
        return TypedActionResult.success(itemStack, world.isClient());
    }

    public static NbtCompound weightedDiceRandomValue(int[] weight) {
        NbtCompound randomValue = new NbtCompound();
        Random random = new Random();

        // Create an array of possible values with weights  6 , 5 has the highest probability, then 4, then 1-3
        int index = random.nextInt(weight.length);
        int result = weight[index];

        // Store the result in the NbtCompound
        randomValue.putDouble("Dice", (double) result / 10);
        return randomValue;
    }
}
