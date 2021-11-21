package com.edorm.repositories.forum;

import com.edorm.entities.forum.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByTopicIsNullOrderByCreateDateDesc();

    List<Post> findAllByTopicOrderByCreateDateAsc(Post post);

}
