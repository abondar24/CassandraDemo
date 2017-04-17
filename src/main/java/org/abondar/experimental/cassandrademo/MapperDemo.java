package org.abondar.experimental.cassandrademo;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public class MapperDemo {
    public static void main(String[] args) {
        Cluster cluster = Cluster.builder().addContactPoint("172.17.0.2").build();
        Session session = cluster.connect("hotel");

        MappingManager mappingManager = new MappingManager(session);
        Mapper<Hotel> hotelMapper = mappingManager.mapper(Hotel.class);

        Hotel hotel = new Hotel("AZ789","SS-217","1-456-687-12-92");
        hotelMapper.save(hotel);

        Hotel retrievedHotel = hotelMapper.get(hotel.getId());
        System.out.println(retrievedHotel.toString());

        hotelMapper.delete(hotel);

        cluster.close();
        System.exit(0);
    }
}
