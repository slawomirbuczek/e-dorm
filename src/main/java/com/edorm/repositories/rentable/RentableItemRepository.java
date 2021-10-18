package com.edorm.repositories.rentable;

import com.edorm.entities.rentable.RentableItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentableItemRepository extends JpaRepository<RentableItem, Long> {

    List<RentableItem> findAllByAvailableTrue();

}
