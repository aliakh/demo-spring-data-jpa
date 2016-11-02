package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.RouterFunction;
import reactor.ipc.netty.http.HttpServer;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.RequestPredicates.*;
import static org.springframework.web.reactive.function.RouterFunctions.route;
import static org.springframework.web.reactive.function.RouterFunctions.toHttpHandler;

@SpringBootApplication
public class Application {

    @Bean
    public RouterFunction<?> router(MessageHandler messageHandler) {
        return route(GET("/message"), messageHandler::findAll)
                .and(route(GET("/message/{id}").and(accept(APPLICATION_JSON)), messageHandler::findById))
                .and(route(POST("/message").and(contentType(APPLICATION_JSON)), messageHandler::save));
    }

    @Bean
    public HttpServer server(RouterFunction<?> router) {
        HttpHandler handler = toHttpHandler(router);
        HttpServer httpServer = HttpServer.create(8080);
        httpServer.start(new ReactorHttpHandlerAdapter(handler));
        return httpServer;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}





