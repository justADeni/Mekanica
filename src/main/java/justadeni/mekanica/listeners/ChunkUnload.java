package justadeni.mekanica.listeners;

import justadeni.mekanica.TaskScheduler;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

public class ChunkUnload implements Listener {

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent e){

        Chunk chunk = e.getChunk();
        TaskScheduler.chunkUnload(e.getWorld().getName(), chunk.getX(), chunk.getZ());
    }

}
