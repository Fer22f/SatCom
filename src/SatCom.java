package fer22f.mods.satcom;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid="SatCom", name="SatCom", version="0.0.0")
@NetworkMod(clientSideRequired=true)
public class SatCom {

	@Instance(value = "SatCom")
    public static SatCom instance;
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent event) {
            
    }
}
