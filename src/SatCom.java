package fer22f.mods.satcom;

import net.minecraft.block.Block;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import fer22f.mods.satcom.block.BlockSatellite;

@Mod(modid="SatCom", name="SatCom", version="0.0.0")
@NetworkMod(clientSideRequired=true)
public class SatCom {

	@Instance(value = "SatCom")
    public static SatCom instance;
	
	public static int satelliteID = 500;
	public static Block BlockSatellite = new BlockSatellite();
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent event) {
            
    }
}
