package org.argoseven.dice.mixin;


import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import org.argoseven.dice.DiceItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import team.creative.itemphysic.client.ItemPhysicClient;



@Restriction(
        require = {
                @Condition("itemphysic")
        }
)
@Environment(EnvType.CLIENT)
@Mixin({ItemPhysicClient.class})
public class ItemPhysicPatch {
    @Inject(method = "render", at= @At("HEAD"),cancellable = true)
    private static void bypassRender(ItemEntity entity, float entityYaw, float partialTicks, MatrixStack pose, VertexConsumerProvider buffer, int packedLight, ItemRenderer itemRenderer, Random rand, CallbackInfoReturnable<Boolean> cir) {
        if (entity.getStack().getItem() instanceof DiceItem && entity.isOnGround()){
            pose.push();
            ItemStack itemstack = entity.getStack();
            BakedModel bakedmodel = itemRenderer.getModel(itemstack, entity.world, (LivingEntity)null, entity.getId());
            pose.translate((double)0.0F, 0.1, (double)0.0F);
            itemRenderer.renderItem(itemstack, ModelTransformation.Mode.GROUND, false, pose, buffer, packedLight, OverlayTexture.DEFAULT_UV, bakedmodel);
            pose.pop();
            cir.setReturnValue(true);
        }
    }
}
