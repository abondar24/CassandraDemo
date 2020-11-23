package org.abondar.experimental.cassandrademo.command;


import com.datastax.driver.core.*;
import org.abondar.experimental.cassandrademo.command.util.Command;

import static org.abondar.experimental.cassandrademo.command.util.CommandUtil.HOTEL_KEYSPACE;
import static org.abondar.experimental.cassandrademo.command.util.CommandUtil.ID_COLUMN;
import static org.abondar.experimental.cassandrademo.command.util.CommandUtil.NAME_COLUMN;
import static org.abondar.experimental.cassandrademo.command.util.CommandUtil.PHONE_COLUMN;
import static org.abondar.experimental.cassandrademo.command.util.CommandUtil.SERVER_IP;

public class TransactionCommand implements Command {

    @Override
    public void execute() {
        Cluster cluster = Cluster.builder().addContactPoint(SERVER_IP).build();

        Session session = cluster.connect(HOTEL_KEYSPACE);


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
            System.out.format("id: %s, name: %s, phone: %s\n\n", row.getString(ID_COLUMN),
                    row.getString(NAME_COLUMN), row.getString(PHONE_COLUMN));
        }

        cluster.close();
        System.exit(0);
    }
}
