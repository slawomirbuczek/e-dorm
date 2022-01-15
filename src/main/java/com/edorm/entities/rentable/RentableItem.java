package com.edorm.entities.rentable;

import com.edorm.entities.images.Image;
import com.edorm.enums.rentable.RentableItemType;
import lombok.*;
import org.apache.commons.lang3.builder.*;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RENTABLE_ITEM")
public class RentableItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "available", nullable = false)
    private Boolean available;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private RentableItemType type;

    @OneToOne
    private Image image;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("available", available)
                .append("image", image)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RentableItem that = (RentableItem) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(name, that.name)
                .append(available, that.available)
                .append(image, that.image)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(image)
                .append(available)
                .toHashCode();
    }
}
