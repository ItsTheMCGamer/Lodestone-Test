package net.mcgamer.lodestone_test.item;

import com.google.common.util.concurrent.ClosingFuture;
import net.mcgamer.lodestone_test.LodestoneTest;
import net.mcgamer.lodestone_test.item.custom.ParticleStickItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, LodestoneTest.MOD_ID);

    public static final RegistryObject<Item> PARTICLE_STICK = ITEMS.register("particle_stick",
            () -> new ParticleStickItem(new Item.Properties()));

    public static final RegistryObject<Item> SPEEDITE = ITEMS.register("speedite",
            () -> new Item(new Item.Properties()));


    public static final RegistryObject<Item> SPEEDITE_BOOTS = ITEMS.register("speedite_boots.json",
            () -> new ArmorItem(ModArmorMaterials.SPEEDITE, ArmorItem.Type.BOOTS, new Item.Properties()));
    public static final RegistryObject<Item> SPEEDITE_LEGGINGS = ITEMS.register("speedite_leggings",
            () -> new ArmorItem(ModArmorMaterials.SPEEDITE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> SPEEDITE_HELMET = ITEMS.register("speedite_helmet",
            () -> new ArmorItem(ModArmorMaterials.SPEEDITE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> SPEEDITE_CHESTPLATE = ITEMS.register("speedite_chestplate.json",
            () -> new ArmorItem(ModArmorMaterials.SPEEDITE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
