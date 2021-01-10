package br.com.jluna.dsdeliver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jluna.dsdeliver.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
