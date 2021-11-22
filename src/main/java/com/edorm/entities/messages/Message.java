package com.edorm.entities.messages;

import com.edorm.entities.images.Image;
import com.edorm.entities.users.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MESSAGE")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Lob
    @Column(name = "content")
    private String content;

    @OneToOne
    private Image image;

    @OneToOne
    private User from;

    @OneToOne
    private User to;


}
