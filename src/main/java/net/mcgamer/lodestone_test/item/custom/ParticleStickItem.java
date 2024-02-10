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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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

@Mod.EventBusSubscriber(modid = "lodestone_test", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ParticleStickItem extends Item {
    public ParticleStickItem(Properties pProperties) {
        super(pProperties);
    }

    @SubscribeEvent
    public static void renderStages(RenderLevelStageEvent event) {
        renderSphere(event);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        Minecraft.getInstance().player.sendSystemMessage(Component.literal("This works!"));
        return InteractionResultHolder.success(itemstack);
    }
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
    

    private static void renderBeam(RenderLevelStageEvent event, Color color) {
        Matrix4f matrix4f = event.getPoseStack().last().pose();
        Vec3 posStart = Minecraft.getInstance().player.position();
        Vec3 posEnd = new Vec3(posStart.x + 20, posStart.y, posStart.z + 20);
        RenderType renderType = LodestoneRenderTypeRegistry.TEXTURE.applyWithModifier(new ResourceLocation(LodestoneTest.MOD_ID,
                "textures/misc/beam.png"), b -> b.replaceVertexFormat(TRIANGLES));
        VertexConsumer vertexConsumer = RenderHandler.DELAYED_RENDER.getBuffer(renderType);
        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat();
        builder.renderBeam(vertexConsumer, matrix4f, posStart, posEnd, 20)
                .setColor(color)
                .setLight(30);
    }

    public static final RenderType PERIODIC_TABLE_SPHERE = LodestoneRenderTypeRegistry.TEXTURE
            .applyWithModifier(new ResourceLocation(LodestoneTest.MOD_ID, "textures/misc/sphere.png"),
                    b -> b.replaceVertexFormat(TRIANGLES));


    private static void renderSphere(RenderLevelStageEvent event) {
        Vec3 spherePos = new Vec3(2.0, 2.0, 2.0);
        Vec3 playerPos = Minecraft.getInstance().player.position();
        PoseStack stack = event.getPoseStack();

        stack.pushPose();
        stack.translate(spherePos.x + playerPos.x, spherePos.y + playerPos.y, spherePos.z + playerPos.z);

        VFXBuilders.WorldVFXBuilder builder = new VFXBuilders.WorldVFXBuilder();
        builder.renderSphere(RenderHandler.DELAYED_RENDER.getBuffer(PERIODIC_TABLE_SPHERE), stack, 4f, 30, 30);

        stack.popPose();
    }
}

