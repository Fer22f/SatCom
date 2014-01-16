package fer22f.mods.satcom;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import fer22f.mods.satcom.tile.TileEntitySatellite;

public class PacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {
		if (packet.channel == "IDUpdate")
		{
			DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		       
	        int X;
	        int Y;
	        int Z;
	        int newID;
	       
	        try {
	                X = inputStream.readInt();
	                Y = inputStream.readInt();
	                Z = inputStream.readInt();
	                newID = inputStream.readInt();
	                
	                EntityPlayer p = (EntityPlayer)player;
	                
	                TileEntity t = p.worldObj.getBlockTileEntity(X, Y, Z);
	                
	                if (t instanceof TileEntitySatellite)
	                {
	                	TileEntitySatellite s = (TileEntitySatellite)t;
	                	
	                	s.ID = newID;
	                	
	                	p.worldObj.markBlockForUpdate(X, Y, Z);	              
	                }
	                
	        } catch (IOException e) {
	                e.printStackTrace();
	                return;
	        }
		} else if(packet.channel == "rocketexplosion")
		{
			DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		       
	        int X;
	        int Y;
	        int Z;
	        
	        try {
                X = inputStream.readInt();
                Y = inputStream.readInt();
                Z = inputStream.readInt();
                                
                EntityPlayer p = (EntityPlayer)player;
                
                World w = p.worldObj;
                
                for (int k = 0; k < 8; k++)
                {
                w.spawnParticle("hugeexplosion", X, Y + k, Z, 0.0F, 0.0F, 0.0F);
                }
                
	        } catch (IOException e) {
                e.printStackTrace();
                return;
            }
		}
	}

}
