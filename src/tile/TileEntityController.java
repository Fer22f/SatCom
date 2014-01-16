package fer22f.mods.satcom.tile;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import fer22f.mods.satcom.SatCom;
import fer22f.mods.satcom.WorldHandler;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityController extends TileEntity {

	public int ID;
	public String module;
	private int cooldown;
	public boolean hasAntenna;
	public MapColor[][] topBlocks = new MapColor[20][20];
	
	public static int findTopBlockBelowY(World world, int x, int z, int y)
	  {
	    int id = world.getBlockId(x, y, z);
	    while ((id == 0) && (y >= 0)) {
	      y--;
	      id = world.getBlockId(x, y, z);
	    }
	    return y;
	  }
	
	private void getTopBlocks(World world, int x, int y, int z)
	  {
	    int range = 0;
	    int maxrange = 10;
	    for (int i = -range; i <= range; i++)
	      for (int j = -range; j <= range; j++) {
	        int topy = findTopBlockBelowY(world, x + i, z + j, 255);
	        Material m = world.getBlockMaterial(x + i, topy, z + j);
	        this.topBlocks[(i + range)][(j + range)] = m.materialMapColor;
	      }
	  }
	
	public void updateModuleName()
	{
		module = "";
		for (int k = 0; k < WorldHandler.satellitesList.size(); k++)
		{
			NBTTagCompound n = WorldHandler.satellitesList.get(k);
			if (n.getInteger("ID") == ID)
			{
				module = n.getString("Module");
				break;
			}
		}
	}
	
	public void updateEntity()
	{
		if (cooldown == 0)
		{
			if (worldObj.getBlockId(this.xCoord, this.yCoord + 1, this.zCoord) == SatCom.Antenna.blockID)
			{
				updateModuleName();
				hasAntenna = true;
			} else {
				hasAntenna = false;
			}
			
			if (this.module.equalsIgnoreCase("moduleCamera"))
			{
				getTopBlocks(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}
			
			cooldown = 10 * 20;			
		} else {
			cooldown -= 1;
		}
	}
	
	public Packet getDescriptionPacket() {
	    NBTTagCompound tagCompound = new NBTTagCompound();
	    writeToNBT(tagCompound);
	    return new Packet132TileEntityData(xCoord, yCoord, zCoord, 5, tagCompound);
	}
	
	public void onDataPacket(INetworkManager networkManager, Packet132TileEntityData packet) {
	    readFromNBT(packet.data);
	}
	
	public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);              
       	this.ID = tag.getInteger("ID");
      }

    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);                
        tag.setInteger("ID", this.ID);
    }

	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

}
