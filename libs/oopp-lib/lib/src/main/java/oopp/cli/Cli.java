package oopp.cli;

import oopp.cli.command.CommandContainerFactory;
import oopp.cli.command.CommandDispatcher;

import java.util.*;

public class Cli {
    private boolean running = false;
    private final CommandDispatcher commandDispatcher;

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

    public void start() {
        this.running = true;
        Scanner scanner = new Scanner(System.in);
        while (running) {
            String line = scanner.nextLine().toLowerCase();
            commandDispatcher.dispatch(line);
        }
    }

    public void stop() {
        this.running = false;
    }
}
