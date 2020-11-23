package org.abondar.experimental.cassandrademo.listener;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;


public class ConnectionListener implements Host.StateListener {

    public ConnectionListener() {
        super();
    }

    public String getHostString(Host host) {
        return new StringBuilder("Data Center: " + host.getDatacenter() +
                " Rack: " + host.getRack() +
                " Host: " + host.getAddress()).toString() +
                " Version: " + host.getCassandraVersion() +
                " State: " + host.getState();
    }

    @Override
    public void onAdd(Host host) {
        System.out.printf("Node is up: %s\n", getHostString(host));
    }

    @Override
    public void onUp(Host host) {
        System.out.printf("Node is up: %s\n", getHostString(host));
    }

    @Override
    public void onDown(Host host) {
        System.out.printf("Node is down: %s\n",getHostString(host));
    }

    @Override
    public void onRemove(Host host) {
        System.out.printf("Node is removed: %s\n",getHostString(host));
    }

    @Override
    public void onRegister(Cluster cluster) {

    }

    @Override
    public void onUnregister(Cluster cluster) {

    }

}
