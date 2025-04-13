package org.argoseven.dice;

import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class DiceEntity extends ThrownItemEntity {
    public DiceEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public DiceEntity(EntityType<? extends ThrownItemEntity> entityType, double d, double e, double f, World world) {
        super(entityType, d, e, f, world);
    }

    public DiceEntity(EntityType<? extends ThrownItemEntity> entityType, LivingEntity livingEntity, World world) {
        super(entityType, livingEntity, world);
    }

    public DiceEntity(LivingEntity livingEntity, World world) {
            super(RegisterEntity.THROWABLE_DICE, livingEntity, world);
    }


    @Override
    protected Item getDefaultItem() {
        return RegisterItem.BASIC_DICE;
    }


    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {
            this.world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, this.getStack()), this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        }
    }



    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!world.isClient) {
            ItemEntity dice_entity = new ItemEntity(world, this.getX(), this.getY(), this.getZ(), this.getStack());
            dice_entity.setPickupDelay(60);

            world.spawnEntity(dice_entity);
            world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.BLOCK_LODESTONE_PLACE, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
            world.sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            this.discard();
        }
    }


}
