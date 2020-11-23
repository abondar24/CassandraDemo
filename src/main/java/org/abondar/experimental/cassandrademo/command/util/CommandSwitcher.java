package org.abondar.experimental.cassandrademo.command.util;


import org.abondar.experimental.cassandrademo.command.ConnectionCommand;

public class CommandSwitcher {

    private final CommandExecutor executor;

    public CommandSwitcher() {
        this.executor = new CommandExecutor();
    }


    public void executeCommand(String cmd){
        try {
            switch (Commands.valueOf(cmd)){
                case CC:
                    ConnectionCommand cc = new ConnectionCommand();
                    executor.executeCommand(cc);
                    break;

            }

        } catch (IllegalArgumentException ex){
            System.out.println("Check documentation for command list");
            System.exit(1);
        }

    }
}
