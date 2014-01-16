package fer22f.mods.satcom.gui;

import org.lwjgl.opengl.GL11;

import fer22f.mods.satcom.SatCom;
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
        
        String informativeText = "";
        
        if (!rocketLauncher.correctDimension)
        {
        	informativeText = "Wrong Dimension";
        } else if (!rocketLauncher.structureOk)
        {
        	informativeText = "Structure is wrong";
        } else if (!rocketLauncher.hasFuel)
        {
        	informativeText = "Place the fuel";
        } else if (!rocketLauncher.IDavaliable)
        {
        	informativeText = "ID not avaliable, try another ID";
        } else if (!rocketLauncher.hasModule)
        {
        	informativeText = "Place a module in the satellite";
        } else {
        	informativeText = "Put a redstone signal to start";
        }
        
        this.fontRenderer.drawString(informativeText, 8, this.ySize - 96 + 2, 4210752);  
	}
	
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(guiTexture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);        
                
        for (int x = 0; x < 3; x++)
        {
        	for (int y = 0; y < 3; y++)
        	{
        		if (rocketLauncher.structure[x + (y * 3)])
        		{
                    this.drawTexturedModalRect(17 + k + (x * 16), 20 + l + (y * 16), 176, 0, 16, 16);
        		} else {
        			this.drawTexturedModalRect(17 + k + (x * 16), 20 + l + (y * 16), 176, 16, 16, 16);
        		}
        	}
        }
        
        for (int x = 0; x < 3; x++)
        {
        	for (int y = 0; y < 3; y++)
        	{
        	if (rocketLauncher.structure[x + 9 + (y * 3)])
    		{
                this.drawTexturedModalRect(110 + k + (x * 16), 20 + l + (y * 16), 176, 0, 16, 16);
    		} else {
    			this.drawTexturedModalRect(110 + k + (x * 16), 20 + l + (y * 16), 176, 16, 16, 16);
    		}
        	}
        }
    }

}
