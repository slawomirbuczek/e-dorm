package com.edorm.entities.rentable;

import com.edorm.entities.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@SuppressWarnings("JpaDataSourceORMInspection")
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

}
