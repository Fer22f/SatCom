package fer22f.mods.satcom.block;

import java.util.Random;

import fer22f.mods.satcom.SatCom;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class BlockNiobiumOre extends Block {

	public BlockNiobiumOre(int par1) {
		super(par1, Material.rock);
		this.setHardness(3.0F);
		this.setResistance(5.0F);
		this.setStepSound(soundStoneFootstep);
		this.setUnlocalizedName("niobiumOre");
		this.setCreativeTab(SatCom.tabSatellite);
		this.setTextureName("satcom:niobiumOre");
	}
	

}
