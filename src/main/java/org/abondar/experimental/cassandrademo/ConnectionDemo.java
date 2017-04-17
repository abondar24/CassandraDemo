package org.abondar.experimental.cassandrademo;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;

public class ConnectionDemo {

    public static void main(String[] args) {
        Cluster cluster = Cluster.builder().addContactPoint("172.17.0.2").build();

        cluster.init();

        Metadata metadata = cluster.getMetadata();

        System.out.printf("Connected to cluster: %s %s\n",
                metadata.getClusterName(),cluster.getClusterName());

        for (Host host: metadata.getAllHosts()){
            System.out.printf("Data center: %s; Rack: %s; Host: %s\n",
                    host.getDatacenter(),host.getRack(),host.getAddress());
        }

        System.out.println("Schema:");
        System.out.println(metadata.exportSchemaAsString());
        System.out.println();
        System.out.printf("Schema agreement: %s\n", metadata.checkSchemaAgreement());


        cluster.close();
        System.exit(0);
    }
}
