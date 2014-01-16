package fer22f.mods.satcom.gui;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.network.PacketDispatcher;
import fer22f.mods.satcom.tile.TileEntitySatellite;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.ResourceLocation;

public class GuiSatellite extends GuiContainer {

	private static final ResourceLocation satelliteGuiTexture = new ResourceLocation("satcom","textures/gui/satellite.png");
    private TileEntitySatellite satellite;
	
	public GuiSatellite(InventoryPlayer inventory, TileEntitySatellite tile) {
		super(new ContainerSatellite(inventory, tile));
		this.satellite = tile;
		System.out.println(tile.ID);
	}
	
	public void initGui()
    {
		int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        super.initGui();
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, 60 + k, 19 + l, 17, 20, "-"));
        this.buttonList.add(new GuiButton(1, 98 + k, 19 + l, 17, 20, "+"));
        this.buttonList.add(new GuiButton(0, 43 + k, 19 + l, 17, 20, "--"));
    }

	protected void drawGuiContainerForegroundLayer(int i, int j) {
        this.fontRenderer.drawString("Satellite", this.xSize / 2 - this.fontRenderer.getStringWidth("Satellite") / 2, 6, 4210752);
        this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
        
        this.fontRenderer.drawString(satellite.ID + "",
        		(11 - this.fontRenderer.getStringWidth(satellite.ID + "") / 2) + 77,
        		26, 4210752);
	}
		
	protected void actionPerformed(GuiButton par1GuiButton)
    {
		if (par1GuiButton.id == 0)
        {
        	if (satellite.ID > 1)
        	{
        		satellite.ID -= 1;
        	}
        } else if (par1GuiButton.id == 1)
        {
        	if (satellite.ID < 998)
        	{
        		satellite.ID += 1;
        	}
        }
        
        sendChangeToServer();
    }
	
	public void sendChangeToServer(){
	    ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
	    DataOutputStream outputStream = new DataOutputStream(bos);
	    try {
	        outputStream.writeInt(satellite.xCoord);
	        outputStream.writeInt(satellite.yCoord);
	        outputStream.writeInt(satellite.zCoord);
	        outputStream.writeInt(satellite.ID);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	               
	    Packet250CustomPayload packet = new Packet250CustomPayload();
	    packet.channel = "IDUpdate";
	    packet.data = bos.toByteArray();
	    packet.length = bos.size();

	    PacketDispatcher.sendPacketToServer(packet);
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
