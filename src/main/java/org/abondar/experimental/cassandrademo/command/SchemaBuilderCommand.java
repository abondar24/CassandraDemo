package org.abondar.experimental.cassandrademo.command;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.DataType;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;
import com.datastax.driver.core.schemabuilder.SchemaStatement;
import org.abondar.experimental.cassandrademo.command.util.Command;

public class SchemaBuilderCommand implements Command {

    @Override
    public void execute() {
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        Session session = cluster.connect("hotel");

        SchemaStatement motelSchema = SchemaBuilder.createTable("motels")
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
