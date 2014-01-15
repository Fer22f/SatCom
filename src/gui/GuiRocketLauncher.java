package fer22f.mods.satcom.gui;

import org.lwjgl.opengl.GL11;

import fer22f.mods.satcom.tile.TileEntityRocketLauncher;
import fer22f.mods.satcom.tile.TileEntitySatellite;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiRocketLauncher extends GuiContainer {

	private static final ResourceLocation guiTexture = new ResourceLocation("satcom","textures/gui/rocketLauncher.png");
    private TileEntityRocketLauncher rocketLauncher;
	
	public GuiRocketLauncher(InventoryPlayer inventory, TileEntityRocketLauncher tile) {
		super(new ContainerRocketLauncher(inventory, tile));
		this.rocketLauncher = tile;
	}

	protected void drawGuiContainerForegroundLayer(int i, int j) {
        this.fontRenderer.drawString("Rocket Launcher", this.xSize / 2 - this.fontRenderer.getStringWidth("Rocket Launcher") / 2, 6, 4210752);
        this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(guiTexture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }

}
