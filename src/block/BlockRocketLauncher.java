package fer22f.mods.satcom.block;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fer22f.mods.satcom.SatCom;
import fer22f.mods.satcom.WorldHandler;
import fer22f.mods.satcom.tile.TileEntityRocketLauncher;
import fer22f.mods.satcom.tile.TileEntitySatellite;

public class BlockRocketLauncher extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private static Icon faceIcon;
	
	public int tickRate(World par1World)
    {
        return 4;
    }
	
	public BlockRocketLauncher(int id) {
		super(id, Material.iron);
		this.setHardness(10.0F);
		this.setStepSound(soundMetalFootstep);
		this.setUnlocalizedName("rocketLauncher");
		this.setCreativeTab(SatCom.tabSatellite);
		this.setTextureName("satcom:rocketLauncher");
	}
	
	public void updateTick(World world, int X, int Y, int Z, Random random)
    {
		int metadata = world.getBlockMetadata(X, Y, Z);
		int addX = 0;
		int addZ = 0;
		
		switch (metadata) {
		case 0: addZ = -1; break;
		case 1: addX = 1; break;
		case 2: addZ = 1; break;
		case 3: addX = -1;
		}
		
		TileEntity s = (TileEntity)world.getBlockTileEntity(X + (addX * 2), Y + 1, Z + (addZ * 2));
				
		if (s != null && s instanceof TileEntitySatellite)
		{
			TileEntitySatellite r = (TileEntitySatellite)s;
			int ID = r.ID;
			String Module = r.getStackInSlot(0) == null ? "" : r.getStackInSlot(0).getUnlocalizedName();
			
			world.setBlockToAir(X + (addX == 0 ? ((addZ) + (addX)) : ((addZ * 2) + (addX * 2))), Y + 1, Z + (addZ == 0 ? -((addZ) + (addX)) : ((addZ * 2) + (addX * 2))));
		
			world.setBlockToAir(X + (addX * 2), Y + 1, Z + (addZ * 2));
		
			world.setBlockToAir(X + (addX == 0 ? -((addZ) + (addX)) : ((addZ * 2) + (addX * 2))), Y + 1, Z + (addZ == 0 ? ((addZ) + (addX)) : ((addZ * 2) + (addX * 2))));
		
			int explosionX = X + (addX * 2);
			int explosionY = Y + 1;
			int explosionZ = Z + (addZ * 2);
		
			world.playSoundEffect(X, Y, Z, "random.explode", 4.0F, (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);

			NBTTagCompound sat = new NBTTagCompound();
			
			sat.setInteger("ID", ID);
			sat.setString("Module", Module.substring(5));
			
			WorldHandler.satellitesList.add(sat);
			
			sendChangeToClient(explosionX, explosionY, explosionZ);
		}
	}
	
	public void sendChangeToClient(int X, int Y, int Z){
	    ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
	    DataOutputStream outputStream = new DataOutputStream(bos);
	    try {
	        outputStream.writeInt(X);
	        outputStream.writeInt(Y);
	        outputStream.writeInt(Z);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	               
	    Packet250CustomPayload packet = new Packet250CustomPayload();
	    packet.channel = "rocketexplosion";
	    packet.data = bos.toByteArray();
	    packet.length = bos.size();

	    PacketDispatcher.sendPacketToAllInDimension(packet, 0);
	}
	
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
		boolean flag = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4);
		TileEntityRocketLauncher r = (TileEntityRocketLauncher)par1World.getBlockTileEntity(par2, par3, par4);
		
		if (flag && r.correctDimension && r.structureOk && r.IDavaliable && r.hasFuel && r.hasModule)
		{
			r.setInventorySlotContents(0, null);
			par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
		}
    }
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("satcom:niobiumBlock");
        this.faceIcon = par1IconRegister.registerIcon("satcom:rocketLauncher");
    }
	
	@SideOnly(Side.CLIENT)
    public Icon getIcon(int par1, int par2)
    {
        return par1 == 1 ? this.blockIcon : (par1 == 0 ? this.blockIcon : (par2 == 2 && par1 == 2 ? this.faceIcon : (par2 == 3 && par1 == 5 ? this.faceIcon : (par2 == 0 && par1 == 3 ? this.faceIcon : (par2 == 1 && par1 == 4 ? this.faceIcon : this.blockIcon)))));
    }
	
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
    {
        int l = MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, l, 2);
    }

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityRocketLauncher();
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        if (world.isRemote)
        {
            return true;
        }
        else
        {
            TileEntityRocketLauncher tile = (TileEntityRocketLauncher)world.getBlockTileEntity(x, y, z);

            if (tile != null)
            {
                player.openGui(SatCom.instance, 1, world, x, y, z);
            }

            return true;
        }
    }

}
