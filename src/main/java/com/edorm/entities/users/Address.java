package com.edorm.entities.users;

import lombok.*;
import org.apache.commons.lang3.builder.*;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ADDRESS")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "zip", nullable = false)
    private String zip;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "building_number", nullable = false)
    private String buildingNumber;

    @Column(name = "apartment_number")
    private String apartmentNumber;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("country", country)
                .append("city", city)
                .append("zip", zip)
                .append("street", street)
                .append("buildingNumber", buildingNumber)
                .append("apartmentNumber", apartmentNumber)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        return new EqualsBuilder()
                .append(id, address.id)
                .append(country, address.country)
                .append(city, address.city)
                .append(zip, address.zip)
                .append(street, address.street)
                .append(buildingNumber, address.buildingNumber)
                .append(apartmentNumber, address.apartmentNumber)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(country)
                .append(city)
                .append(zip)
                .append(street)
                .append(buildingNumber)
                .append(apartmentNumber)
                .toHashCode();
    }

}
