package cat.bernado.simplebus.spring;

import cat.bernado.simplebus.command.SimpleCommandBus;
import cat.bernado.simplebus.message.MessageHandlerFinder;
import cat.bernado.simplebus.middleware.LoggingMiddleware;
import cat.bernado.simplebus.middleware.Middleware;
import cat.bernado.simplebus.middleware.MiddlewareChainBuilder;
import cat.bernado.simplebus.query.SimpleQueryBus;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SimpleBusAutoConfiguration {

    @Bean
    public MessageHandlerFinder messageHandlerFinder(ApplicationContext applicationContext) {
        return new MessageHandlerFinder(applicationContext);
    }

    @Bean
    public MiddlewareChainBuilder middlewareChainBuilder() {
        return new MiddlewareChainBuilder();
    }

    @Bean
    public SimpleCommandBus simpleCommandBus(MessageHandlerFinder messageHandlerFinder, MiddlewareChainBuilder middlewareChainBuilder, List<Middleware> middlewares) {
        return new SimpleCommandBus(messageHandlerFinder, middlewareChainBuilder, middlewares);
    }

    @Bean
    public SimpleQueryBus simpleQueryBus(MessageHandlerFinder messageHandlerFinder, MiddlewareChainBuilder middlewareChainBuilder, List<Middleware> middlewares) {
        return new SimpleQueryBus(messageHandlerFinder, middlewareChainBuilder, middlewares);
    }

    @Bean
    public LoggingMiddleware loggingMiddleware() {
        return new LoggingMiddleware();
    }
}