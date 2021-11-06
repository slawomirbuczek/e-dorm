package com.edorm.entities.rooms;

import lombok.*;
import org.apache.commons.lang3.builder.*;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "ROOM")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "floor", nullable = false)
    private Integer floor;

    @Column(name = "size", nullable = false)
    private Integer size;

    @Column(name = "residents", nullable = false)
    private Integer residents;

    @Column(name = "availability", nullable = false)
    private Boolean availability;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("number", number)
                .append("floor", floor)
                .append("size", size)
                .append("residents", residents)
                .append("availability", availability)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        return new EqualsBuilder()
                .append(id, room.id)
                .append(number, room.number)
                .append(floor, room.floor)
                .append(size, room.size)
                .append(residents, room.residents)
                .append(availability, room.availability)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(number)
                .append(floor)
                .append(size)
                .append(residents)
                .append(availability)
                .toHashCode();
    }

}
