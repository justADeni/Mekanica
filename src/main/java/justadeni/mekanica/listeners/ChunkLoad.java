package justadeni.mekanica.listeners;

import justadeni.mekanica.TaskScheduler;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

public class ChunkLoad implements Listener {

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent e){
        if (e.isNewChunk()){
            return;
        }

        Chunk chunk = e.getChunk();
        TaskScheduler.chunkLoad(e.getWorld().getName(), chunk.getX(), chunk.getZ());
    }

}
