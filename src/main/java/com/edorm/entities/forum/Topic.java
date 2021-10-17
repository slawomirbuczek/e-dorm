package com.edorm.entities.forum;

import com.edorm.entities.rooms.Room;
import com.edorm.entities.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("JpaDataSourceORMInspection")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TOPIC")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "edited")
    private Boolean edited;

    @Column(name = "deleted")
    private Boolean deleted;

    @OneToOne
    private User user;

    @OneToMany(targetEntity = Post.class, mappedBy = "id", fetch = FetchType.EAGER)
    private List<Post> posts;

    public void addPost(Post post) {
        if (Objects.isNull(posts)) {
            posts = new ArrayList<>();
        }
        posts.add(post);
    }

}
