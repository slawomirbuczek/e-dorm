package com.edorm.entities.users;

import com.edorm.entities.rooms.Room;
import lombok.*;
import org.apache.commons.lang3.builder.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RESIDENT")
public class Resident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @OneToOne(optional = false)
    private User user;

    @OneToOne
    private Address address;

    @OneToOne
    private Room room;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("phoneNumber", phoneNumber)
                .append("birthday", birthday)
                .append("user", user)
                .append("address", address)
                .append("room", room)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Resident resident = (Resident) o;

        return new EqualsBuilder()
                .append(id, resident.id)
                .append(firstName, resident.firstName)
                .append(lastName, resident.lastName)
                .append(phoneNumber, resident.phoneNumber)
                .append(birthday, resident.birthday)
                .append(user, resident.user)
                .append(address, resident.address)
                .append(room, resident.room)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(firstName)
                .append(lastName)
                .append(phoneNumber)
                .append(birthday)
                .append(user)
                .append(address)
                .append(room)
                .toHashCode();
    }
}
