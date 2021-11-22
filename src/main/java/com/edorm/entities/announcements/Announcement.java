package com.edorm.entities.announcements;

import com.edorm.entities.users.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ANNOUNCEMENT")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "subject")
    private String subject;

    @Column(name = "disabled")
    private Boolean disabled;

    @Lob
    @Column(name = "content")
    private String content;

    @OneToOne
    private User creator;

}
