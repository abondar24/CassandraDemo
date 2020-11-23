package org.abondar.experimental.cassandrademo.command;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Host.StateListener;

import java.util.ArrayList;
import java.util.List;

public class ResultSetFutureDemo implements StateListener {

    public ResultSetFutureDemo() {
        super();
    }

    public String getHostString(Host host) {
        return new StringBuilder("Data Center: " + host.getDatacenter() +
                " Rack: " + host.getRack() +
                " Host: " + host.getAddress()).toString() +
                " Version: "+host.getCassandraVersion() +
                " State: "+ host.getState();
    }

    @Override
    public void onAdd(Host host) {

    }

    @Override
    public void onUp(Host host) {
         System.out.printf("Node is down: %s\n",getHostString(host));
    }

    @Override
    public void onDown(Host host) {

    }

    @Override
    public void onRemove(Host host) {
        System.out.printf("Node removed: %s\n",getHostString(host));
    }

    @Override
    public void onRegister(Cluster cluster) {

    }

    @Override
    public void onUnregister(Cluster cluster) {

    }


    public static void main(String[] args) {
           List<StateListener> list = new ArrayList<>();
           list.add(new ResultSetFutureDemo());

           Cluster cluster = Cluster.builder().addContactPoint("172.17.0.2").withInitialListeners(list).build();

           cluster.init();
    }
}
