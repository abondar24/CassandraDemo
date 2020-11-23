package org.abondar.experimental.cassandrademo.command;


import com.datastax.driver.core.*;
import org.abondar.experimental.cassandrademo.command.util.Command;

public class PreparedStatementCommand implements Command {

    @Override
    public void execute() {
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        Session session = cluster.connect("hotel");

        String id = "AZ456";

        PreparedStatement insertStatement = session.prepare("INSERT INTO hotels (id,name,phone) VALUES(?, ?, ?)");
        BoundStatement insertBoundStatement = insertStatement.bind(id, "Super hotel","1-888-000-9999");

        ResultSet insertRes = session.execute(insertBoundStatement);

        System.out.println(insertRes);
        System.out.println(insertRes.wasApplied());
        System.out.println(insertRes.getExecutionInfo());
        System.out.println(insertRes.getExecutionInfo().getIncomingPayload());

        PreparedStatement selectStatement =session.prepare("SELECT * FROM hotels WHERE id=?");

        BoundStatement selectBoundStatement =selectStatement.bind(id);

        ResultSet selectRes = session.execute(selectBoundStatement);

        System.out.println(selectRes);
        System.out.println(selectRes.wasApplied());
        System.out.println(selectRes.getExecutionInfo());
        System.out.println(selectRes.getExecutionInfo().getIncomingPayload());

        for (Row row: selectRes){
            System.out.format("id: %s, name: %s, phone: %s\n\n", row.getString("id"),
                    row.getString("name"), row.getString("phone"));
        }

        cluster.close();
        System.exit(0);

    }
}
