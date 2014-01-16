package fer22f.mods.satcom.tile;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import fer22f.mods.satcom.SatCom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntityController extends TileEntity {

	public int ID;	
		
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
