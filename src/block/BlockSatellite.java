package fer22f.mods.satcom.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fer22f.mods.satcom.SatCom;

public class BlockSatellite extends BlockContainer {

	public BlockSatellite() {
		super(SatCom.satelliteID, Material.iron);
		this.setHardness(10.0F);
		this.setStepSound(soundMetalFootstep);
		this.setUnlocalizedName("satellite");
		this.setCreativeTab(CreativeTabs.tabRedstone);
		this.setTextureName("satcom:satellite");
	}
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("satcom:satellite");
    }

	@Override
	public TileEntity createNewTileEntity(World world) {
		return null;
	}

}
