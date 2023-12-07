package com.ll.library.boundedContext.checkout.repository;

import com.ll.library.boundedContext.checkout.entity.CheckoutHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutHistoryRepository extends JpaRepository<CheckoutHistory, Long> {
}
