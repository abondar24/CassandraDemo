package org.abondar.experimental.cassandrademo.command.util;


import org.abondar.experimental.cassandrademo.command.BatchCommand;
import org.abondar.experimental.cassandrademo.command.ConnectionCommand;
import org.abondar.experimental.cassandrademo.command.ConnectionListenerCommand;
import org.abondar.experimental.cassandrademo.command.MapperCommand;
import org.abondar.experimental.cassandrademo.command.PreparedStatementCommand;
import org.abondar.experimental.cassandrademo.command.QueryBuilderCommand;
import org.abondar.experimental.cassandrademo.command.ResultSetFutureCommand;
import org.abondar.experimental.cassandrademo.command.SchemaBuilderCommand;
import org.abondar.experimental.cassandrademo.command.SessionCommand;
import org.abondar.experimental.cassandrademo.command.SimpleStatementCommand;
import org.abondar.experimental.cassandrademo.command.TransactionCommand;

public class CommandSwitcher {

    private final CommandExecutor executor;

    public CommandSwitcher() {
        this.executor = new CommandExecutor();
    }


    public void executeCommand(String cmd){
        try {
            switch (Commands.valueOf(cmd)){

                case BC:
                    BatchCommand bc = new BatchCommand();
                    executor.executeCommand(bc);
                    break;

                case CC:
                    ConnectionCommand cc = new ConnectionCommand();
                    executor.executeCommand(cc);
                    break;

                case CLC:
                    ConnectionListenerCommand clc = new ConnectionListenerCommand();
                    executor.executeCommand(clc);
                    break;

                case MC:
                    MapperCommand mc = new MapperCommand();
                    executor.executeCommand(mc);
                    break;

                case PSC:
                    PreparedStatementCommand psc = new PreparedStatementCommand();
                    executor.executeCommand(psc);
                    break;

                case QBC:
                    QueryBuilderCommand qbc = new QueryBuilderCommand();
                    executor.executeCommand(qbc);
                    break;

                case RSFC:
                    ResultSetFutureCommand rsfc = new ResultSetFutureCommand();
                    executor.executeCommand(rsfc);
                    break;

                case SBC:
                    SchemaBuilderCommand sbc = new SchemaBuilderCommand();
                    executor.executeCommand(sbc);
                    break;

                case SC:
                    SessionCommand sc = new SessionCommand();
                    executor.executeCommand(sc);
                    break;

                case SSC:
                    SimpleStatementCommand ssc = new SimpleStatementCommand();
                    executor.executeCommand(ssc);
                    break;

                case TC:
                    TransactionCommand tc = new TransactionCommand();
                    executor.executeCommand(tc);
                    break;

            }

        } catch (IllegalArgumentException ex){
            System.out.println("Check documentation for command list");
            System.exit(1);
        }

    }
}
