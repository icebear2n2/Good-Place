package com.icebear2n2.goodplace.store.repository;

import com.icebear2n2.goodplace.store.domain.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    boolean existsByPlaceId(String  id);
}
