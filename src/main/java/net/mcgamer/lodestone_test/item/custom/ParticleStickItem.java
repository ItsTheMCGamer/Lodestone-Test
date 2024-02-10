package net.mcgamer.lodestone_test.item.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Properties;

import static net.mcgamer.lodestone_test.item.custom.BeamRenderer.Renderer.renderBeam;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ParticleStickItem extends Item implements BeamRenderer{
    public ParticleStickItem(Properties pProperties) {
        super(pProperties);
    }

    @SubscribeEvent
    public static void SubscribedBeamRenderer(RenderLevelStageEvent event) {
        renderBeam(event);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(pUsedHand == InteractionHand.MAIN_HAND) {
            pPlayer.sendSystemMessage(Component.literal("This works!"));
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }
}


