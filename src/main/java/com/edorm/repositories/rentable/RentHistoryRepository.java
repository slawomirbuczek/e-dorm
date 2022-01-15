package com.edorm.repositories.rentable;

import com.edorm.entities.rentable.RentHistory;
import com.edorm.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface RentHistoryRepository extends JpaRepository<RentHistory, Long> {

    List<RentHistory> findAllByUserOrderByRentTimeDesc(User user);

}
