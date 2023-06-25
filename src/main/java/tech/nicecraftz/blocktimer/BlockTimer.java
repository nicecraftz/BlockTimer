package tech.nicecraftz.blocktimer;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.codemc.worldguardwrapper.WorldGuardWrapper;
import org.codemc.worldguardwrapper.flag.IWrappedFlag;
import org.codemc.worldguardwrapper.flag.WrappedState;
import tech.nicecraftz.blocktimer.commands.BlockTimerCommand;
import tech.nicecraftz.blocktimer.listeners.BlockListener;
import tech.nicecraftz.blocktimer.timedblock.TimedBlockManager;

import java.util.Optional;

@Getter
public final class BlockTimer extends JavaPlugin {
    private TimedBlockManager timedBlockManager;
    private boolean worldGuardEnabled = false;

    @Override
    public void onLoad() {
        loadFlag();
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        if (!worldGuardEnabled) {
            getLogger().severe("Couldn't register the plugin's flag! Disabling BlockTimer...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getLogger().info("Hooked into WorldGuard!");

        timedBlockManager = new TimedBlockManager(getConfig());
        timedBlockManager.loadTimes();

        getServer().getCommandMap().register("blocktimer", new BlockTimerCommand(this));
        getServer().getPluginManager().registerEvents(new BlockListener(this), this);
        getServer().getScheduler().runTaskTimer(this, timedBlockManager, 0, 1);

        getLogger().info("BlockTimer fully enabled!");
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        if (timedBlockManager != null) {
            timedBlockManager.loadTimes();
        }
    }

    private void loadFlag() {
        WorldGuardWrapper worldGuardWrapper = WorldGuardWrapper.getInstance();
        Optional<IWrappedFlag<WrappedState>> flag = worldGuardWrapper.registerFlag("timed-blocks", WrappedState.class);
        worldGuardEnabled = flag.isPresent();
    }
}
