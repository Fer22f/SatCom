package fer22f.mods.satcom.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fer22f.mods.satcom.SatCom;
import fer22f.mods.satcom.tile.TileEntityController;
import fer22f.mods.satcom.tile.TileEntitySatellite;

public class BlockController extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private static Icon faceIcon;
	
	public BlockController(int id) {
		super(id, Material.iron);
		this.setHardness(10.0F);
		this.setStepSound(soundMetalFootstep);
		this.setUnlocalizedName("controller");
		this.setCreativeTab(SatCom.tabSatellite);
		this.setTextureName("satcom:controller");
	}
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("satcom:niobiumBlock");
        this.faceIcon = par1IconRegister.registerIcon("satcom:controller");
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
		return new TileEntityController();
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        if (world.isRemote)
        {
            return true;
        }
        else
        {
            TileEntityController tile = (TileEntityController)world.getBlockTileEntity(x, y, z);

            if (tile != null)
            {
                player.openGui(SatCom.instance, 2, world, x, y, z);
            }

            return true;
        }
    }

}
