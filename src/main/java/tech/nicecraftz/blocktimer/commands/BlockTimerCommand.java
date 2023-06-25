package tech.nicecraftz.blocktimer.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import tech.nicecraftz.blocktimer.BlockTimer;

import java.util.List;

public class BlockTimerCommand extends Command {
    private final BlockTimer blockTimer;

    public BlockTimerCommand(BlockTimer blockTimer) {
        super("blocktimer");
        this.blockTimer = blockTimer;
        setAliases(List.of("bt", "blockt"));
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (sender.hasPermission("blocktimer.admin")) {
            blockTimer.reloadConfig();
            sender.sendMessage(MiniMessage.miniMessage().deserialize(blockTimer.getConfig().getString("reloaded")));
        }

        return true;
    }
}
