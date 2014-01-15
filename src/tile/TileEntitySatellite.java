package fer22f.mods.satcom.tile;

import fer22f.mods.satcom.SatCom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntitySatellite extends TileEntity implements IInventory {

	private ItemStack[] contents = new ItemStack[1];
	public int ID;
	protected String customName;
	
	@Override
	public int getSizeInventory() {
		return contents.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return this.contents[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (this.contents[i] != null)
        {
            ItemStack itemstack;

            if (this.contents[i].stackSize <= j)
            {
                itemstack = this.contents[i];
                this.contents[i] = null;
                this.onInventoryChanged();
                return itemstack;
            }
            else
            {
                itemstack = this.contents[i].splitStack(j);

                if (this.contents[i].stackSize == 0)
                {
                    this.contents[i] = null;
                }

                this.onInventoryChanged();
                return itemstack;
            }
        }
        else
        {
            return null;
        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (this.contents[i] != null)
        {
            ItemStack itemstack = this.contents[i];
            this.contents[i] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
	}
	
	public Packet getDescriptionPacket() {
	    NBTTagCompound tagCompound = new NBTTagCompound();
	    writeToNBT(tagCompound);
	    System.out.println("Get the description");
	    return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tagCompound);
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.contents[i] = itemstack;

        if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
        {
            itemstack.stackSize = this.getInventoryStackLimit();
        }

        this.onInventoryChanged();
	}

	@Override
	public String getInvName() {
		return this.isInvNameLocalized() ? this.customName : "container.satellite";
	}
	
	public void setCustomName(String costumname)
    {
        this.customName = costumname;
    }

	@Override
	public boolean isInvNameLocalized()
    {
        return this.customName != null;
    }

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack par1ItemStack) {
		if (par1ItemStack.itemID == SatCom.moduleGPS.itemID ||
	    		par1ItemStack.itemID == SatCom.moduleLaser.itemID ||
	    		par1ItemStack.itemID == SatCom.moduleWireless.itemID ||
	    		par1ItemStack.itemID == SatCom.module.itemID)
	    	{
	    		return true;
	    	}
		return false;
	}
	
	public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        NBTTagList nbttaglist = tag.getTagList("Items");
        this.contents = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.contents.length)
            {
                this.contents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        if (tag.hasKey("CustomName"))
        {
            this.customName = tag.getString("CustomName");
        }
       
       	this.ID = tag.getInteger("ID");
    }

    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.contents.length; ++i)
        {
            if (this.contents[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.contents[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        tag.setTag("Items", nbttaglist);

        if (this.isInvNameLocalized())
        {
            tag.setString("CustomName", this.customName);
        }
        
        tag.setInteger("ID", this.ID);
    }

}
