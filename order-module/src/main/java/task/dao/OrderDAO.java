package task.dao;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import task.entity.Order;

@Component
public class OrderDAO {

    ConnectionFactory connectionFactory;

    OrderDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public Mono<Order> findById(Long id) {
        return Mono.from(connectionFactory.create())
                .flatMap(c ->
                        Mono.from(c.createStatement("select id,status_id,client_id from Order where id = $1")
                                .bind("$1", id)
                                .execute())
                                .doFinally((st) -> c.close())).map(result -> result.map((row, meta) ->
                                new Order(row.get("id", Long.class),
                                        row.get("status_id", Long.class),
                                        row.get("client_id", Long.class))))
                                .flatMap(Mono::from);
    }

}
