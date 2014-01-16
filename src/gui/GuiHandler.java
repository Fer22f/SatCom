package fer22f.mods.satcom.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import fer22f.mods.satcom.tile.TileEntityController;
import fer22f.mods.satcom.tile.TileEntityRocketLauncher;
import fer22f.mods.satcom.tile.TileEntitySatellite;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity t = world.getBlockTileEntity(x, y, z);
		if (t instanceof TileEntitySatellite)
		{
			return new ContainerSatellite(player.inventory, (TileEntitySatellite)t);
		}
		if (t instanceof TileEntityRocketLauncher)
		{
			return new ContainerRocketLauncher(player.inventory, (TileEntityRocketLauncher)t);
		}
		if (t instanceof TileEntityController)
		{
			return new ContainerController(player.inventory, (TileEntityController)t);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity t = world.getBlockTileEntity(x, y, z);
		if (t instanceof TileEntitySatellite)
		{
			return new GuiSatellite(player.inventory, (TileEntitySatellite)t);
		}
		if (t instanceof TileEntityRocketLauncher)
		{
			return new GuiRocketLauncher(player.inventory, (TileEntityRocketLauncher)t);
		}
		if (t instanceof TileEntityController)
		{
			return new GuiController(player.inventory, (TileEntityController)t);
		}
		return null;
	}

}
