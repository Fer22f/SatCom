package fer22f.mods.satcom;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class PlayerTracker implements IPlayerTracker {

	@Override
	public void onPlayerLogin(EntityPlayer player) {
		sendUpdateToClient((Player) player);
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) {
		
	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) {
		sendUpdateToClient((Player) player);
	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) {		
		
	}
	
	public void sendUpdateToClient(Player p)
	{   	
    	ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
	    DataOutputStream outputStream = new DataOutputStream(bos);
	    try {
	        outputStream.writeInt(WorldHandler.satellitesList.size());
	        for (int k = 0; k < WorldHandler.satellitesList.size(); k++)
	    	{
	        	outputStream.writeInt(WorldHandler.satellitesList.get(k).getInteger("ID"));
	        	outputStream.writeUTF(WorldHandler.satellitesList.get(k).getString("Module"));
	    	}
	        
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	               
	    Packet250CustomPayload packet = new Packet250CustomPayload();
	    packet.channel = "updateSatelliteList";
	    packet.data = bos.toByteArray();
	    packet.length = bos.size();

	    PacketDispatcher.sendPacketToPlayer(packet, p);
	}

}
