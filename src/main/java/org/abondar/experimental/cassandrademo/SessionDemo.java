package org.abondar.experimental.cassandrademo;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Session;

public class SessionDemo {
    public static void main(String[] args) {
        Cluster cluster = Cluster.builder().addContactPoint("172.17.0.2").build();

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
