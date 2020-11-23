package org.abondar.experimental.cassandrademo.command;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import org.abondar.experimental.cassandrademo.command.util.Command;
import org.abondar.experimental.cassandrademo.listener.ResultSetFutureListener;

import java.util.ArrayList;
import java.util.List;

public class ResultSetFutureCommand implements Command {
    @Override
    public void execute() {
        List<Host.StateListener> list = new ArrayList<>();
        list.add(new ResultSetFutureListener());

        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").withInitialListeners(list).build();

        cluster.init();
    }
}
