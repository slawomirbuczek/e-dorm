package com.edorm.repositories.announcements;

import com.edorm.entities.announcements.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    List<Announcement> findAllByDisabledFalseOrderByDateDesc();

}
