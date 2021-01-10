package br.com.jluna.dsdeliver.services;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jluna.dsdeliver.dto.OrderDTO;
import br.com.jluna.dsdeliver.dto.ProductDTO;
import br.com.jluna.dsdeliver.entities.Order;
import br.com.jluna.dsdeliver.entities.OrderStatus;
import br.com.jluna.dsdeliver.entities.Product;
import br.com.jluna.dsdeliver.repositories.OrderRepository;
import br.com.jluna.dsdeliver.repositories.ProductRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;

	@Autowired
	private ProductRepository productRepository;

	@Transactional(readOnly = true)
	public List<OrderDTO> findAll() {
		List<Order> list = repository.findOrdersWithProducts();
		return list.stream().map(p -> new OrderDTO(p)).collect(Collectors.toList());

	}

	@Transactional
	public OrderDTO insert(OrderDTO dto) {

		// cria uma nova order
		Order order = new Order(null, dto.getAddress(), dto.getLatitude(), dto.getLongitude(), Instant.now(),
				OrderStatus.PENDING);

		// adiciona os produtos na order
		for (ProductDTO p : dto.getProducts()) {
			Product product = productRepository.getOne(p.getId());
			order.getProducts().add(product);
		}

		order = repository.save(order);

		return new OrderDTO(order);
	}

}
