package org.abondar.experimental.cassandrademo;

import com.datastax.driver.core.*;

import java.text.SimpleDateFormat;

public class SimpleStatementDemo {
    public static void main(String[] args) {
        Cluster cluster = Cluster.builder().addContactPoint("172.17.0.2").build();

        Session session = cluster.connect("hotel");

        //hotel id
        String id = "AZ123";

        SimpleStatement insertStatement = new SimpleStatement(
                "INSERT INTO hotels(id, name, phone) VALUES(?, ?, ?)",
                id,"Hotel Magadan","1-408-232-456-90");

        ResultSet insertRes = session.execute(insertStatement);

        System.out.println(insertRes);
        System.out.println(insertRes.wasApplied());
        System.out.println(insertRes.getExecutionInfo());
        System.out.println(insertRes.getExecutionInfo().getIncomingPayload());

        SimpleStatement selectStatement = new SimpleStatement(
                "SELECT * FROM hotels WHERE id=?",id);

        selectStatement.enableTracing();

        ResultSet selectRes = session.execute(selectStatement);

        System.out.println(selectRes);
        System.out.println(selectRes.wasApplied());
        System.out.println(selectRes.getExecutionInfo());
        System.out.println(selectRes.getExecutionInfo().getIncomingPayload());
        System.out.println(selectRes.getExecutionInfo().getQueryTrace());

        for (Row row: selectRes){
            System.out.format("id: %s, name: %s, phone: %s\n\n", row.getString("id"),
                    row.getString("name"), row.getString("phone"));
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
        QueryTrace queryTrace = selectRes.getExecutionInfo().getQueryTrace();

        System.out.printf("Trace id: %s\n\n",queryTrace.getTraceId());
        System.out.printf("%-42s | %-12s | %-10s \n","activity","timestamp","source");
        System.out.println("-------------------------------------------+--------------+-------------");

        for (QueryTrace.Event event : queryTrace.getEvents()){
            System.out.printf("%42s | %12s | %10s \n",event.getDescription(),
                    dateFormat.format(event.getTimestamp()),
                    event.getSource());
        }


        cluster.close();
        System.exit(0);


    }
}
