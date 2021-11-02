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
                .append(updateDate, post.updateDate)
                .append(edited, post.edited)
                .append(deleted, post.deleted)
                .append(user, post.user)
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
                .toHashCode();
    }
}
