package fer22f.mods.satcom.block;

import net.minecraft.block.Block;
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
import fer22f.mods.satcom.tile.TileEntitySatellite;

public class BlockAntenna extends Block {

	@SideOnly(Side.CLIENT)
	private static Icon topIcon;
	
	public BlockAntenna(int id) {
		super(id, Material.iron);
		this.setHardness(10.0F);
		this.setStepSound(soundMetalFootstep);
		this.setUnlocalizedName("antenna");
		this.setCreativeTab(SatCom.tabSatellite);
		this.setTextureName("satcom:antenna");
	}
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("satcom:antenna");
        this.topIcon = par1IconRegister.registerIcon("satcom:antenna_top");
    }
	
	@SideOnly(Side.CLIENT)
    public Icon getIcon(int par1, int par2)
    {
        return par1 == 1 ? this.topIcon : (par1 == 0 ? this.topIcon : this.blockIcon);
    }

}
