package org.argoseven.dice;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;


public class DiceItem extends Item implements DyeableItem {
    protected int maxDiceFace;
    public static final DispenserBehavior DISPENSER_BEHAVIOR = new ItemDispenserBehavior(){
        @Override
        protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
            System.out.println(stack);
            super.dispenseSilently(pointer, stack.getItem().getDefaultStack());
            stack.decrement(1);
            return stack;
        }
    };

//AAA
    public DiceItem(int maxDiceFace) {
        super(new FabricItemSettings().maxCount(64).group(ItemGroup.COMBAT).rarity(Rarity.UNCOMMON));
        this.maxDiceFace = maxDiceFace;
        DispenserBlock.registerBehavior(this, DISPENSER_BEHAVIOR);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.item.dice.dice.tooltip").formatted(Formatting.GRAY));
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = new ItemStack(this);
        stack.setNbt(diceRandomValue(maxDiceFace));
        return stack;
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.getItemCooldownManager().set(this, 20);
        //world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_LODESTONE_PLACE, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
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

    public static NbtCompound diceRandomValue(int maxValue){
        NbtCompound randomValue = new NbtCompound();
        Random random = new Random();
        randomValue.putDouble("Dice", (double) (random.nextInt(maxValue) + 1) / 10);
        return randomValue;
    }
}
