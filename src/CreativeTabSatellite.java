package fer22f.mods.satcom;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabSatellite extends CreativeTabs {

	public CreativeTabSatellite() {
		super(CreativeTabs.getNextID(), "SatCom");
	}
	
	@SideOnly(Side.CLIENT)
    public int getTabIconItemIndex()
    {
        return SatCom.BlockSatellite.blockID;
    }

}
