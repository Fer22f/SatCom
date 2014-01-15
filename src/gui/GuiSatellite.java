package fer22f.mods.satcom.gui;

import org.lwjgl.opengl.GL11;

import fer22f.mods.satcom.tile.TileEntitySatellite;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiSatellite extends GuiContainer {

	private static final ResourceLocation satelliteGuiTexture = new ResourceLocation("satcom","textures/gui/satellite.png");
    private TileEntitySatellite satellite;
	
	public GuiSatellite(InventoryPlayer inventory, TileEntitySatellite tile) {
		super(new ContainerSatellite(inventory, tile));
		this.satellite = tile;
	}
	
	public void initGui()
    {
		int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        super.initGui();
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, 60 + k, 19 + l, 15, 20, "-"));
        this.buttonList.add(new GuiButton(0, 98 + k, 19 + l, 15, 20, "+"));
    }

	protected void drawGuiContainerForegroundLayer(int i, int j) {
        this.fontRenderer.drawString("Satellite", this.xSize / 2 - this.fontRenderer.getStringWidth("Satellite") / 2, 6, 4210752);
        this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(satelliteGuiTexture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }

}
