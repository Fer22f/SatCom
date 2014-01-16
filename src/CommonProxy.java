package fer22f.mods.satcom;

import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

	public void preInit()
    {
		MinecraftForge.EVENT_BUS.register(new WorldHandler());
    }
}
