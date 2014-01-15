package fer22f.mods.satcom.tile;

import java.util.Random;

import fer22f.mods.satcom.SatCom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityRocketLauncher extends TileEntity implements IInventory {

	private ItemStack[] contents = new ItemStack[1];
	public Boolean[] structure = new Boolean[12];
	private int cooldown;
	protected String customName;
	
	@Override
	public int getSizeInventory() {
		return contents.length;
	}
	
	public void updateEntity()
    {
		if (cooldown == 0)
		{
			int metadata = getBlockMetadata();
			int addX = 0;
			int addZ = 0;
			int X = this.xCoord;
			int Y = this.yCoord;
			int Z = this.zCoord;
			World world = this.worldObj;
			
			switch (metadata) {
			case 0: addZ = -1; break;
			case 1: addX = 1; break;
			case 2: addZ = 1; break;
			case 3: addX = -1;
			}		
						
			for (int x = 0; x < 12; x++)
			{
				this.structure[x] = false;				
			}
			this.structure[0] = world.getBlockId(X + (addX == 0 ? ((addZ) + (addX)) : ((addZ * 3) + (addX * 3))), Y, Z + (addZ == 0 ? -((addZ) + (addX)) : ((addZ * 3) + (addX * 3)))) == SatCom.NiobiumBlock.blockID;
			this.structure[1] = world.getBlockId(X + (addX * 3), Y, Z + (addZ * 3)) == SatCom.NiobiumBlock.blockID;
			this.structure[2] = world.getBlockId(X + (addX == 0 ? -((addZ) + (addX)) : ((addZ * 3) + (addX * 3))), Y, Z + (addZ == 0 ? ((addZ) + (addX)) : ((addZ * 3) + (addX * 3)))) == SatCom.NiobiumBlock.blockID;
			this.structure[3] = world.getBlockId(X + (addX == 0 ? ((addZ) + (addX)) : ((addZ * 2) + (addX * 2))), Y, Z + (addZ == 0 ? -((addZ) + (addX)) : ((addZ * 2) + (addX * 2)))) == SatCom.NiobiumBlock.blockID;
			this.structure[4] = world.getBlockId(X + (addX * 2), Y, Z + (addZ * 2)) == SatCom.NiobiumBlock.blockID;
			this.structure[5] = world.getBlockId(X + (addX == 0 ? -((addZ) + (addX)) : ((addZ * 2) + (addX * 2))), Y, Z + (addZ == 0 ? ((addZ) + (addX)) : ((addZ * 2) + (addX * 2)))) == SatCom.NiobiumBlock.blockID;
			this.structure[6] = world.getBlockId(X + (addX == 0 ? -((addZ) + (addX)) : ((addZ * 1) + (addX * 1))), Y, Z + (addZ == 0 ? ((addZ) + (addX)) : ((addZ * 1) + (addX * 1)))) == SatCom.NiobiumBlock.blockID;
			this.structure[7] = world.getBlockId(X + (addX * 1), Y, Z + (addZ * 1)) == SatCom.NiobiumBlock.blockID;
			this.structure[8] = world.getBlockId(X + (addX == 0 ? ((addZ) + (addX)) : ((addZ * 1) + (addX * 1))), Y, Z + (addZ == 0 ? -((addZ) + (addX)) : ((addZ * 1) + (addX * 1)))) == SatCom.NiobiumBlock.blockID;
			
			this.cooldown = 5 * 20;
		} else {
			cooldown--;
		}
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
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
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
    }

}
