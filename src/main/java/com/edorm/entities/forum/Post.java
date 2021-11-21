package com.edorm.entities.forum;

import com.edorm.entities.images.Image;
import com.edorm.entities.users.User;
import lombok.*;
import org.apache.commons.lang3.builder.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "POST")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "edited")
    private Boolean edited;

    @Column(name = "deleted")
    private Boolean deleted;

    @OneToOne
    private User user;

    @OneToOne
    private Image image;

    @OneToOne
    private Post topic;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("content", content)
                .append("createDate", createDate)
                .append("edited", edited)
                .append("deleted", deleted)
                .append("user", user)
                .append("forumImage", image)
                .append("topic", topic)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        return new EqualsBuilder()
                .append(id, post.id)
                .append(content, post.content)
                .append(createDate, post.createDate)
                .append(edited, post.edited)
                .append(deleted, post.deleted)
                .append(user, post.user)
                .append(image, post.image)
                .append(topic, post.topic)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(content)
                .append(createDate)
                .append(edited)
                .append(deleted)
                .append(user)
                .append(image)
                .append(topic)
                .toHashCode();
    }
}
