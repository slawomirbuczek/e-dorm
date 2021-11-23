package com.edorm.entities.messages;

import com.edorm.entities.users.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CONVERSATION")
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @OneToOne
    private User userOne;

    @OneToOne
    private User userTwo;


}
