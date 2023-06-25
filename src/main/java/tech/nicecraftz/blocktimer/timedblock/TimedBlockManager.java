package tech.nicecraftz.blocktimer.timedblock;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
public class TimedBlockManager {
    private final FileConfiguration config;
    private final EnumMap<Material, Integer> materialTimeMap = new EnumMap<>(Material.class);
    private final List<TimedBlock> timedBlockList = Lists.newArrayList();

    public void loadTimes() {
        materialTimeMap.clear();
        List<String> timesString = config.getStringList("timed-blocks.blocks");
        for (String timeString : timesString) {
            String[] split = timeString.split(":");
            Material material = Material.getMaterial(split[0]);
            int time = Integer.parseInt(split[1]);
            materialTimeMap.put(material, time);
        }
    }

    public void addBlock(TimedBlock timedBlock) {
        timedBlockList.add(timedBlock);
    }

    public void tick() {
        Iterator<TimedBlock> iterator = timedBlockList.iterator();
        while (iterator.hasNext()) {
            TimedBlock timedBlock = iterator.next();
            int time = materialTimeMap.getOrDefault(timedBlock.material(), config.getInt("timed-blocks.default-time"));
            if (timedBlock.hasExpired(time)) {
                timedBlock.location().getBlock().setType(Material.AIR);
                iterator.remove();
            }
        }
    }
}
