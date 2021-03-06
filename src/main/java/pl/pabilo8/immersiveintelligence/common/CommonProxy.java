package pl.pabilo8.immersiveintelligence.common;

import blusunrize.immersiveengineering.ImmersiveEngineering;
import blusunrize.immersiveengineering.api.IEApi;
import blusunrize.immersiveengineering.api.MultiblockHandler;
import blusunrize.immersiveengineering.api.MultiblockHandler.MultiblockFormEvent;
import blusunrize.immersiveengineering.api.crafting.BlueprintCraftingRecipe;
import blusunrize.immersiveengineering.api.crafting.CrusherRecipe;
import blusunrize.immersiveengineering.api.crafting.IngredientStack;
import blusunrize.immersiveengineering.api.crafting.MixerRecipe;
import blusunrize.immersiveengineering.api.tool.BulletHandler;
import blusunrize.immersiveengineering.api.tool.ExcavatorHandler;
import blusunrize.immersiveengineering.api.tool.ExcavatorHandler.MineralMix;
import blusunrize.immersiveengineering.api.tool.RailgunHandler;
import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces.IGuiTile;
import blusunrize.immersiveengineering.common.blocks.ItemBlockIEBase;
import blusunrize.immersiveengineering.common.blocks.metal.BlockTypes_MetalDevice0;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntityChargingStation;
import blusunrize.immersiveengineering.common.blocks.wooden.BlockTypes_WoodenDevice0;
import blusunrize.immersiveengineering.common.blocks.wooden.TileEntityWatermill;
import blusunrize.immersiveengineering.common.blocks.wooden.TileEntityWindmill;
import blusunrize.immersiveengineering.common.crafting.RecipeRGBColouration;
import blusunrize.immersiveengineering.common.items.IEItemInterfaces.IGuiItem;
import blusunrize.immersiveengineering.common.util.ItemNBTHelper;
import blusunrize.immersiveengineering.common.util.network.MessageNoSpamChatComponents;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager.LoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import pl.pabilo8.immersiveintelligence.Config.IIConfig.MechanicalDevices;
import pl.pabilo8.immersiveintelligence.Config.IIConfig.Ores;
import pl.pabilo8.immersiveintelligence.ImmersiveIntelligence;
import pl.pabilo8.immersiveintelligence.api.*;
import pl.pabilo8.immersiveintelligence.api.ShrapnelHandler.Shrapnel;
import pl.pabilo8.immersiveintelligence.api.bullets.BulletRegistry;
import pl.pabilo8.immersiveintelligence.api.bullets.DamageBlockPos;
import pl.pabilo8.immersiveintelligence.api.bullets.PenetrationRegistry;
import pl.pabilo8.immersiveintelligence.api.crafting.ElectrolyzerRecipe;
import pl.pabilo8.immersiveintelligence.api.rotary.CapabilityRotaryEnergy;
import pl.pabilo8.immersiveintelligence.api.rotary.RotaryUtils;
import pl.pabilo8.immersiveintelligence.api.utils.IAdvancedMultiblock;
import pl.pabilo8.immersiveintelligence.api.utils.MachineUpgrade;
import pl.pabilo8.immersiveintelligence.api.utils.MinecartBlockHelper;
import pl.pabilo8.immersiveintelligence.common.ammunition_system.BulletComponentFirework;
import pl.pabilo8.immersiveintelligence.common.ammunition_system.BulletComponentTracerPowder;
import pl.pabilo8.immersiveintelligence.common.ammunition_system.BulletComponentWhitePhosphorus;
import pl.pabilo8.immersiveintelligence.common.ammunition_system.cores.*;
import pl.pabilo8.immersiveintelligence.common.ammunition_system.explosives.BulletComponentHMX;
import pl.pabilo8.immersiveintelligence.common.ammunition_system.explosives.BulletComponentNuke;
import pl.pabilo8.immersiveintelligence.common.ammunition_system.explosives.BulletComponentRDX;
import pl.pabilo8.immersiveintelligence.common.ammunition_system.explosives.BulletComponentTNT;
import pl.pabilo8.immersiveintelligence.common.ammunition_system.shrapnel.BulletComponentShrapnel;
import pl.pabilo8.immersiveintelligence.common.blocks.BlockIIBase;
import pl.pabilo8.immersiveintelligence.common.blocks.BlockIIFluid;
import pl.pabilo8.immersiveintelligence.common.blocks.fortification.TileEntityChainFence;
import pl.pabilo8.immersiveintelligence.common.blocks.fortification.TileEntityTankTrap;
import pl.pabilo8.immersiveintelligence.common.blocks.metal.*;
import pl.pabilo8.immersiveintelligence.common.blocks.multiblocks.metal.tileentities.first.*;
import pl.pabilo8.immersiveintelligence.common.blocks.multiblocks.metal.tileentities.second.MultiblockEmplacement;
import pl.pabilo8.immersiveintelligence.common.blocks.multiblocks.metal.tileentities.second.MultiblockRedstoneInterface;
import pl.pabilo8.immersiveintelligence.common.blocks.multiblocks.metal.tileentities.second.TileEntityEmplacement;
import pl.pabilo8.immersiveintelligence.common.blocks.multiblocks.metal.tileentities.second.TileEntityRedstoneInterface;
import pl.pabilo8.immersiveintelligence.common.blocks.multiblocks.wooden.*;
import pl.pabilo8.immersiveintelligence.common.blocks.rotary.*;
import pl.pabilo8.immersiveintelligence.common.blocks.stone.TileEntitySandbags;
import pl.pabilo8.immersiveintelligence.common.blocks.types.IIBlockTypes_Connector;
import pl.pabilo8.immersiveintelligence.common.blocks.types.IIBlockTypes_MetalDevice;
import pl.pabilo8.immersiveintelligence.common.blocks.types.IIBlockTypes_Ore;
import pl.pabilo8.immersiveintelligence.common.compat.IICompatModule;
import pl.pabilo8.immersiveintelligence.common.entity.*;
import pl.pabilo8.immersiveintelligence.common.entity.bullets.EntityBullet;
import pl.pabilo8.immersiveintelligence.common.entity.bullets.EntityShrapnel;
import pl.pabilo8.immersiveintelligence.common.entity.minecarts.*;
import pl.pabilo8.immersiveintelligence.common.items.ItemIIBase;
import pl.pabilo8.immersiveintelligence.common.items.ItemIIMinecart;
import pl.pabilo8.immersiveintelligence.common.items.weapons.ItemIIRailgunOverride;
import pl.pabilo8.immersiveintelligence.common.network.IIPacketHandler;
import pl.pabilo8.immersiveintelligence.common.network.MessageBlockDamageSync;
import pl.pabilo8.immersiveintelligence.common.wire.IIDataWireType;
import pl.pabilo8.immersiveintelligence.common.world.IIWorldGen;
import pl.pabilo8.immersiveintelligence.common.world.IIWorldGen.EnumOreType;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map.Entry;

import static blusunrize.immersiveengineering.api.energy.wires.WireApi.registerFeedthroughForWiretype;

/**
 * Created by Pabilo8 on 2019-05-07.
 * 2 days of headache, pain, lack of sleep and addict-like coffee drinking, finally i got a fix!
 * the problem why the server couldn't load was the modifier "abstract" in this class
 * why? i don't know why it was here in the first place
 * for how long? ask github
 * how did you not notice that? ... that was really unexpected, didn't even consider such a thing being there
 */
@Mod.EventBusSubscriber(modid = ImmersiveIntelligence.MODID)
public class CommonProxy implements IGuiHandler, LoadingCallback
{
	public static final String DESCRIPTION_KEY = "desc."+ImmersiveIntelligence.MODID+".";
	public static final String INFO_KEY = "info."+ImmersiveIntelligence.MODID+".";
	public static final String DATA_KEY = "datasystem."+ImmersiveIntelligence.MODID+".";
	public static final String ROTARY_KEY = "rotary."+ImmersiveIntelligence.MODID+".";
	public static final String BLOCK_KEY = "tile."+ImmersiveIntelligence.MODID+".";

	public static final String SKIN_LOCATION = ImmersiveIntelligence.MODID+":textures/skins/";

	public static final String TOOL_ADVANCED_HAMMER = "II_ADVANCED_HAMMER";
	public static final String TOOL_WRENCH = "II_WRENCH";
	public static final String TOOL_ADVANCED_WRENCH = "II_ADVANCED_WRENCH";
	public static final String TOOL_CROWBAR = "II_CROWBAR";
	public static final String TOOL_TACHOMETER = "TOOL_TACHOMETER";

	public CommonProxy()
	{

	}

	private static ResourceLocation createRegistryName(String unlocalized)
	{
		unlocalized = unlocalized.substring(unlocalized.indexOf(ImmersiveIntelligence.MODID));
		unlocalized = unlocalized.replaceFirst("\\.", ":");
		return new ResourceLocation(unlocalized);
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		for(Block block : IIContent.blocks)
			event.getRegistry().register(block.setRegistryName(createRegistryName(block.getUnlocalizedName())));

		registerFeedthroughForWiretype(IIDataWireType.DATA, new ResourceLocation(ImmersiveIntelligence.MODID, "block/data_connector.obj"),
				new ResourceLocation(ImmersiveIntelligence.MODID, "blocks/data_connector_feedtrough"), new float[]{4, 4, 12, 12},
				0.09375, IIContent.block_data_connector.getStateFromMeta(IIBlockTypes_Connector.DATA_CONNECTOR.getMeta()),
				0, 0, (f) -> f);

	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		ImmersiveIntelligence.logger.info("Registering Items");

		for(Item item : IIContent.items)
			event.getRegistry().register(item.setRegistryName(createRegistryName(item.getUnlocalizedName())));

		registerOreDict();
	}

	@SubscribeEvent
	public static void registerPotions(RegistryEvent.Register<Potion> event)
	{
		/*POTIONS*/
		IIPotions.init();
		for(Block block : IIContent.blocks)
		{
			if(block instanceof BlockIIFluid&&((BlockIIFluid)block).isAcid)
				((BlockIIFluid)block).setPotionEffects(new PotionEffect(IIPotions.corrosion, 40, 1));
		}
	}

	public static <T extends TileEntity & IGuiTile> void openGuiForTile(@Nonnull EntityPlayer player, @Nonnull T tile)
	{
		player.openGui(ImmersiveIntelligence.INSTANCE, tile.getGuiID(), tile.getWorld(), tile.getPos().getX(),
				tile.getPos().getY(), tile.getPos().getZ());
	}

	public static <T extends TileEntity & IGuiTile> void openSpecificGuiForEvenMoreSpecificTile(@Nonnull EntityPlayer player, @Nonnull T tile, int gui)
	{
		player.openGui(ImmersiveIntelligence.INSTANCE, gui, tile.getWorld(), tile.getPos().getX(),
				tile.getPos().getY(), tile.getPos().getZ());
	}

	public static <T extends Entity & IGuiTile> void openGuiForEntity(@Nonnull EntityPlayer player, @Nonnull T entity, int gui)
	{
		player.openGui(ImmersiveIntelligence.INSTANCE, gui, entity.world, entity.getPosition().getX(),
				entity.getPosition().getY(), entity.getPosition().getZ());
	}

	public static void openGuiForItem(@Nonnull EntityPlayer player, @Nonnull EntityEquipmentSlot slot)
	{
		ItemStack stack = player.getItemStackFromSlot(slot);
		if(stack.isEmpty()||!(stack.getItem() instanceof IGuiItem))
			return;
		IGuiItem gui = (IGuiItem)stack.getItem();
		player.openGui(ImmersiveIntelligence.INSTANCE, gui.getGuiID(stack), player.world, (int)player.posX, (int)player.posY, (int)player.posZ);
	}

	public static void addConfiguredWorldgen(IBlockState state, String name, int[] config, EnumOreType type)
	{
		if(config!=null&&config.length >= 5&&config[0] > 0)
			IIWorldGen.addOreGen(name, state, config[0], config[1], config[2], config[3], config[4], type);
	}

	public static void registerTile(Class<? extends TileEntity> tile)
	{
		String s = tile.getSimpleName();
		s = s.substring(s.indexOf("TileEntity")+"TileEntity".length());
		GameRegistry.registerTileEntity(tile, new ResourceLocation(ImmersiveIntelligence.MODID+":"+s));
	}

	public static void registerOreDict()
	{
		OreDictionary.registerOre("electronTubeAdvanced", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("advanced_electron_tube")));
		OreDictionary.registerOre("transistor", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("transistor")));

		//Basic Circuit Board
		OreDictionary.registerOre("circuitBasic", new ItemStack(IEContent.itemMaterial, 1, 27));
		OreDictionary.registerOre("circuitBasicRaw", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("basic_circuit_board_raw")));
		OreDictionary.registerOre("circuitBasicEtched", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("basic_circuit_board_etched")));
		OreDictionary.registerOre("chipBasic", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("basic_electronic_element")));

		//Advanced Circuit Board
		OreDictionary.registerOre("circuitAdvancedRaw", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("advanced_circuit_board_raw")));
		OreDictionary.registerOre("circuitAdvancedEtched", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("advanced_circuit_board_etched")));
		OreDictionary.registerOre("chipAdvanced", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("advanced_electronic_element")));
		OreDictionary.registerOre("circuitAdvanced", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("advanced_circuit_board")));

		//Processor Circuit Board
		OreDictionary.registerOre("circuitProcessorEtched", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("processor_circuit_board_etched")));
		OreDictionary.registerOre("circuitProcessorRaw", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("processor_circuit_board_raw")));
		OreDictionary.registerOre("chipProcessor", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("processor_electronic_element")));
		OreDictionary.registerOre("circuitProcessor", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("processor_circuit_board")));

		OreDictionary.registerOre("circuitEliteEtched", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("processor_circuit_board_etched")));
		OreDictionary.registerOre("circuitEliteRaw", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("processor_circuit_board_raw")));
		OreDictionary.registerOre("circuitElite", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("processor_circuit_board")));
		OreDictionary.registerOre("chipElite", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("processor_electronic_element")));

		registerItemOredict(IIContent.item_material, "compact_electric_engine", "engineElectricSmall", "engineElectricCompact");
		registerItemOredict(IIContent.item_material, "compact_electric_engine_advanced", "engineElectricSmallAdvanced", "engineElectricCompactAdvanced");

		registerMetalOredict(IIContent.item_material_ingot, "ingot");
		registerMetalOredict(IIContent.item_material_plate, "plate");
		registerMetalOredict(IIContent.item_material_dust, "dust");
		registerMetalOredict(IIContent.item_material_nugget, "nugget");
		registerMetalOredict(IIContent.item_material_wire, "wire");
		registerMetalOredict(IIContent.item_material_spring, "spring");
		registerMetalOredict(IIContent.item_material_gem, "gem");
		registerMetalOredict(IIContent.item_material_boule, "boule");

		registerMetalOredictBlock(IIContent.block_ore, "ore");
		registerMetalOredictBlock(IIContent.block_sheetmetal, "sheetmetal");
		registerMetalOredictBlock(IIContent.block_sheetmetal_slabs, "slabSheetmetal");
		registerMetalOredictBlock(IIContent.block_metal_storage, "block");
		registerMetalOredictBlock(IIContent.block_metal_slabs, "slab");


		//Punchtapes
		OreDictionary.registerOre("punchtapeEmpty", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("punchtape_empty")));
		OreDictionary.registerOre("punchtape", new ItemStack(IIContent.item_punchtape, 1, 0));

		OreDictionary.registerOre("pageEmpty", new ItemStack(IIContent.item_printed_page, 1, 0));
		OreDictionary.registerOre("pageText", new ItemStack(IIContent.item_printed_page, 1, 1));
		OreDictionary.registerOre("pageWritten", new ItemStack(IIContent.item_printed_page, 1, 1));

		OreDictionary.registerOre("pageCode", new ItemStack(IIContent.item_printed_page, 1, 2));
		OreDictionary.registerOre("pageWritten", new ItemStack(IIContent.item_printed_page, 1, 2));
		OreDictionary.registerOre("pageBlueprint", new ItemStack(IIContent.item_printed_page, 1, 3));
		OreDictionary.registerOre("pageWritten", new ItemStack(IIContent.item_printed_page, 1, 3));

		OreDictionary.registerOre("materialTNT", new ItemStack(Blocks.TNT, 1, 0));
		OreDictionary.registerOre("materialRDX", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("dust_rdx")));
		OreDictionary.registerOre("materialHexogen", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("dust_rdx")));
		OreDictionary.registerOre("materialHMX", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("dust_hmx")));

		OreDictionary.registerOre("dustWhitePhosphorus", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("white_phosphorus")));
		OreDictionary.registerOre("whitePhosphorus", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("white_phosphorus")));

		OreDictionary.registerOre("dustSalt", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("dust_salt")));
		OreDictionary.registerOre("dustWood", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("dust_wood")));
		OreDictionary.registerOre("pulpWood", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("pulp_wood")));
		OreDictionary.registerOre("pulpWoodTreated", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("pulp_wood_treated")));
		OreDictionary.registerOre("dustHexamine", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("dust_hexamine")));
		OreDictionary.registerOre("dustFormaldehyde", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("dust_formaldehyde")));

		OreDictionary.registerOre("leatherArtificial", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("artificial_leather")));
		OreDictionary.registerOre("leather", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("artificial_leather")));

		OreDictionary.registerOre("brushCarbon", new ItemStack(IIContent.item_material, 1, IIContent.item_material.getMetaBySubname("carbon_brush")));

		OreDictionary.registerOre("listAllMeatRaw", Items.PORKCHOP);
		OreDictionary.registerOre("listAllMeatRaw", Items.BEEF);
		OreDictionary.registerOre("listAllMeatRaw", Items.FISH);
		OreDictionary.registerOre("listAllMeatRaw", Items.CHICKEN);
		OreDictionary.registerOre("listAllMeatRaw", Items.RABBIT);
		OreDictionary.registerOre("listAllMeatRaw", Items.MUTTON);

		registerMetalOredict(IIContent.item_motor_gear, "gear");
		registerMetalOredict(IIContent.item_motor_belt, "belt");
	}

	private static void registerMetalOredictBlock(BlockIIBase block, String dict)
	{
		for(int i = 0; i < block.enumValues.length; i += 1)
			OreDictionary.registerOre(Utils.toCamelCase(dict+"_"+block.enumValues[i].name().toLowerCase(), true), new ItemStack(block, 1, i));
	}

	private static void registerItemOredict(ItemIIBase item, String subname, String... dicts)
	{
		for(int i = 0; i < dicts.length; i += 1)
			OreDictionary.registerOre(Utils.toCamelCase(dicts[i], true), new ItemStack(item, 1, item.getMetaBySubname(subname)));
	}

	private static void registerMetalOredict(ItemIIBase item, String dict)
	{
		for(int i = 0; i < item.getSubNames().length; i += 1)
			OreDictionary.registerOre(Utils.toCamelCase(dict+"_"+item.getSubNames()[i].toLowerCase(), true), new ItemStack(item, 1, i));
	}

	@SubscribeEvent
	public static void onSave(WorldEvent.Save event)
	{
		IISaveData.setDirty(0);
	}

	@SubscribeEvent
	public static void onUnload(WorldEvent.Unload event)
	{
		IISaveData.setDirty(0);
	}

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event)
	{
		String sulfur = OreDictionary.doesOreNameExist("oreSulfur")?"oreSulfur": "dustSulfur";

		MineralMix mineralFluorite = ExcavatorHandler.addMineral("Fluorite", 25, .65f, new String[]{"oreFluorite", "oreQuartz"}, new float[]{.5f, .25f});
		MineralMix mineralPhosphorite = ExcavatorHandler.addMineral("Phosphorite", 30, .45f, new String[]{"orePhosphorus", sulfur, "oreIron", "oreAluminum"}, new float[]{.65f, .125f, 0.0625f, 0.0125f});
		mineralFluorite.dimensionWhitelist = new int[]{-1};
		mineralPhosphorite.dimensionWhitelist = new int[]{-1};

		ExcavatorHandler.addMineral("Wolframite", 15, .15f, new String[]{"oreTungsten", "oreIron"}, new float[]{.25f, .75f});
		ExcavatorHandler.addMineral("Ferberite", 10, .2f, new String[]{"oreTungsten", "oreIron", "oreTin"}, new float[]{.2f, .4f, .3f});

		LighterFuelHandler.addFuel(FluidRegistry.getFluid("creosote"), 100);
		LighterFuelHandler.addFuel(FluidRegistry.getFluid("ethanol"), 20);

		MachinegunCoolantHandler.addCoolant(FluidRegistry.WATER, 1);
		// LighterFuelHandler.addFuel(FluidRegistry.getFluid("creosote"),100);

		CrusherRecipe.addRecipe(Utils.getStackWithMetaName(IIContent.item_material_dust, "silicon"), new IngredientStack("plateSilicon"), 12000);

		final ItemStack powder = new ItemStack(IIContent.item_tracer_powder, 1, 0);
		event.getRegistry().register(new RecipeRGBColouration((s) -> (OreDictionary.itemMatches(powder, s, true)), (s) -> (ItemNBTHelper.hasKey(s, "colour")?ItemNBTHelper.getInt(s, "colour"): 0xffffff), (s, i) -> ItemNBTHelper.setInt(s, "colour", i)).setRegistryName(ImmersiveEngineering.MODID, "tracer_powder_colour"));

		IIRecipes.addMinecartRecipes(event.getRegistry());
		IIRecipes.addSmallCrateRecipes(event.getRegistry());

		for(int i = 0; i < IIContent.item_mold.getSubNames().length; i++)
			BlueprintCraftingRecipe.addRecipe("molds", new ItemStack(IIContent.item_mold, 1, i), "plateSteel", "plateSteel", "plateSteel", "plateSteel", "plateSteel", new ItemStack(IEContent.itemTool, 1, 1));

		//((IForgeRegistryModifiable)CraftingManager.REGISTRY).remove(new ResourceLocation(""));

		IIRecipes.addBulletPressRecipes();

		IIRecipes.addSiliconProcessingRecipes();
		IIRecipes.addCircuitRecipes();

		IIRecipes.addFunctionalCircuits();
		IIRecipes.addSpringRecipes();
		IIRecipes.addMiscIERecipes();

		IIRecipes.addRotaryPowerRecipes();

		IIRecipes.addRDXProductionRecipes();
		IIRecipes.addHMXProductionRecipes();

		IIRecipes.addConcreteRecipes();
		IIRecipes.addChemicalBathCleaningRecipes();

		//Immersive Engineering can into space???
		ElectrolyzerRecipe.addRecipe(FluidRegistry.getFluidStack("water", 3000), FluidRegistry.getFluidStack("oxygen", 1000), FluidRegistry.getFluidStack("hydrogen", 2000), 640, 320);
		ElectrolyzerRecipe.addRecipe(FluidRegistry.getFluidStack("brine", 3000), FluidRegistry.getFluidStack("chlorine", 1500), FluidRegistry.getFluidStack("hydrogen", 1500), 640, 320);


		IIRecipes.addInkRecipes();

		MixerRecipe.addRecipe(new FluidStack(IIContent.fluid_etching_acid, 1000), new FluidStack(IIContent.gas_chlorine, 500), new Object[]{"dustIron"}, 4800);
		MixerRecipe.addRecipe(new FluidStack(IIContent.fluid_sulfuric_acid, 500), new FluidStack(FluidRegistry.WATER, 1000), new Object[]{"dustSulfur"}, 4800);
		MixerRecipe.addRecipe(new FluidStack(IIContent.fluid_hydrofluoric_acid, 500), new FluidStack(IIContent.fluid_sulfuric_acid, 1000), new Object[]{"dustFluorite"}, 5600);
		MixerRecipe.addRecipe(new FluidStack(IIContent.fluid_nitric_acid, 250), new FluidStack(IIContent.fluid_sulfuric_acid, 1000), new Object[]{"dustSaltpeter"}, 5600);
		MixerRecipe.addRecipe(new FluidStack(IIContent.fluid_brine, 750), new FluidStack(FluidRegistry.WATER, 750), new Object[]{"dustSalt"}, 3200);
	}

	public static Fluid makeFluid(String name, int density, int viscosity)
	{
		return makeFluid(name, density, viscosity, "");
	}

	public static Fluid makeFluid(String name, int density, int viscosity, String prefix)
	{
		Fluid fl = new Fluid(
				name,
				new ResourceLocation(ImmersiveIntelligence.MODID+":blocks/fluid/"+prefix+name+"_still"),
				new ResourceLocation(ImmersiveIntelligence.MODID+":blocks/fluid/"+prefix+name+"_flow")
		).setDensity(density).setViscosity(viscosity);
		FluidRegistry.addBucketForFluid(fl);
		if(!FluidRegistry.registerFluid(fl))
			fl = FluidRegistry.getFluid(fl.getName());

		IICreativeTab.fluidBucketMap.add(fl);
		return fl;
	}

	public static void refreshFluidReferences()
	{
		IIContent.fluid_ink_black = FluidRegistry.getFluid("ink");
		IIContent.fluid_ink_cyan = FluidRegistry.getFluid("ink_cyan");
		IIContent.fluid_ink_magenta = FluidRegistry.getFluid("ink_magenta");
		IIContent.fluid_ink_yellow = FluidRegistry.getFluid("ink_yellow");

		IIContent.fluid_brine = FluidRegistry.getFluid("brine");
		IIContent.fluid_etching_acid = FluidRegistry.getFluid("etching_acid");
		IIContent.fluid_hydrofluoric_acid = FluidRegistry.getFluid("hydrofluoric_acid");
		IIContent.fluid_sulfuric_acid = FluidRegistry.getFluid("sulfuric_acid");

		IIContent.fluid_ammonia = FluidRegistry.getFluid("ammonia");
		IIContent.fluid_methanol = FluidRegistry.getFluid("methanol");

		IIContent.gas_chlorine = FluidRegistry.getFluid("chlorine");
		IIContent.gas_hydrogen = FluidRegistry.getFluid("hydrogen");
		IIContent.gas_oxygen = FluidRegistry.getFluid("oxygen");
	}


	public void preInit()
	{
		IIDataWireType.init();
		IIPacketHandler.preInit();
		CapabilityRotaryEnergy.register();
		IICompatModule.doModulesPreInit();
		IEContent.itemRailgun = new ItemIIRailgunOverride();

		//ALWAYS REGISTER BULLETS IN PRE-INIT! (so they get their texture registered before TextureStitchEvent.Pre)
		//Bullets

		BulletRegistry.INSTANCE.registerCasing(IIContent.item_ammo_artillery);
		BulletRegistry.INSTANCE.registerCasing(IIContent.item_ammo_autocannon);
		BulletRegistry.INSTANCE.registerCasing(IIContent.item_grenade);
		BulletRegistry.INSTANCE.registerCasing(IIContent.item_railgun_grenade);

		BulletRegistry.INSTANCE.registerCasing(IIContent.item_ammo_machinegun);
		BulletRegistry.INSTANCE.registerCasing(IIContent.item_ammo_submachinegun);
		BulletRegistry.INSTANCE.registerCasing(IIContent.item_ammo_storm_rifle);
		BulletRegistry.INSTANCE.registerCasing(IIContent.item_ammo_revolver);

		BulletRegistry.INSTANCE.registerComponent(new BulletComponentTNT());
		BulletRegistry.INSTANCE.registerComponent(new BulletComponentRDX());
		BulletRegistry.INSTANCE.registerComponent(new BulletComponentHMX());
		BulletRegistry.INSTANCE.registerComponent(new BulletComponentNuke());
		BulletRegistry.INSTANCE.registerComponent(new BulletComponentWhitePhosphorus());
		BulletRegistry.INSTANCE.registerComponent(new BulletComponentFirework());
		BulletRegistry.INSTANCE.registerComponent(new BulletComponentTracerPowder());

		BulletRegistry.INSTANCE.registerBulletCore(new BulletCoreSteel());
		BulletRegistry.INSTANCE.registerBulletCore(new BulletCoreTungsten());
		BulletRegistry.INSTANCE.registerBulletCore(new BulletCoreBrass());
		BulletRegistry.INSTANCE.registerBulletCore(new BulletCoreLead());
		BulletRegistry.INSTANCE.registerBulletCore(new BulletCoreUranium());
		BulletRegistry.INSTANCE.registerBulletCore(new BulletCorePabilium());

		//ShrapnelHandler.addShrapnel("wood","",1,0.25f,0f,true);

		ShrapnelHandler.addShrapnel("aluminum", 0xd9ecea, "immersiveengineering:textures/blocks/sheetmetal_aluminum", 1, 0.05f, 0f);
		ShrapnelHandler.addShrapnel("zinc", 0xdee3dc, "immersiveintelligence:textures/blocks/metal/sheetmetal_zinc", 1, 0.15f, 0f);
		ShrapnelHandler.addShrapnel("copper", 0xe37c26, "immersiveengineering:textures/blocks/sheetmetal_copper", 2, 0.25f, 0f);
		ShrapnelHandler.addShrapnel("platinum", 0xd8e1e1, "immersiveintelligence:textures/blocks/metal/sheetmetal_platinum", 2, 0.05f, 0f);
		ShrapnelHandler.addShrapnel("gold", 0xd1b039, "minecraft:textures/blocks/gold_block", 2, 0.25f, 0f);
		ShrapnelHandler.addShrapnel("nickel", 0x838877, "immersiveengineering:textures/blocks/sheetmetal_nickel", 2, 0.25f, 0f);
		ShrapnelHandler.addShrapnel("silver", 0xa7cac8, "immersiveengineering:textures/blocks/sheetmetal_silver", 2, 0.25f, 0f);
		ShrapnelHandler.addShrapnel("electrum", 0xf6ad59, "immersiveengineering:textures/blocks/sheetmetal_electrum", 2, 0.25f, 0f);
		ShrapnelHandler.addShrapnel("constantan", 0xf97456, "immersiveengineering:textures/blocks/sheetmetal_constantan", 3, 0.25f, 0f);
		ShrapnelHandler.addShrapnel("iron", 0xc7c7c7, "minecraft:textures/blocks/iron_block", 4, 0.25f, 0f);
		ShrapnelHandler.addShrapnel("lead", 0x3a3e44, "immersiveengineering:textures/blocks/sheetmetal_lead", 5, 0.75f, 0f);
		ShrapnelHandler.addShrapnel("steel", 0x4d4d4d, "immersiveengineering:textures/blocks/sheetmetal_steel", 6, 0.35f, 0f);
		ShrapnelHandler.addShrapnel("brass", 0x957743, "immersiveintelligence:textures/blocks/metal/sheetmetal_brass", 6, 0.35f, 0f);
		ShrapnelHandler.addShrapnel("tungsten", 0x3b3e43, "immersiveintelligence:textures/blocks/metal/sheetmetal_tungsten", 8, 0.45f, 0f);
		ShrapnelHandler.addShrapnel("HOPGraphite", 0x282828, "immersiveengineering:textures/blocks/stone_decoration_coke", 8, 0.45f, 0f);
		ShrapnelHandler.addShrapnel("uranium", 0x659269, "immersiveengineering:textures/blocks/sheetmetal_uranium", 12, 0.45f, 8f);

		for(Entry<String, Shrapnel> s : ShrapnelHandler.registry.entrySet())
		{
			BulletComponentShrapnel shrapnel = new BulletComponentShrapnel(s.getKey());
			BulletRegistry.INSTANCE.registerComponent(shrapnel);
		}

		BulletHandler.registerBullet("ii_bullet", IIContent.item_ammo_revolver);
	}

	public void init()
	{
		IICompatModule.doModulesInit();
		reInitGui();

		//Blocks config
		IIContent.block_ore.setMiningLevels();

		//Worldgen registration
		IIWorldGen iiWorldGen = new IIWorldGen();
		GameRegistry.registerWorldGenerator(iiWorldGen, 0);
		MinecraftForge.EVENT_BUS.register(iiWorldGen);

		ImmersiveIntelligence.logger.info("Adding oregen");
		addConfiguredWorldgen(IIContent.block_ore.getStateFromMeta(IIBlockTypes_Ore.PLATINUM.getMeta()), "platinum", Ores.ore_platinum, EnumOreType.OVERWORLD);
		addConfiguredWorldgen(IIContent.block_ore.getStateFromMeta(IIBlockTypes_Ore.ZINC.getMeta()), "zinc", Ores.ore_zinc, EnumOreType.OVERWORLD);
		addConfiguredWorldgen(IIContent.block_ore.getStateFromMeta(IIBlockTypes_Ore.TUNGSTEN.getMeta()), "tungsten", Ores.ore_tungsten, EnumOreType.OVERWORLD);
		addConfiguredWorldgen(IIContent.block_ore.getStateFromMeta(IIBlockTypes_Ore.SALT.getMeta()), "salt", Ores.ore_salt, EnumOreType.OVERWORLD);
		addConfiguredWorldgen(IIContent.block_ore.getStateFromMeta(IIBlockTypes_Ore.FLUORITE.getMeta()), "fluorite", Ores.ore_fluorite, EnumOreType.NETHER);
		addConfiguredWorldgen(IIContent.block_ore.getStateFromMeta(IIBlockTypes_Ore.PHOSPHORUS.getMeta()), "phosphorus", Ores.ore_phosphorus, EnumOreType.NETHER);

		RailgunHandler.registerProjectileProperties(new IngredientStack("stickTungsten"), 32, 1.3).setColourMap(new int[][]{{0xCBD1D6, 0xCBD1D6, 0xCBD1D6, 0xCBD1D6, 0x9EA2A7, 0x9EA2A7}});

		//Disallow crates in crates
		IEApi.forbiddenInCrates.add((stack) ->
		{
			if(OreDictionary.itemMatches(new ItemStack(IIContent.block_metal_device, 1, 0), stack, true))
				return true;
			if(stack.getItem()==IIContent.item_minecart&&stack.getMetadata() < ItemIIMinecart.META_MINECART_WOODEN_BARREL)
				return true;
			return OreDictionary.itemMatches(new ItemStack(IIContent.block_metal_device, 1, 1), stack, true);
		});

		IIContent.tileEntitiesWeDontLike.add(tileEntity -> tileEntity instanceof TileEntityChargingStation);

		IEApi.forbiddenInCrates.add((stack) -> stack.getItem() instanceof ItemBlockIEBase&&((ItemBlockIEBase)stack.getItem()).getBlock() instanceof BlockIISmallCrate);


		ImmersiveIntelligence.logger.info("Adding TileEntities");
		registerTile(TileEntityMetalCrate.class);
		registerTile(TileEntityAmmunitionCrate.class);
		registerTile(TileEntitySmallCrate.class);
		registerTile(TileEntityAlarmSiren.class);
		registerTile(TileEntityProgrammableSpeaker.class);
		registerTile(TileEntityMedicalCrate.class);
		registerTile(TileEntityRepairCrate.class);

		registerTile(TileEntityInserter.class);
		registerTile(TileEntityAdvancedInserter.class);
		registerTile(TileEntityFluidInserter.class);

		registerTile(TileEntityTimedBuffer.class);
		registerTile(TileEntityRedstoneBuffer.class);
		registerTile(TileEntitySmallDataBuffer.class);
		registerTile(TileEntityDataMerger.class);
		registerTile(TileEntityDataRouter.class);
		registerTile(TileEntityDataDebugger.class);
		registerTile(TileEntityPunchtapeReader.class);
		registerTile(TileEntityChemicalDispenser.class);

		registerTile(TileEntityDataConnector.class);
		registerTile(TileEntityDataRelay.class);
		registerTile(TileEntityDataCallbackConnector.class);

		registerTile(TileEntitySandbags.class);

		registerTile(TileEntityMechanicalWheel.class);
		registerTile(TileEntityGearbox.class);
		registerTile(TileEntityTransmissionBoxCreative.class);
		registerTile(TileEntityTransmissionBox.class);
		registerTile(TileEntityMechanicalPump.class);

		registerTile(TileEntityTankTrap.class);
		registerTile(TileEntityChainFence.class);

		registerTile(TileEntitySkyCratePost.class);
		registerTile(TileEntitySkyCrateStation.class);
		registerTile(TileEntitySkyCartStation.class);

		registerTile(TileEntitySawmill.class);

		registerTile(TileEntityRadioStation.class);
		registerTile(TileEntityDataInputMachine.class);
		registerTile(TileEntityArithmeticLogicMachine.class);
		registerTile(TileEntityPrintingPress.class);
		registerTile(TileEntityChemicalBath.class);
		registerTile(TileEntityElectrolyzer.class);
		registerTile(TileEntityConveyorScanner.class);
		registerTile(TileEntityPrecissionAssembler.class);
		registerTile(TileEntityArtilleryHowitzer.class);
		registerTile(TileEntityAmmunitionFactory.class);
		registerTile(TileEntityBallisticComputer.class);
		registerTile(TileEntityPacker.class);
		registerTile(TileEntityRedstoneInterface.class);
		registerTile(TileEntityEmplacement.class);

		//Wooden
		MultiblockHandler.registerMultiblock(MultiblockSkyCratePost.instance);
		MultiblockHandler.registerMultiblock(MultiblockSkyCrateStation.instance);
		MultiblockHandler.registerMultiblock(MultiblockSkyCartStation.instance);
		MultiblockHandler.registerMultiblock(MultiblockSawmill.instance);

		//Metal0
		MultiblockHandler.registerMultiblock(MultiblockRadioStation.instance);
		MultiblockHandler.registerMultiblock(MultiblockDataInputMachine.instance);
		MultiblockHandler.registerMultiblock(MultiblockArithmeticLogicMachine.instance);
		MultiblockHandler.registerMultiblock(MultiblockPrintingPress.instance);
		MultiblockHandler.registerMultiblock(MultiblockChemicalBath.instance);
		MultiblockHandler.registerMultiblock(MultiblockElectrolyzer.instance);
		MultiblockHandler.registerMultiblock(MultiblockConveyorScanner.instance);
		MultiblockHandler.registerMultiblock(MultiblockPrecissionAssembler.instance);
		MultiblockHandler.registerMultiblock(MultiblockArtilleryHowitzer.instance);
		MultiblockHandler.registerMultiblock(MultiblockAmmunitionFactory.instance);
		MultiblockHandler.registerMultiblock(MultiblockBallisticComputer.instance);
		MultiblockHandler.registerMultiblock(MultiblockPacker.instance);

		//Metal1
		MultiblockHandler.registerMultiblock(MultiblockRedstoneInterface.instance);
		MultiblockHandler.registerMultiblock(MultiblockEmplacement.instance);

		int i = -1;
		EntityRegistry.registerModEntity(new ResourceLocation(ImmersiveIntelligence.MODID, "minecart_wooden_crate"),
				EntityMinecartCrateWooden.class, "minecart_wooden_crate", i++, ImmersiveIntelligence.INSTANCE, 64, 1,
				true);
		EntityRegistry.registerModEntity(new ResourceLocation(ImmersiveIntelligence.MODID, "minecart_reinforced_crate"),
				EntityMinecartCrateReinforced.class, "minecart_reinforced_crate", i++, ImmersiveIntelligence.INSTANCE,
				64, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(ImmersiveIntelligence.MODID, "minecart_steel_crate"),
				EntityMinecartCrateSteel.class, "minecart_steel_crate", i++, ImmersiveIntelligence.INSTANCE, 64, 1,
				true);
		EntityRegistry.registerModEntity(new ResourceLocation(ImmersiveIntelligence.MODID, "minecart_wooden_barrel"),
				EntityMinecartBarrelWooden.class, "minecart_wooden_barrel", i++, ImmersiveIntelligence.INSTANCE, 64, 1,
				true);
		EntityRegistry.registerModEntity(new ResourceLocation(ImmersiveIntelligence.MODID, "minecart_metal_barrel"),
				EntityMinecartBarrelSteel.class, "minecart_metal_barrel", i++, ImmersiveIntelligence.INSTANCE, 64, 1,
				true);

		//Entities

		//Finally Skycrates are a thing! ^^
		EntityRegistry.registerModEntity(new ResourceLocation(ImmersiveIntelligence.MODID, "skycrate"),
				EntitySkyCrate.class, "skycrate", i++, ImmersiveIntelligence.INSTANCE, 64, 1, true);

		EntityRegistry.registerModEntity(new ResourceLocation(ImmersiveIntelligence.MODID, "bullet"),
				EntityBullet.class, "bullet", i++, ImmersiveIntelligence.INSTANCE, 32, 1, true);

		EntityRegistry.registerModEntity(new ResourceLocation(ImmersiveIntelligence.MODID, "shrapnel"),
				EntityShrapnel.class, "shrapnel", i++, ImmersiveIntelligence.INSTANCE, 16, 1, true);

		EntityRegistry.registerModEntity(new ResourceLocation(ImmersiveIntelligence.MODID, "machinegun"),
				EntityMachinegun.class, "machinegun", i++, ImmersiveIntelligence.INSTANCE, 64, 1, true);

		EntityRegistry.registerModEntity(new ResourceLocation(ImmersiveIntelligence.MODID, "camera"),
				EntityCamera.class, "camera", i++, ImmersiveIntelligence.INSTANCE, 1, 1, true);

		EntityRegistry.registerModEntity(new ResourceLocation(ImmersiveIntelligence.MODID, "skycrate_internal"),
				EntitySkycrateInternal.class, "skycrate_internal", i++, ImmersiveIntelligence.INSTANCE, 64, 1, true);

		EntityRegistry.registerModEntity(new ResourceLocation(ImmersiveIntelligence.MODID, "motorbike"),
				EntityMotorbike.class, "motorbike", i++, ImmersiveIntelligence.INSTANCE, 64, 1, true);

		EntityRegistry.registerModEntity(new ResourceLocation(ImmersiveIntelligence.MODID, "field_howitzer"),
				EntityFieldHowitzer.class, "field_howitzer", i++, ImmersiveIntelligence.INSTANCE, 64, 1, true);

		EntityRegistry.registerModEntity(new ResourceLocation(ImmersiveIntelligence.MODID, "seat"),
				EntityVehicleSeat.class, "seat", i++, ImmersiveIntelligence.INSTANCE, 64, 1, true);

		EntityRegistry.registerModEntity(new ResourceLocation(ImmersiveIntelligence.MODID, "atomic_boom"),
				EntityAtomicBoom.class, "atomic_boom", i++, ImmersiveIntelligence.INSTANCE, 64, 1, true);
	}

	public void postInit()
	{
		IICompatModule.doModulesPostInit();

		MinecartBlockHelper.blocks.put((stack -> blusunrize.immersiveengineering.common.util.Utils.getBlockFromItem(stack.getItem())==IEContent.blockWoodenDevice0&&stack.getMetadata()==BlockTypes_WoodenDevice0.CRATE.getMeta()),
				EntityMinecartCrateWooden::new);
		MinecartBlockHelper.blocks.put((stack -> blusunrize.immersiveengineering.common.util.Utils.getBlockFromItem(stack.getItem())==IEContent.blockWoodenDevice0&&stack.getMetadata()==BlockTypes_WoodenDevice0.REINFORCED_CRATE.getMeta()),
				EntityMinecartCrateReinforced::new);
		MinecartBlockHelper.blocks.put((stack -> blusunrize.immersiveengineering.common.util.Utils.getBlockFromItem(stack.getItem())==IIContent.block_metal_device&&stack.getMetadata()==IIBlockTypes_MetalDevice.METAL_CRATE.getMeta()),
				EntityMinecartCrateSteel::new);
		MinecartBlockHelper.blocks.put((stack -> blusunrize.immersiveengineering.common.util.Utils.getBlockFromItem(stack.getItem())==IEContent.blockWoodenDevice0&&stack.getMetadata()==BlockTypes_WoodenDevice0.BARREL.getMeta()),
				EntityMinecartBarrelWooden::new);
		MinecartBlockHelper.blocks.put((stack -> blusunrize.immersiveengineering.common.util.Utils.getBlockFromItem(stack.getItem())==IEContent.blockMetalDevice0&&stack.getMetadata()==BlockTypes_MetalDevice0.BARREL.getMeta()),
				EntityMinecartBarrelSteel::new);

		RotaryUtils.ie_rotational_blocks_torque.put(tileEntity -> tileEntity instanceof TileEntityWindmill,
				aFloat -> aFloat*MechanicalDevices.dynamo_windmill_torque
		);

		RotaryUtils.ie_rotational_blocks_torque.put(tileEntity -> tileEntity instanceof TileEntityWatermill,
				aFloat -> aFloat*MechanicalDevices.dynamo_watermill_torque
		);

		IIRecipes.addWoodTableSawRecipes();

		CorrosionHandler.addItemToBlacklist(new ItemStack(Items.DIAMOND_HELMET));
		CorrosionHandler.addItemToBlacklist(new ItemStack(Items.DIAMOND_CHESTPLATE));
		CorrosionHandler.addItemToBlacklist(new ItemStack(Items.DIAMOND_LEGGINGS));
		CorrosionHandler.addItemToBlacklist(new ItemStack(Items.DIAMOND_BOOTS));
	}

	public void reInitGui()
	{

	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		ItemStack stack = player.getHeldItem(player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof IGuiItem?EnumHand.MAIN_HAND: EnumHand.OFF_HAND);

		Container gui;
		if(IIGuiList.values().length > ID)
		{
			IIGuiList guiBuilder = IIGuiList.values()[ID];
			// TODO: 03.10.2020 items with server side container inventory
			if(guiBuilder.item)
				return null;
			else if(te instanceof IGuiTile&&guiBuilder.teClass.isInstance(te))
			{
				gui = guiBuilder.container.apply(player, te);
				if(gui!=null)
				{
					((IGuiTile)te).onGuiOpened(player, true);
					return gui;
				}
			}
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return getServerGuiElement(ID, player, world, x, y, z);
	}

	public void renderTile(TileEntity te)
	{
	}

	public void startSkyhookSound(EntitySkyCrate entitySkyCrate)
	{
	}

	public void onServerGuiChangeRequest(TileEntity tile, int gui, EntityPlayer player)
	{
		if(!(tile instanceof IGuiTile)||((IGuiTile)tile).getGuiMaster()==null)
			return;

		//I like casting things
		IGuiTile te = ((IGuiTile)((IGuiTile)tile).getGuiMaster());
		if(!((TileEntity)te).getWorld().isRemote&&te.canOpenGui(player))
		{
			openSpecificGuiForEvenMoreSpecificTile(player, (TileEntity & IGuiTile)te, gui);
		}
	}

	//Cancel when using a machinegun
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onItemUse(PlayerInteractEvent.RightClickBlock event)
	{
		if(event.getEntity().isRiding()&&event.getEntity().getRidingEntity() instanceof EntityMachinegun)
		{
			event.setResult(Result.DENY);
			event.setCanceled(true);
		}
	}

	//Cancel when using a machinegun
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onBlockUse(PlayerInteractEvent.RightClickItem event)
	{
		if(event.getEntity().isRiding()&&event.getEntity().getRidingEntity() instanceof EntityMachinegun)
		{
			event.setResult(Result.DENY);
			event.setCanceled(true);
		}
	}

	//Shooting
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onEmptyRightclick(PlayerInteractEvent.RightClickEmpty event)
	{
		if(event.getEntity().isRiding()&&event.getEntity().getRidingEntity() instanceof EntityMachinegun)
		{
			event.setResult(Result.DENY);
		}
	}

	@SubscribeEvent
	public void onBreakBlock(BreakEvent event)
	{
		DamageBlockPos dpos = null;
		for(DamageBlockPos g : PenetrationRegistry.blockDamage)
		{
			if(g.dimension==event.getWorld().provider.getDimension()&&event.getPos().equals(g)) ;
			{
				dpos = g;
				break;
			}
		}
		if(dpos!=null)
		{
			PenetrationRegistry.blockDamage.remove(dpos);
			dpos.damage = 0;
			IIPacketHandler.INSTANCE.sendToAllAround(new MessageBlockDamageSync(dpos), Utils.targetPointFromPos(dpos, event.getWorld(), 32));
		}
	}

	@SubscribeEvent
	public void onMultiblockForm(MultiblockFormEvent.Post event)
	{
		if(event.isCancelable()&&!event.isCanceled()&&event.getMultiblock().getClass().isAnnotationPresent(IAdvancedMultiblock.class))
		{
			//Required by Advanced Structures!
			if(!pl.pabilo8.immersiveintelligence.api.Utils.isAdvancedHammer(event.getHammer()))
			{
				if(!event.getEntityPlayer().getEntityWorld().isRemote)
					ImmersiveEngineering.packetHandler.sendTo(new MessageNoSpamChatComponents(new TextComponentTranslation(CommonProxy.INFO_KEY+"requires_advanced_hammer")), (EntityPlayerMP)event.getEntityPlayer());
				event.setCanceled(true);
			}
		}
	}

	public void reloadModels()
	{

	}

	public static MachineUpgrade createMachineUpgrade(String name)
	{
		return new MachineUpgrade(new ResourceLocation(ImmersiveIntelligence.MODID, name), new ResourceLocation(ImmersiveIntelligence.MODID, "textures/gui/upgrade/"+name));
	}

	@Override
	public void ticketsLoaded(List<Ticket> tickets, World world)
	{

	}
}
