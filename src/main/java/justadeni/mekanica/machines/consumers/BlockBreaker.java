package justadeni.mekanica.machines.consumers;

import justadeni.mekanica.items.ItemManager;
import justadeni.mekanica.machines.Machine;
import justadeni.mekanica.utils.nms.Breakability;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dropper;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class BlockBreaker extends Machine {

    public final static ItemManager itemManager = new ItemManager(8,"Block Breaker", Material.DROPPER);

    public static BlockBreaker getNew(){
        return new BlockBreaker(0, 40000, (short) 0, new ItemStack(Material.AIR), (byte) 0);
    }

    private ItemStack product;

    private byte progress;
    public BlockBreaker(int RF, int limit, short procon, ItemStack product, byte progress) {
        super(RF, limit, procon);
        this.product = product;
        this.progress = progress;
    }

    @Override
    public void produce(Location loc) {
        if (getRF() <= 0){
            setProcon((short) 0);
            return;
        }

        Block block = getFacing(loc);
        if (block != null && !block.isEmpty()){
            if (progress == 0){
                if (Breakability.isBreakable(block)){
                    work();
                } else {
                    setProcon((short) 0);
                    return;
                }
            } else if (progress <= 80){
                work();
            } else {
                breakBlock(block);
                work();
                progress = 0;
            }
        }
    }

    private void work(){
        if (progress < 100) {
            progress += 20;
        }
        int delta = 50;
        subtractRF(delta);
        setProcon((short) delta);
    }
    private Block getFacing(Location loc){
        Dropper dropper = (Dropper) loc.getBlock();
        BlockFace face = dropper.getBlock().getFace(dropper.getBlock());
        switch (face){
            case UP -> {
                return (new Location(loc.getWorld(), loc.getX(), loc.getY()+1, loc.getZ())).getBlock();
            }
            case DOWN -> {
                return (new Location(loc.getWorld(), loc.getX(), loc.getY()-1, loc.getZ())).getBlock();
            }
            case WEST -> {
                return (new Location(loc.getWorld(), loc.getX()-1, loc.getY(), loc.getZ())).getBlock();
            }
            case EAST -> {
                return (new Location(loc.getWorld(), loc.getX()+1, loc.getY(), loc.getZ())).getBlock();
            }
            case SOUTH -> {
                return (new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()+1)).getBlock();
            }
            case NORTH -> {
                return (new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()-1)).getBlock();
            }
            default -> {
                return null;
            }
        }
    }

    //TODO: make this a nice animation using NMS
    private void breakBlock(Block block){
        block.setType(Material.AIR);
    }
}