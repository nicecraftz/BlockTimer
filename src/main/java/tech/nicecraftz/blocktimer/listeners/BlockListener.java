package tech.nicecraftz.blocktimer.listeners;

import lombok.RequiredArgsConstructor;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.codemc.worldguardwrapper.WorldGuardWrapper;
import org.codemc.worldguardwrapper.flag.IWrappedFlag;
import org.codemc.worldguardwrapper.flag.WrappedState;
import tech.nicecraftz.blocktimer.BlockTimer;
import tech.nicecraftz.blocktimer.timedblock.TimedBlock;

import java.util.Optional;


@RequiredArgsConstructor
public class BlockListener implements Listener {
    private final BlockTimer blockTimer;

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Block block = e.getBlock();

        Optional<IWrappedFlag<WrappedState>> stateFlag = WorldGuardWrapper.getInstance().getFlag("timed-blocks", WrappedState.class);
        if (stateFlag.isEmpty()) {
            return;
        }

        WrappedState wrappedState = WorldGuardWrapper.getInstance().queryFlag(e.getPlayer(), block.getLocation(), stateFlag.get()).orElse(WrappedState.DENY);
        if (wrappedState != WrappedState.ALLOW) {
            return;
        }

        TimedBlock timedBlock = new TimedBlock(block);
        blockTimer.getTimedBlockManager().addBlock(timedBlock);
    }
}
