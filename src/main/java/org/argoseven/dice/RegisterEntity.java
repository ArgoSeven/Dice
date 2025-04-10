package org.argoseven.dice;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class RegisterEntity {

    public  static final EntityType<DiceEntity> THROWABLE_DICE = registryEntity("throwable_dice");

    public static EntityType<DiceEntity> registryEntity(String id){
        return Registry.register(Registry.ENTITY_TYPE, new Identifier("dice", id), FabricEntityTypeBuilder.<DiceEntity>create(SpawnGroup.MISC, DiceEntity::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build());
    }

    public static void registerModEnities(){
        System.out.println("Register Enitities");
    }
}
