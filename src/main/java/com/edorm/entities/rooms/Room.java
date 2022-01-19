package com.edorm.entities.rooms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ROOM")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "number", nullable = false, unique = true)
    private String number;

    @Column(name = "floor", nullable = false)
    private Integer floor;

    @Column(name = "size", nullable = false)
    private Integer size;

    @Column(name = "residents", nullable = false)
    private Integer residents;

    @OneToOne
    private Composition composition;


}
