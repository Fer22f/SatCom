package fer22f.mods.satcom.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fer22f.mods.satcom.SatCom;
import fer22f.mods.satcom.tile.TileEntitySatellite;

public class BlockNiobiumBlock extends Block {

	
	public BlockNiobiumBlock(int id) {
		super(id, Material.iron);
		this.setHardness(10.0F);
		this.setStepSound(soundMetalFootstep);
		this.setUnlocalizedName("niobiumBlock");
		this.setCreativeTab(SatCom.tabSatellite);
		this.setTextureName("satcom:niobiumBlock");
	}
	
	

}
