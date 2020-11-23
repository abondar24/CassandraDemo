package org.abondar.experimental.cassandrademo.listener;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Host.StateListener;


public class ResultSetFutureListener implements StateListener {

    public ResultSetFutureListener() {
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


}
