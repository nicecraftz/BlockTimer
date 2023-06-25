package tech.nicecraftz.blocktimer.timedblock;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public record TimedBlock(Material material, Location location, long placeTimestamp) {

    public TimedBlock(Block block) {
        this(block.getBlockData().getMaterial(), block.getLocation(), System.currentTimeMillis());
    }

    public boolean hasExpired(int timeInSeconds) {
        return System.currentTimeMillis() - placeTimestamp > (timeInSeconds * 1000L);
    }

}
