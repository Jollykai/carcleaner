package com.jollykai.carcleaner.repository;

import com.jollykai.carcleaner.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Project: CarCleaner
 *
 * @author Dmitry Istomin
 * https://github.com/Jollykai
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
}
