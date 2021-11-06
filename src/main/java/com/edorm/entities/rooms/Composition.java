package com.edorm.entities.rooms;

import lombok.*;
import org.apache.commons.lang3.builder.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "COMPOSITION")
public class Composition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(targetEntity = Room.class, mappedBy = "id", fetch = FetchType.LAZY)
    private List<Room> rooms;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("rooms", rooms)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Composition that = (Composition) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(name, that.name)
                .append(rooms, that.rooms)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(rooms)
                .toHashCode();
    }
}
