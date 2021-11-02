package com.edorm.entities.forum;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("content", content)
                .append("createDate", createDate)
                .append("updateDate", updateDate)
                .append("edited", edited)
                .append("deleted", deleted)
                .append("user", user)
                .append("posts", posts)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Topic topic = (Topic) o;

        return new EqualsBuilder()
                .append(id, topic.id)
                .append(content, topic.content)
                .append(createDate, topic.createDate)
                .append(updateDate, topic.updateDate)
                .append(edited, topic.edited)
                .append(deleted, topic.deleted)
                .append(user, topic.user)
                .append(posts, topic.posts)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(content)
                .append(createDate)
                .append(updateDate)
                .append(edited)
                .append(deleted)
                .append(user)
                .append(posts)
                .toHashCode();
    }
}
