package tech.nicecraftz.blocktimer.timedblock;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TimedBlockTask implements Runnable {
    private final TimedBlockManager timedBlockManager;

    @Override
    public void run() {
        timedBlockManager.tick();
    }
}
