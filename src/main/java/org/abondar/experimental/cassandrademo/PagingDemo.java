package org.abondar.experimental.cassandrademo;


import com.datastax.driver.core.*;

public class PagingDemo {

    public static void main(String[] args) {
        Cluster cluster = Cluster.builder().addContactPoint("172.17.0.2").build();

        Session session = cluster.connect("hotel");

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
