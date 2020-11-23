package org.abondar.experimental.cassandrademo.command;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.BuiltStatement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;

public class QueryBuilderDemo {

    public static void main(String[] args) {
        Cluster cluster = Cluster.builder().addContactPoint("172.17.0.2").build();
        Session session = cluster.connect("hotel");
        String id = "AZ789";

        BuiltStatement insert = QueryBuilder.insertInto("hotels")
                .value("id",id)
                .value("name","Babah hotel")
                .value("phone","1-669-567-12-89");

        ResultSet insertRes = session.execute(insert);
        System.out.println(insertRes);
        System.out.println(insertRes.wasApplied());
        System.out.println(insertRes.getExecutionInfo());
        System.out.println(insertRes.getExecutionInfo().getIncomingPayload());

        BuiltStatement select = QueryBuilder.select().all().from("hotels").where(eq("id",id));

        ResultSet selectRes = session.execute(select);
        System.out.println(selectRes);
        System.out.println(selectRes.wasApplied());
        System.out.println(selectRes.getExecutionInfo());
        System.out.println(selectRes.getExecutionInfo().getIncomingPayload());


        for (Row row: selectRes){
            System.out.format("id: %s, name: %s, phone: %s\n\n", row.getString("id"),
                    row.getString("name"), row.getString("phone"));
        }

        BuiltStatement delete = QueryBuilder.delete().from("hotels").where(eq("id",id));

        ResultSet deleteRes = session.execute(delete);
        System.out.println(deleteRes);
        System.out.println(deleteRes.wasApplied());
        System.out.println(deleteRes.getExecutionInfo());
        System.out.println(deleteRes.getExecutionInfo().getIncomingPayload());


        cluster.close();
        System.exit(0);
    }
}
