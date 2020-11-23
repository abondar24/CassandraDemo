package org.abondar.experimental.cassandrademo.command;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.DataType;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;
import com.datastax.driver.core.schemabuilder.SchemaStatement;
import org.abondar.experimental.cassandrademo.command.util.Command;

import static org.abondar.experimental.cassandrademo.command.util.CommandUtil.HOTEL_KEYSPACE;
import static org.abondar.experimental.cassandrademo.command.util.CommandUtil.MOTELS_TABLE;
import static org.abondar.experimental.cassandrademo.command.util.CommandUtil.SERVER_IP;

public class SchemaBuilderCommand implements Command {

    @Override
    public void execute() {
        Cluster cluster = Cluster.builder().addContactPoint(SERVER_IP).build();

        Session session = cluster.connect(HOTEL_KEYSPACE);

        SchemaStatement motelSchema = SchemaBuilder.createTable(MOTELS_TABLE)
                .addPartitionKey("id", DataType.uuid())
                .addColumn("name",DataType.text())
                .addColumn("phone",DataType.text())
                .addColumn("address",DataType.text());

        session.execute(motelSchema);

        Metadata metadata = cluster.getMetadata();
        System.out.println("Schema:");
        System.out.println(metadata.exportSchemaAsString());
        System.out.println();
        System.out.printf("Schema agreement: %s\n",metadata.checkSchemaAgreement());

        cluster.close();
        System.exit(0);

    }
}
