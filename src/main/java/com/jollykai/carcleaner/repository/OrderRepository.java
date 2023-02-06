package com.jollykai.carcleaner.repository;

import com.jollykai.carcleaner.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Project: CarCleaner
 *
 * @author Dmitry Istomin
 * https://github.com/Jollykai
 */
@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    boolean existsByDate(Date date);
}
