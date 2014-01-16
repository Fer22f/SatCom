package fer22f.mods.satcom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkDataEvent.Load;
import net.minecraftforge.event.world.ChunkDataEvent.Save;
import net.minecraftforge.event.world.ChunkEvent.Unload;

public class WorldHandler {
		
	public static List<NBTTagCompound> satellitesList = new ArrayList<NBTTagCompound>();
	
	@ForgeSubscribe
    public void onWorldLoad(Load event)
    {
		if (satellitesList.isEmpty())
		load();
    }
    
    @ForgeSubscribe
    public void onWorldSave(Save event)
    {
    	for (int k = 0; k < satellitesList.size(); k++)
        	save(satellitesList.get(k));
    }
    
    @ForgeSubscribe
    public void onChunkDataLoad(ChunkDataEvent.Load event)
    {

    }
    
    @ForgeSubscribe
    public void onWorldUnload(Unload event)
    {
    	for (int k = 0; k < satellitesList.size(); k++)
    	save(satellitesList.get(k));
    }
    
    private void save(NBTTagCompound s)
    {   	    	
    	try
        {
            File saveFile = new File(DimensionManager.getCurrentSaveRootDirectory(), "SatCom");
            if(!saveFile.exists())
                saveFile.createNewFile();
            DataOutputStream dout = new DataOutputStream(new FileOutputStream(saveFile));
            CompressedStreamTools.writeCompressed(s, dout);
            dout.close();
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    
    public NBTTagCompound load()
    {
    	NBTTagCompound sav = null;
    	File saveDir = new File(DimensionManager.getCurrentSaveRootDirectory(), "SatCom");
        try
        {	
        	if(!saveDir.exists())
                saveDir.mkdirs();
        	File satelitesFile = new File(saveDir, "satellites");
        	
            if(satelitesFile.exists())
            {
                DataInputStream din = new DataInputStream(new FileInputStream(satelitesFile));
                sav = CompressedStreamTools.readCompressed(din);
                din.close();
            }
            else
            {
                sav = new NBTTagCompound();
            }
        } catch (Exception e) {
        	return null;
        }
        
        return sav;
    }
    
}
