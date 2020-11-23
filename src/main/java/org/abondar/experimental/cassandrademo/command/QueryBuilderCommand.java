package org.abondar.experimental.cassandrademo.command;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.BuiltStatement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.abondar.experimental.cassandrademo.command.util.Command;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static org.abondar.experimental.cassandrademo.command.util.CommandUtil.HOTELS_TABLE;
import static org.abondar.experimental.cassandrademo.command.util.CommandUtil.HOTEL_KEYSPACE;
import static org.abondar.experimental.cassandrademo.command.util.CommandUtil.ID_COLUMN;
import static org.abondar.experimental.cassandrademo.command.util.CommandUtil.NAME_COLUMN;
import static org.abondar.experimental.cassandrademo.command.util.CommandUtil.PHONE_COLUMN;
import static org.abondar.experimental.cassandrademo.command.util.CommandUtil.SERVER_IP;

public class QueryBuilderCommand implements Command {

    @Override
    public void execute() {
        Cluster cluster = Cluster.builder().addContactPoint(SERVER_IP).build();
        Session session = cluster.connect(HOTEL_KEYSPACE);
        String id = "AZ789";

        BuiltStatement insert = QueryBuilder.insertInto(HOTELS_TABLE)
                .value(ID_COLUMN,id)
                .value(NAME_COLUMN,"Babah hotel")
                .value(PHONE_COLUMN,"1-669-567-12-89");

        ResultSet insertRes = session.execute(insert);
        System.out.println(insertRes);
        System.out.println(insertRes.wasApplied());
        System.out.println(insertRes.getExecutionInfo());
        System.out.println(insertRes.getExecutionInfo().getIncomingPayload());

        BuiltStatement select = QueryBuilder.select().all().from(HOTELS_TABLE).where(eq(ID_COLUMN,id));

        ResultSet selectRes = session.execute(select);
        System.out.println(selectRes);
        System.out.println(selectRes.wasApplied());
        System.out.println(selectRes.getExecutionInfo());
        System.out.println(selectRes.getExecutionInfo().getIncomingPayload());


        for (Row row: selectRes){
            System.out.format("id: %s, name: %s, phone: %s\n\n", row.getString(ID_COLUMN),
                    row.getString(NAME_COLUMN), row.getString(PHONE_COLUMN));
        }

        BuiltStatement delete = QueryBuilder.delete().from(HOTELS_TABLE).where(eq(ID_COLUMN,id));

        ResultSet deleteRes = session.execute(delete);
        System.out.println(deleteRes);
        System.out.println(deleteRes.wasApplied());
        System.out.println(deleteRes.getExecutionInfo());
        System.out.println(deleteRes.getExecutionInfo().getIncomingPayload());


        cluster.close();
        System.exit(0);
    }
}
