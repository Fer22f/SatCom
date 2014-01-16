package fer22f.mods.satcom.gui;

import fer22f.mods.satcom.SatCom;
import fer22f.mods.satcom.tile.TileEntityController;
import fer22f.mods.satcom.tile.TileEntitySatellite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerController extends Container {
	
	private TileEntityController tile;
	
	public ContainerController(IInventory inventory, TileEntityController tile) {
		this.tile = tile;
        int i;
        int j;

        for (i = 0; i < 3; ++i)
        {
            for (j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
        }
	}


	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tile.isUseableByPlayer(player);
	}

}
