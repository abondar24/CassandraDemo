package org.abondar.experimental.cassandrademo;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Frozen;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.Set;

@Table(keyspace = "hotel", name = "hotels")
public class Hotel {

    @PartitionKey
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    @Frozen
    private String address;

    @Column(name = "pois")
    private Set<String> pointsOfInterest;

    public Hotel(String id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public Hotel(String id, String name, String phone, String address, Set<String> pointsOfInterest) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.pointsOfInterest = pointsOfInterest;
    }

    public Hotel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<String> getPointsOfInterest() {
        return pointsOfInterest;
    }

    public void setPointsOfInterest(Set<String> pointsOfInterest) {
        this.pointsOfInterest = pointsOfInterest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hotel hotel = (Hotel) o;

        if (id != null ? !id.equals(hotel.id) : hotel.id != null) return false;
        if (name != null ? !name.equals(hotel.name) : hotel.name != null) return false;
        if (phone != null ? !phone.equals(hotel.phone) : hotel.phone != null) return false;
        if (address != null ? !address.equals(hotel.address) : hotel.address != null) return false;
        return pointsOfInterest != null ? pointsOfInterest.equals(hotel.pointsOfInterest) : hotel.pointsOfInterest == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (pointsOfInterest != null ? pointsOfInterest.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", pointsOfInterest=" + pointsOfInterest +
                '}';
    }
}
