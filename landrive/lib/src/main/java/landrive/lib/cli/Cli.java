package landrive.lib.cli;

import io.vertx.core.AbstractVerticle;
import landrive.lib.cli.command.CommandContainerFactory;
import landrive.lib.cli.command.CommandDispatcher;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class Cli extends AbstractVerticle implements Runnable {
    private final CommandDispatcher commandDispatcher;
    private final Thread dispatcherThread = new Thread(this);
    private final Scanner scanner = new Scanner(System.in);
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final Semaphore nextCmd = new Semaphore(0);

    public Cli(List<Object> refs) {
        this.commandDispatcher = new CommandDispatcher(
                CommandContainerFactory.makeCommandContainers(refs)
        );
    }

    public Cli(Object ref) {
        this.commandDispatcher = new CommandDispatcher(
                CommandContainerFactory.makeCommandContainers(ref)
        );
    }

    @Override
    public void start() {
        this.dispatcherThread.start();
    }

    @Override
    public void stop() throws InterruptedException {
        this.running.set(false);
        this.nextCmd.release();
        this.dispatcherThread.join();
        this.scanner.close();
    }

    @Override
    public void run() {
        this.running.set(true);
        while(this.running.get()) {
            final CompletableFuture<String> futureLine = asyncGetLine();
            futureLine.thenAccept(line -> {
                commandDispatcher.dispatch(line.toLowerCase());
                this.nextCmd.release();
            });
            try {
                this.nextCmd.acquire();
                futureLine.cancel(true);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private CompletableFuture<String> asyncGetLine() {
        final CompletableFuture<String> future = new CompletableFuture<>();
        final Thread thread = new Thread(() -> {
            final String line = this.scanner.nextLine();
            future.complete(line);
        });
        thread.start();
        return future;
    }
}