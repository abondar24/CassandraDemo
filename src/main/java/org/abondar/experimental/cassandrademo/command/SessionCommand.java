package org.abondar.experimental.cassandrademo.command;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Session;
import org.abondar.experimental.cassandrademo.command.util.Command;

public class SessionCommand implements Command {

    @Override
    public void execute() {
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        Session session = cluster.connect();
        Session.State state = session.getState();

        System.out.printf("New session created for keyspace: %s\n",session.getLoggedKeyspace());

        for (Host host: state.getConnectedHosts()){
            System.out.printf("Data center: %s; Rack: %s; Host: %s; Open connections: %s\n",
                    host.getDatacenter(),host.getRack(),host.getAddress(),state.getOpenConnections(host));
        }

        cluster.close();
        System.exit(0);
    }
}
