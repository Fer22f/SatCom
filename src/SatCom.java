package fer22f.mods.satcom;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import fer22f.mods.satcom.block.BlockNiobiumOre;
import fer22f.mods.satcom.block.BlockSatellite;
import fer22f.mods.satcom.block.NiobiumGenerator;
import fer22f.mods.satcom.gui.GuiHandler;
import fer22f.mods.satcom.tile.TileEntitySatellite;

@Mod(modid="SatCom", name="SatCom", version="0.0.0")
@NetworkMod(clientSideRequired=true)
public class SatCom {

	@Instance(value = "SatCom")
    public static SatCom instance;
	
	public static CreativeTabs tabSatellite = new CreativeTabSatellite();
	
	public static int satelliteID = 500;
	public static Block BlockSatellite = new BlockSatellite();
	public static Block BlockNiobiumOre = new BlockNiobiumOre(501);
	public static Item module = new Item(5000).setUnlocalizedName("module").setCreativeTab(tabSatellite).setTextureName("satcom:module").setMaxStackSize(1);
	public static Item moduleGPS = new Item(5001).setUnlocalizedName("moduleGPS").setCreativeTab(tabSatellite).setTextureName("satcom:moduleGPS").setMaxStackSize(1);
	public static Item moduleLaser = new Item(5002).setUnlocalizedName("moduleLaser").setCreativeTab(tabSatellite).setTextureName("satcom:moduleLaser").setMaxStackSize(1);
	public static Item moduleWireless = new Item(5003).setUnlocalizedName("moduleWireless").setCreativeTab(tabSatellite).setTextureName("satcom:moduleWireless").setMaxStackSize(1);
	public static Item niobiumIngot = new Item(5004).setUnlocalizedName("niobiumIngot").setCreativeTab(tabSatellite).setTextureName("satcom:niboumIngot");
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent event) {
            GameRegistry.registerBlock(BlockSatellite, "satellite");
            LanguageRegistry.addName(BlockSatellite, "Satellite");
            GameRegistry.registerTileEntity(TileEntitySatellite.class, "Satellite");
            NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
            LanguageRegistry.instance().addStringLocalization("itemGroup.SatCom", "SatCom");
            GameRegistry.registerBlock(BlockNiobiumOre, "niobiumOre");
            LanguageRegistry.addName(BlockNiobiumOre, "Niobium Ore");
            MinecraftForge.setBlockHarvestLevel(BlockNiobiumOre, 2, "pickaxe", 2);
            MinecraftForge.setBlockHarvestLevel(BlockNiobiumOre, 3, "pickaxe", 2);
            MinecraftForge.setBlockHarvestLevel(BlockNiobiumOre, 4, "pickaxe", 2);
            GameRegistry.registerWorldGenerator(new NiobiumGenerator());
            
            LanguageRegistry.addName(module, "Module");
            LanguageRegistry.addName(moduleGPS, "GPS Module");
            LanguageRegistry.addName(moduleLaser, "Laser Module");
            LanguageRegistry.addName(moduleWireless, "Wireless Module");
            
    }
}
