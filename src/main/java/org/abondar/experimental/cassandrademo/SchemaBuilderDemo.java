package org.abondar.experimental.cassandrademo;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.DataType;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;
import com.datastax.driver.core.schemabuilder.SchemaStatement;

public class SchemaBuilderDemo {

    public static void main(String[] args) {
        Cluster cluster = Cluster.builder().addContactPoint("172.17.0.2").build();

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
