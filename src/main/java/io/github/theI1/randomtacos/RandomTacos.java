package io.github.theI1.randomtacos;

import com.mojang.logging.LogUtils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RandomTacos.MODID)
public class RandomTacos
{
    public static final String MODID = "randomtacos";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    
    public static final RegistryObject<Item> TACO_FLOUR_ITEM = ITEMS.register("taco_flour", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TACO_DOUGH_ITEM = ITEMS.register("taco_dough", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TACO_SHELL_ITEM = ITEMS.register("taco_shell", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).build())));
    public static final RegistryObject<Block> HOT_PEPPER_CROP_BLOCK = BLOCKS.register("hot_pepper", () -> new CropBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final RegistryObject<Item> HOT_PEPPER_ITEM = ITEMS.register("hot_pepper", () -> new ItemNameBlockItem(HOT_PEPPER_CROP_BLOCK.get(), new Item.Properties().food(new FoodProperties.Builder().nutrition(4).build())));
    public static final RegistryObject<Item> GROUND_BEEF_ITEM = ITEMS.register("ground_beef", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).meat().build())));
    public static final RegistryObject<Item> COOKED_GROUND_BEEF_ITEM = ITEMS.register("cooked_ground_beef", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(0.8F).meat().build())));

    public RandomTacos()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
    }

    private void addCreative(CreativeModeTabEvent.BuildContents event)
    {
        if (event.getTab() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(HOT_PEPPER_ITEM);
            event.accept(TACO_SHELL_ITEM);
        }
        if (event.getTab() == CreativeModeTabs.INGREDIENTS) {
            event.accept(HOT_PEPPER_ITEM);
            event.accept(TACO_FLOUR_ITEM);
            event.accept(TACO_DOUGH_ITEM);
            event.accept(TACO_SHELL_ITEM);
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
        }
    }
}
