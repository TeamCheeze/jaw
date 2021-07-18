package io.github.dolphin2410.jaw.util.async;

/**
 * Asynchronous executing
 *
 * @author dolphin2410
 */
public class Async {
    /**
     * Execute async
     * @param runnable The action to execute asynchronously
     */
    public static void execute(Runnable runnable) {
        Thread newThread = new Thread(runnable);
        newThread.start();
    }
}
