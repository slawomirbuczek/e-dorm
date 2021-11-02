package com.edorm.entities.rentable;

import com.edorm.entities.users.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RENT_HISTORY")
public class RentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "rent_time", nullable = false)
    private LocalDateTime rentTime;

    @Column(name = "return_time")
    private LocalDateTime returnTime;

    @OneToOne(optional = false)
    private RentableItem rentableItem;

    @OneToOne(optional = false)
    private User user;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("rentTime", rentTime)
                .append("returnTime", returnTime)
                .append("rentableItem", rentableItem)
                .append("user", user)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RentHistory that = (RentHistory) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(rentTime, that.rentTime)
                .append(returnTime, that.returnTime)
                .append(rentableItem, that.rentableItem)
                .append(user, that.user)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(rentTime)
                .append(returnTime)
                .append(rentableItem)
                .append(user)
                .toHashCode();
    }
}
