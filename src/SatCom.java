package fer22f.mods.satcom;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import fer22f.mods.satcom.block.BlockController;
import fer22f.mods.satcom.block.BlockNiobiumOre;
import fer22f.mods.satcom.block.BlockNiobiumBlock;
import fer22f.mods.satcom.block.BlockRocketLauncher;
import fer22f.mods.satcom.block.BlockSatellite;
import fer22f.mods.satcom.block.BlockAntenna;
import fer22f.mods.satcom.block.NiobiumGenerator;
import fer22f.mods.satcom.gui.GuiHandler;
import fer22f.mods.satcom.tile.TileEntityController;
import fer22f.mods.satcom.tile.TileEntityRocketLauncher;
import fer22f.mods.satcom.tile.TileEntitySatellite;

@Mod(modid="SatCom", name="SatCom", version="0.0.0")
@NetworkMod(clientSideRequired=true, serverSideRequired=false,
channels={"IDUpdate","rocketexplosion","updateSatellite"}, packetHandler = PacketHandler.class)
public class SatCom {

	@Instance(value = "SatCom")
    public static SatCom instance;
	
	@SidedProxy(clientSide="fer22f.mods.satcom.ClientProxy", serverSide="fer22f.mods.satcom.CommonProxy")
	public static CommonProxy proxy;
	
		
	public static CreativeTabs tabSatellite = new CreativeTabSatellite();
	
	public static Block Satellite = new BlockSatellite(500);
	public static Block NiobiumOre = new BlockNiobiumOre(501);
	public static Block Antenna = new BlockAntenna(502);
	public static Block NiobiumBlock = new BlockNiobiumBlock(503);
	public static Block RocketLauncher = new BlockRocketLauncher(504);
	public static Block Controller = new BlockController(505);
		
	public static Item module = new Item(5000).setUnlocalizedName("module").setCreativeTab(tabSatellite).setTextureName("satcom:module").setMaxStackSize(1);
	public static Item moduleLaser = new Item(5001).setUnlocalizedName("moduleLaser").setCreativeTab(tabSatellite).setTextureName("satcom:moduleLaser").setMaxStackSize(1);
	public static Item moduleWeather = new Item(5002).setUnlocalizedName("moduleWeather").setCreativeTab(tabSatellite).setTextureName("satcom:moduleWeather").setMaxStackSize(1);
	public static Item moduleWireless = new Item(5003).setUnlocalizedName("moduleWireless").setCreativeTab(tabSatellite).setTextureName("satcom:moduleWireless").setMaxStackSize(1);
	public static Item niobiumIngot = new Item(5004).setUnlocalizedName("niobiumIngot").setCreativeTab(tabSatellite).setTextureName("satcom:niobiumIngot");
	public static Item rocketFuel = new Item(5005).setUnlocalizedName("rocketFuel").setCreativeTab(tabSatellite).setTextureName("satcom:rocketFuel").setMaxStackSize(1);
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent event) {
		
		
		GameRegistry.registerBlock(NiobiumOre, "niobiumOre");
        LanguageRegistry.addName(NiobiumOre, "Niobium Ore");
        MinecraftForge.setBlockHarvestLevel(NiobiumOre, 2, "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(NiobiumOre, 3, "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(NiobiumOre, 4, "pickaxe", 2);
        GameRegistry.registerWorldGenerator(new NiobiumGenerator());
        LanguageRegistry.addName(niobiumIngot, "Niobium Ingot");
        FurnaceRecipes.smelting().addSmelting(NiobiumOre.blockID, new ItemStack(niobiumIngot), 1F);
				
        GameRegistry.registerBlock(Satellite, "satellite");
        LanguageRegistry.addName(Satellite, "Satellite");
        GameRegistry.registerTileEntity(TileEntitySatellite.class, "Satellite");
        NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
        LanguageRegistry.instance().addStringLocalization("itemGroup.SatCom", "SatCom");
        CraftingManager.getInstance().addRecipe(new ItemStack(Satellite), "XXX", "RMR", "XXX", 'X', niobiumIngot,'R', Item.redstone, 'M', module);    
        
        GameRegistry.registerBlock(Antenna, "antenna");
        LanguageRegistry.addName(Antenna, "Antenna");
        CraftingManager.getInstance().addRecipe(new ItemStack(Antenna), "I I", "IRI", "III", 'I', Item.ingotIron, 'R', Item.redstone);
        
        GameRegistry.registerBlock(Controller, "controller");
        LanguageRegistry.addName(Controller, "Controller");
        GameRegistry.registerTileEntity(TileEntityController.class, "Controller");
        
        GameRegistry.registerBlock(NiobiumBlock, "niobiumBlock");
        LanguageRegistry.addName(NiobiumBlock, "Niobium Block");
        CraftingManager.getInstance().addRecipe(new ItemStack(NiobiumBlock), "XXX", "XXX", "XXX", 'X', niobiumIngot);
        CraftingManager.getInstance().addRecipe(new ItemStack(niobiumIngot), "X", 'X', NiobiumBlock);
        
        GameRegistry.registerBlock(RocketLauncher, "rocketLauncher");
        LanguageRegistry.addName(RocketLauncher, "Rocket Launcher");
        GameRegistry.registerTileEntity(TileEntityRocketLauncher.class, "RocketLauncher");
        CraftingManager.getInstance().addRecipe(new ItemStack(RocketLauncher), "XXX", "RTR", "XXX", 'X', niobiumIngot,'R', Item.redstone, 'T', Block.tnt);
        
        LanguageRegistry.addName(rocketFuel, "Rocket Fuel");
        CraftingManager.getInstance().addRecipe(new ItemStack(rocketFuel), "RGR", "GTG", "RGR", 'R', Item.redstone,'G', Item.gunpowder, 'T', Item.ghastTear);
        CraftingManager.getInstance().addRecipe(new ItemStack(rocketFuel), "GRG", "RTR", "GRG", 'R', Item.redstone,'G', Item.gunpowder, 'T', Item.ghastTear);
        
        LanguageRegistry.addName(module, "Module");
        LanguageRegistry.addName(moduleLaser, "Laser Module");
        LanguageRegistry.addName(moduleWeather, "Cloud Destroyer Module");
        LanguageRegistry.addName(moduleWireless, "Wireless Module");
        CraftingManager.getInstance().addRecipe(new ItemStack(module), "CGC","GRG","CGC", 'C', new ItemStack(Item.dyePowder, 1, 2), 'G', Item.goldNugget, 'R', Item.redstone);
        CraftingManager.getInstance().addRecipe(new ItemStack(module), "GCG","CRC","GCG", 'C', new ItemStack(Item.dyePowder, 1, 2), 'G', Item.goldNugget, 'R', Item.redstone);
        CraftingManager.getInstance().addRecipe(new ItemStack(moduleLaser), "DRD", "RMR", "DRD", 'M', module, 'R', Item.redstone, 'D', Item.diamond);
        CraftingManager.getInstance().addRecipe(new ItemStack(moduleLaser), "RDR", "DMD", "RDR", 'M', module, 'R', Item.redstone, 'D', Item.diamond);
        CraftingManager.getInstance().addRecipe(new ItemStack(moduleWeather), "RDR", "DMD", "RDR", 'M', module, 'D', Item.bucketWater, 'R', Item.redstone);
        CraftingManager.getInstance().addRecipe(new ItemStack(moduleWeather), "DRD", "RMR", "DRD", 'M', module, 'D', Item.bucketWater, 'R', Item.redstone);
        CraftingManager.getInstance().addRecipe(new ItemStack(moduleWireless), "TRT", "RMR", "TRT", 'M', module, 'R', Item.redstone, 'T', Block.torchRedstoneActive);
        CraftingManager.getInstance().addRecipe(new ItemStack(moduleWireless), "RTR", "TMT", "RTR", 'M', module, 'R', Item.redstone, 'T', Block.torchRedstoneActive);
	
        proxy.preInit();
	}
	
	public static boolean isModule(ItemStack m)
	{
		if (m == null)
			return false;
		
		if (m.itemID == SatCom.moduleLaser.itemID ||
        		m.itemID == SatCom.moduleWeather.itemID ||
        		m.itemID == SatCom.moduleWireless.itemID ||
        		m.itemID == SatCom.module.itemID)
		{
			return true;
		}
		return false;
	}
	
	public static Item getItemfromModuleName(String name)
	{
		if (name.equalsIgnoreCase("moduleLaser"))
		{
			return moduleLaser;
		} else if (name.equalsIgnoreCase("moduleWireless"))
		{
			return moduleWireless;
		} else if (name.equalsIgnoreCase("moduleWeather"))
		{
			return moduleWeather;
		} else {		
			return module;
		}
	}
}
