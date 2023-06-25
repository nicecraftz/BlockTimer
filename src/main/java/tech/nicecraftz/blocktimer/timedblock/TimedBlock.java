package tech.nicecraftz.blocktimer.timedblock;

import lombok.Data;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

@Data
public class TimedBlock {
    private final Material material;
    private final Location location;
    private final long placeTimestamp;

    public TimedBlock(Material material, Location location) {
        this.material = material;
        this.location = location;
        placeTimestamp = System.currentTimeMillis();
    }

    public TimedBlock(Block block) {
        this.material = block.getBlockData().getMaterial();
        this.location = block.getLocation();
        placeTimestamp = System.currentTimeMillis();
    }

    public boolean hasExpired(int timeInSeconds) {
        return System.currentTimeMillis() - placeTimestamp > (timeInSeconds * 1000L);
    }

}
