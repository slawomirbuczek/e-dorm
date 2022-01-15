package com.edorm.repositories.rentable;

import com.edorm.entities.rentable.RentableItem;
import com.edorm.enums.rentable.RentableItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface RentableItemRepository extends JpaRepository<RentableItem, Long> {

    List<RentableItem> findAllByTypeOrderByName(RentableItemType type);

}
