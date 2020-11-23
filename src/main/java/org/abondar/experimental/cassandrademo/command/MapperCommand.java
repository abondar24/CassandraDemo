package org.abondar.experimental.cassandrademo.command;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.abondar.experimental.cassandrademo.command.util.Command;
import org.abondar.experimental.cassandrademo.data.Hotel;

import static org.abondar.experimental.cassandrademo.command.util.CommandUtil.HOTEL_KEYSPACE;
import static org.abondar.experimental.cassandrademo.command.util.CommandUtil.SERVER_IP;

public class MapperCommand implements Command {


    @Override
    public void execute() {
        Cluster cluster = Cluster.builder().addContactPoint(SERVER_IP).build();
        Session session = cluster.connect(HOTEL_KEYSPACE);

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
