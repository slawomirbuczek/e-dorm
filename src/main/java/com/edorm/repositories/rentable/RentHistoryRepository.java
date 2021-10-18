package com.edorm.repositories.rentable;

import com.edorm.entities.rentable.RentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentHistoryRepository extends JpaRepository<RentHistory, Long> {



}
