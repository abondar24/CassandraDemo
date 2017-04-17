package org.abondar.experimental.cassandrademo;


import com.datastax.driver.core.*;

public class TransactionDemo {

    public static void main(String[] args) {
        Cluster cluster = Cluster.builder().addContactPoint("172.17.0.2").build();

        Session session = cluster.connect("hotel");


        SimpleStatement statement = new SimpleStatement(
                "INSERT INTO hotels(id,name,phone) VALUES (?, ?, ?) IF NOT EXISTS",
                "AZ123","Hotel Magadan","1-408-232-456-90");

        ResultSet insertRes = session.execute(statement);

        System.out.println(insertRes);
        System.out.println(insertRes.wasApplied());
        System.out.println(insertRes.getExecutionInfo());
        System.out.println(insertRes.getExecutionInfo().getIncomingPayload());
        System.out.println(insertRes.getExecutionInfo().getQueryTrace());


        for (Row row: insertRes){
            System.out.format("id: %s, name: %s, phone: %s\n\n", row.getString("id"),
                    row.getString("name"), row.getString("phone"));
        }

        cluster.close();
        System.exit(0);
    }
}
