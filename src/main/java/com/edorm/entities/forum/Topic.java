package com.edorm.entities.forum;

import com.edorm.entities.images.Image;
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
@Table(name = "TOPIC")
public class Topic {

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

    @OneToOne
    private Image image;

    @OneToOne
    private User creator;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("content", content)
                .append("createDate", createDate)
                .append("edited", edited)
                .append("user", creator)
                .append("forumImage", image)
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
                .append(edited, topic.edited)
                .append(creator, topic.creator)
                .append(image, topic.image)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(content)
                .append(createDate)
                .append(edited)
                .append(creator)
                .append(image)
                .toHashCode();
    }

}
