package fer22f.mods.satcom.gui;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.network.PacketDispatcher;
import fer22f.mods.satcom.SatCom;
import fer22f.mods.satcom.WorldHandler;
import fer22f.mods.satcom.tile.TileEntityController;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.ResourceLocation;

public class GuiController extends GuiContainer {
	
	private static final ResourceLocation controllerGuiTexture = new ResourceLocation("satcom","textures/gui/controller.png");
    private TileEntityController controller;
	
	public GuiController(InventoryPlayer inventory, TileEntityController tile) {
		super(new ContainerController(inventory, tile));
		this.controller = tile;
	}
	
	
	public void initGui()
    {
		int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        super.initGui();
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, 60  + k, 19 + l, 17, 20, "-"));
        this.buttonList.add(new GuiButton(1, 98  + k, 19 + l, 17, 20, "+"));
        this.buttonList.add(new GuiButton(2, 41  + k, 19 + l, 20, 20, "--"));
        this.buttonList.add(new GuiButton(3, 113 + k, 19 + l, 20, 20, "++"));
       
        this.buttonList.add(new GuiButton(4, 60  + k, 45 + l, 17, 20, "-"));
        this.buttonList.add(new GuiButton(5, 98  + k, 45 + l, 17, 20, "+"));
        this.buttonList.add(new GuiButton(6, 41  + k, 45 + l, 20, 20, "--"));
        this.buttonList.add(new GuiButton(7, 113 + k, 45 + l, 20, 20, "++"));
    }
	
	protected void drawGuiContainerForegroundLayer(int i, int j) {
        this.fontRenderer.drawString("Controller", this.xSize / 2 - this.fontRenderer.getStringWidth("Satellite") / 2, 6, 4210752);
        this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 96 + 2, 4210752);       
        
        this.fontRenderer.drawString(controller.ID + "",
        		(11 - this.fontRenderer.getStringWidth(controller.ID + "") / 2) + 77,
        		26, 4210752);
        
        String informativeText = "";
        
        if (!controller.hasAntenna)
        {
        	informativeText = "There is no antenna!";
        } else if (controller.module.equalsIgnoreCase("module")) {
        	informativeText = "This is a generic module";
        } else if (controller.module.equalsIgnoreCase("moduleWeather")) {
        	informativeText = "A signal will stop the rain";
        } else if (controller.module.equalsIgnoreCase("moduleWireless")) {
        	informativeText = "Second ID";
        } else if (controller.module.equalsIgnoreCase("moduleCamera")) {
        	
        }
        
      //  this.fontRenderer.drawString(informativeText, 6 + 17, 53, 4210752);
                
        itemRenderer.zLevel = 100.0F;
        if (controller.module != "" && controller.hasAntenna)
        itemRenderer.renderItemAndEffectIntoGUI(this.fontRenderer, this.mc.getTextureManager(), new ItemStack(SatCom.getItemfromModuleName(controller.module)), 13, 21);
        
	}
		
	protected void actionPerformed(GuiButton par1GuiButton)
    {
		if (par1GuiButton.id == 0)
        {
        	if (controller.ID > 0)
        	{
        		controller.ID -= 1;
        	}
        } else if (par1GuiButton.id == 1)
        {
        	if (controller.ID < 999)
        	{
        		controller.ID += 1;
        	}
        } else if (par1GuiButton.id == 2)
        {
        	if (controller.ID > 9)
        	{
        		controller.ID -= 10;
        	}
        } else if (par1GuiButton.id == 3)
        {
        	if (controller.ID < 990)
        	{
        		controller.ID += 10;
        	}
        }
				
		controller.updateModuleName();				
        sendChangeToServer();
    }
	
	public void sendChangeToServer(){
	    ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
	    DataOutputStream outputStream = new DataOutputStream(bos);
	    try {
	        outputStream.writeInt(controller.xCoord);
	        outputStream.writeInt(controller.yCoord);
	        outputStream.writeInt(controller.zCoord);
	        outputStream.writeInt(controller.ID);
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
        this.mc.getTextureManager().bindTexture(controllerGuiTexture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }

}
