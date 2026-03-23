package ru.bibikov.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bibikov.order.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
