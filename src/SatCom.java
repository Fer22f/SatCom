package fer22f.mods.satcom;

import net.minecraft.block.Block;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import fer22f.mods.satcom.block.BlockSatellite;
import fer22f.mods.satcom.tile.TileSatellite;

@Mod(modid="SatCom", name="SatCom", version="0.0.0")
@NetworkMod(clientSideRequired=true)
public class SatCom {

	@Instance(value = "SatCom")
    public static SatCom instance;
	
	public static int satelliteID = 500;
	public static Block BlockSatellite = new BlockSatellite();
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent event) {
            GameRegistry.registerBlock(BlockSatellite, "satellite");
            LanguageRegistry.addName(BlockSatellite, "Satellite");
            GameRegistry.registerTileEntity(TileSatellite.class, "Satellite");
    }
}
