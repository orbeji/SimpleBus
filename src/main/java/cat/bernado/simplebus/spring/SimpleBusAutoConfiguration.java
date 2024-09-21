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

/**
 * Configuration class that defines the Spring beans needed for the SimpleBus framework.
 * <p>
 * This class provides the configuration for the core components of the project, such as the {@link SimpleCommandBus},
 * {@link SimpleQueryBus}, middleware chain builders, and the {@link MessageHandlerFinder}.
 */
@Configuration
public class SimpleBusAutoConfiguration {

    /**
     * Constructor
     */
    public SimpleBusAutoConfiguration() {
    }

    /**
     * Creates a {@link MessageHandlerFinder} bean.
     * <p>
     * This bean is responsible for locating the appropriate {@link cat.bernado.simplebus.message.MessageHandler}
     * for a given message in the Spring context.
     *
     * @param applicationContext the Spring application context
     * @return a {@link MessageHandlerFinder} instance
     */
    @Bean
    public MessageHandlerFinder messageHandlerFinder(ApplicationContext applicationContext) {
        return new MessageHandlerFinder(applicationContext);
    }

    /**
     * Creates a {@link MiddlewareChainBuilder} bean.
     * <p>
     * This bean is responsible for building the chain of middleware that will be applied to each command or query
     * before it is processed by its handler.
     *
     * @return a {@link MiddlewareChainBuilder} instance
     */
    @Bean
    public MiddlewareChainBuilder middlewareChainBuilder() {
        return new MiddlewareChainBuilder();
    }

    /**
     * Creates a {@link SimpleCommandBus} bean.
     * <p>
     * This bean handles the execution of commands by locating the corresponding handler and processing
     * any middleware in the correct order.
     *
     * @param messageHandlerFinder the service to find the message handlers
     * @param middlewareChainBuilder the builder that constructs the middleware chain
     * @param middlewares a list of middleware to apply to commands
     * @return a {@link SimpleCommandBus} instance
     */
    @Bean
    public SimpleCommandBus simpleCommandBus(MessageHandlerFinder messageHandlerFinder, MiddlewareChainBuilder middlewareChainBuilder, List<Middleware> middlewares) {
        return new SimpleCommandBus(messageHandlerFinder, middlewareChainBuilder, middlewares);
    }

    /**
     * Creates a {@link SimpleQueryBus} bean.
     * <p>
     * This bean handles the execution of queries by locating the appropriate handler and processing
     * any middleware in the correct order.
     *
     * @param messageHandlerFinder the service to find the message handlers
     * @param middlewareChainBuilder the builder that constructs the middleware chain
     * @param middlewares a list of middleware to apply to queries
     * @return a {@link SimpleQueryBus} instance
     */
    @Bean
    public SimpleQueryBus simpleQueryBus(MessageHandlerFinder messageHandlerFinder, MiddlewareChainBuilder middlewareChainBuilder, List<Middleware> middlewares) {
        return new SimpleQueryBus(messageHandlerFinder, middlewareChainBuilder, middlewares);
    }

    /**
     * Creates a {@link LoggingMiddleware} bean.
     * <p>
     * This middleware logs the execution of commands and queries, and can be applied to both the {@link SimpleCommandBus}
     * and {@link SimpleQueryBus}.
     *
     * @return a {@link LoggingMiddleware} instance
     */
    @Bean
    public LoggingMiddleware loggingMiddleware() {
        return new LoggingMiddleware();
    }
}
