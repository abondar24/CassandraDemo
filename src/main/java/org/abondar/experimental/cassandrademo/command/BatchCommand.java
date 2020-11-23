package org.abondar.experimental.cassandrademo.command;


import com.datastax.driver.core.*;
import org.abondar.experimental.cassandrademo.command.util.Command;

import static org.abondar.experimental.cassandrademo.command.util.CommandUtil.HOTEL_KEYSPACE;
import static org.abondar.experimental.cassandrademo.command.util.CommandUtil.ID_COLUMN;
import static org.abondar.experimental.cassandrademo.command.util.CommandUtil.NAME_COLUMN;
import static org.abondar.experimental.cassandrademo.command.util.CommandUtil.PHONE_COLUMN;
import static org.abondar.experimental.cassandrademo.command.util.CommandUtil.SERVER_IP;

public class BatchCommand implements Command {


    @Override
    public void execute() {
        Cluster cluster = Cluster.builder().addContactPoint(SERVER_IP).build();

        Session session = cluster.connect(HOTEL_KEYSPACE);

        //hotel id
        String id = "AZ123";

        SimpleStatement hotelInsert = new SimpleStatement(
                "INSERT INTO hotels(id, name, phone) VALUES(?, ?, ?)",
                id,"Hotel Magadan","1-408-232-456-90");

        SimpleStatement hotelsByPoiInsert = new SimpleStatement(
                "INSERT INTO hotels_by_poi (poi_name, hotel_id, name, phone) VALUES (?, ?, ?, ?)",
                "Magadan",id,"Hotel Magadan","1-408-232-456-90");

        BatchStatement batch = new BatchStatement();
        batch.add(hotelInsert);
        batch.add(hotelsByPoiInsert);

        ResultSet hotelInsertRes = session.execute(batch);

        System.out.println(hotelInsertRes);
        System.out.println(hotelInsertRes.wasApplied());
        System.out.println(hotelInsertRes.getExecutionInfo());
        System.out.println(hotelInsertRes.getExecutionInfo().getIncomingPayload());

        SimpleStatement hotelSelectStatement = new SimpleStatement(
                "SELECT * FROM hotels WHERE id=?",id);

        hotelSelectStatement.enableTracing();

        ResultSet hotelSelectRes = session.execute(hotelSelectStatement);

        System.out.println(hotelSelectRes);
        System.out.println(hotelSelectRes.wasApplied());
        System.out.println(hotelSelectRes.getExecutionInfo());
        System.out.println(hotelSelectRes.getExecutionInfo().getIncomingPayload());
        System.out.println(hotelSelectRes.getExecutionInfo().getQueryTrace());

        for (Row row: hotelSelectRes){
            System.out.format("id: %s, name: %s, phone: %s\n\n", row.getString(ID_COLUMN),
                    row.getString(NAME_COLUMN), row.getString(PHONE_COLUMN));
        }

        cluster.close();
        System.exit(0);

    }
}
