package task.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import task.dao.OrderDAO;
import task.entity.Order;

@RestController
public class OrderController {

    private final OrderDAO orderDAO;

    public OrderController(OrderDAO orderDao) {
        this.orderDAO = orderDao;
    }

    @GetMapping("/order/{id}")
    public Mono<ResponseEntity<Order>> getOrder(@PathVariable("id") Long id) {
        return orderDAO.findById(id)
                .map(acc -> new ResponseEntity<>(acc, HttpStatus.OK))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(null, HttpStatus.NOT_FOUND)));
    }

}
