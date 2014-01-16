package fer22f.mods.satcom;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.ICraftingHandler;

public class CraftingHandler implements ICraftingHandler {

	@Override
	public void onCrafting(EntityPlayer player, ItemStack item,
			IInventory craftMatrix) {
		if (item.itemID == SatCom.module.itemID)
        {
            player.addStat(SatCom.moduleAchieve, 1);
        } else if (item.itemID == SatCom.Satellite.blockID)
        {
        	player.addStat(SatCom.satelliteAchieve, 1);
        }
	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) {
		
	}

}
