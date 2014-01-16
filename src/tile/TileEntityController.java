package fer22f.mods.satcom.tile;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import fer22f.mods.satcom.SatCom;
import fer22f.mods.satcom.WorldHandler;
import net.minecraft.block.Block;
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
	public int progress;
	public boolean redstonePower;
	
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
			
			
			
			cooldown = 1 * 20;
		} else {
			cooldown -= 1;
		}
		
		if (module.equalsIgnoreCase("moduleLaser"))
		{
			int metadata = getBlockMetadata();
			int addX = 0;
			int addZ = 0;
			
			switch (metadata) {
			case 0: addZ = -1; break;
			case 1: addX = 1; break;
			case 2: addZ = 1; break;
			case 3: addX = -1;
			}
			
			if (progress != 255 && worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord) != Block.bedrock.blockID)
			{
				System.out.println(progress);
				worldObj.destroyBlock(this.xCoord + (addX * 2), 255 - progress, this.zCoord + (addZ * 2), true);		
			} else {
				progress = 0;
			}
			
			progress += 1;
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
