package task.dao;

import io.netty.util.internal.StringUtil;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;

@Configuration
@PropertySource("classpath:application.properties")
public class Config {

    @Value( "${db.password}" )
    private String password;

    @Value("${db.url}")
    private String url;

    @Value("${db.user}")
    private String user;

    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactoryOptions baseOptions = ConnectionFactoryOptions.parse(url);
        ConnectionFactoryOptions.Builder ob = ConnectionFactoryOptions.builder().from(baseOptions);
        ob.option(DATABASE, "microservices");
        if (!StringUtil.isNullOrEmpty(user)) {
            ob.option(USER, user);
        }
        if (!StringUtil.isNullOrEmpty(password)) {
            ob.option(PASSWORD, password);
        }
        return ConnectionFactories.get(ob.build());
    }

    @PostConstruct
    public void initDb() throws Exception {

        Flux.from(connectionFactory().create())
                .flatMap(c ->
                        Flux.from(c.createBatch()
                                .add("drop table if exists Order")
                                .add("create table Order(" +
                                        "id IDENTITY(1,1)," +
                                        "status_id int," +
                                        "client_id int)")
                                .add("insert into Order(status_id,client_id)" +
                                        "values(1,1)")
                                .execute())
                                .doFinally((st) -> c.close())
                ).;
    }

}
