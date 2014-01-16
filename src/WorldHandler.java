package fer22f.mods.satcom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
		if (satellitesList.isEmpty() && !event.world.isRemote)
		load();
    }
    
    @ForgeSubscribe
    public void onWorldSave(Save event)
    {
    	if (!event.world.isRemote)
    	PrepareToSave();
    }
    
    @ForgeSubscribe
    public void onChunkDataLoad(ChunkDataEvent.Load event)
    {
    	if (satellitesList.isEmpty()  && !event.world.isRemote)
    		load();
    }
    
    public void PrepareToSave()
    {
    	NBTTagCompound n = new NBTTagCompound();
    	
    	for (int k = 0; k < satellitesList.size(); k++)
    	{
    		n.setTag(k + "", satellitesList.get(k));
    	}
    	
    	n.setInteger("Size", satellitesList.size());
    		
    	save(n);
    }
    
    @ForgeSubscribe
    public void onWorldUnload(Unload event)
    {
    	if (!event.world.isRemote)
    	PrepareToSave();
    }
    
    private void save(NBTTagCompound s)
    {   	    	
    	File saveDir = new File(DimensionManager.getCurrentSaveRootDirectory(), "SatCom");
    	
    	try
        {
            File saveFile = new File(saveDir, "satellites.dat");
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
        	File satelitesFile = new File(saveDir, "satellites.dat");
        	
            if(satelitesFile.exists())
            {
                DataInputStream din = new DataInputStream(new FileInputStream(satelitesFile));
                sav = CompressedStreamTools.readCompressed(din);
                
                int s = sav.getInteger("Size");
                
                for (int l = 0; l < s; l++)
                {
                	NBTTagCompound n = (NBTTagCompound) sav.getTag(l + "");
                	satellitesList.add(n);
                }
                
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
