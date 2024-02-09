package net.mcgamer.lodestone_test.item.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.mcgamer.lodestone_test.LodestoneTest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import org.joml.Matrix4f;
import team.lodestar.lodestone.handlers.RenderHandler;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;

import java.awt.*;

import static com.mojang.blaze3d.vertex.VertexFormat.Mode.TRIANGLES;

public class ParticleStickItem extends Item {
    public ParticleStickItem(Properties pProperties) {
        super(pProperties);
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand, RenderLevelStageEvent event) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        renderSphere(event);
        Minecraft.getInstance().player.sendSystemMessage(Component.literal("This works! But not really..."));
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    //@Override
    //public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
    //if(usedHand.equals(InteractionHand.MAIN_HAND))
    //spawnParticle(level, player, Color.CYAN, Color.blue);
    //return super.use(level, player, usedHand);
    //}

    private static void spawnParticle(Level level, Player player, Color color, Color endColor) {
        if (level.isClientSide) {
            double x = player.getX() + 0.5f;
            double y = player.getY() + 0.5f;
            double z = player.getZ() + 0.5f;
            WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setScaleData(GenericParticleData.create(0.5f, 0).build())
                    .setTransparencyData(GenericParticleData.create(0.75f, 0.25f).build())
                    .setColorData(ColorParticleData.create(color, endColor).setCoefficient(1.4f).setEasing(Easing.BOUNCE_IN_OUT).build())
                    .setSpinData(SpinParticleData.create(0.2f, 0.4f).setSpinOffset((level.getGameTime() * 0.2f) % 6.28f).setEasing(Easing.QUARTIC_IN).build())
                    .setLifetime(40)
                    .addMotion(0, 0.01f, 0)
                    .enableNoClip()
                    .spawn(level, x, y, z);

        }
    }

    //RenderHandler.DELAYED_RENDER.getBuffer(renderType);

    private static void renderBeam(RenderLevelStageEvent event, Color color) {
        Matrix4f matrix4f = event.getPoseStack().last().pose();
        Vec3 posStart = Minecraft.getInstance().getCameraEntity().getEyePosition();
        Vec3 posEnd = new Vec3(posStart.x + 20, posStart.y, posStart.z + 20);
        RenderType renderType = LodestoneRenderTypeRegistry.TEXTURE.applyWithModifier(new ResourceLocation(LodestoneTest.MOD_ID,
                "textures/misc/beam.png"), b -> b.replaceVertexFormat(TRIANGLES));
        VertexConsumer vertexConsumer = RenderHandler.DELAYED_RENDER.getBuffer(renderType);
        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat();
        builder.renderBeam(vertexConsumer, matrix4f, posStart, posEnd, 20)
                .setColor(color)
                .setLight(30);
    }

    private static void renderSphere(RenderLevelStageEvent event) {
        PoseStack poseStack = event.getPoseStack();
        RenderType renderType = LodestoneRenderTypeRegistry.TEXTURE.applyWithModifier(new ResourceLocation(LodestoneTest.MOD_ID,
                "textures/misc/sphere.png"), b -> b.replaceVertexFormat(TRIANGLES));
        VertexConsumer vertexConsumer = RenderHandler.DELAYED_RENDER.getBuffer(renderType);
        poseStack.pushPose();
        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat();
        builder.renderSphere(vertexConsumer, poseStack, 3, 50, 50);
        poseStack.popPose();
    }

    private boolean isGrassBlock(BlockState state) {
        return state.is(Blocks.GRASS_BLOCK);
    }
}

