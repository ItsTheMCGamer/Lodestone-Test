package net.mcgamer.lodestone_test.item.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.mcgamer.lodestone_test.LodestoneTest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import org.joml.Matrix4f;
import team.lodestar.lodestone.handlers.RenderHandler;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;

import java.awt.*;

import static com.mojang.blaze3d.vertex.VertexFormat.Mode.TRIANGLES;

interface BeamRenderer {

    class Renderer {
        public static final RenderType PERIODIC_TABLE_BEAM = LodestoneRenderTypeRegistry.TEXTURE
                .applyAndCache(new ResourceLocation(LodestoneTest.MOD_ID, "textures/misc/sphere.png"));

        public static void renderBeam(RenderLevelStageEvent event) {
            PoseStack poseStack = event.getPoseStack();
            Vec3 startPos = new Vec3(3, 63, 0);
            Vec3 endPos = startPos.add(1, 10, 1);

            VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat();
            VertexConsumer consumer = RenderHandler.DELAYED_RENDER.getBuffer(PERIODIC_TABLE_BEAM);


            poseStack.pushPose();

            builder.setColor(Color.BLUE);
            builder.setAlpha(0.5f);
            builder.renderBeam(consumer, poseStack.last().pose(), startPos, endPos, 3);
            builder.setPosColorLightmapDefaultFormat();

            poseStack.popPose();
        }
    }
}
