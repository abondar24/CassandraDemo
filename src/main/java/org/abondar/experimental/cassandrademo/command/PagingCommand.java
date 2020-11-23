package org.abondar.experimental.cassandrademo.command;


import com.datastax.driver.core.*;
import org.abondar.experimental.cassandrademo.command.util.Command;

import static org.abondar.experimental.cassandrademo.command.util.CommandUtil.HOTEL_KEYSPACE;
import static org.abondar.experimental.cassandrademo.command.util.CommandUtil.SERVER_IP;

public class PagingCommand implements Command {

    @Override
    public void execute() {
        Cluster cluster = Cluster.builder().addContactPoint(SERVER_IP).build();

        Session session = cluster.connect(HOTEL_KEYSPACE);

        SimpleStatement statement = new SimpleStatement(
                "SELECT * FROM available_rooms_by_hotel_date WHERE is_available=true ALLOW FILTERING");

        statement.setFetchSize(50);

        ResultSet resultSet = session.execute(statement);

        for (Row row : resultSet) {
            if (resultSet.getAvailableWithoutFetching() < 10 && !resultSet.isFullyFetched()) {
                resultSet.fetchMoreResults();
            }
            System.out.format("hotel_id: %s, date: %s, room_number: %s \n\n", row.getString("hotel_id"),
                    row.getDate("date").toString(), row.getShort("room_number"));
        }


        cluster.close();
        System.exit(0);
    }
}
