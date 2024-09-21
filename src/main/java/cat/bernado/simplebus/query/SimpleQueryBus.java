package cat.bernado.simplebus.query;

import cat.bernado.simplebus.message.MessageHandlerFinder;
import cat.bernado.simplebus.middleware.Middleware;
import cat.bernado.simplebus.middleware.MiddlewareChainBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

/**
 * Implementation of the {@link QueryBus} that handles the execution of queries.
 * <p>
 * This class is responsible for finding the correct {@link QueryHandler} for a given query, building a middleware
 * execution chain, and invoking the handler. It applies any configured {@link Middleware} during query execution.
 */
@Service
public class SimpleQueryBus implements QueryBus {
    private final MessageHandlerFinder messageHandlerFinder;
    private final MiddlewareChainBuilder middlewareChainBuilder;
    private final List<Middleware> middlewares;

    /**
     * Constructs a {@code SimpleQueryBus} with the required dependencies.
     *
     * @param messageHandlerFinder the service to find the appropriate {@link QueryHandler}
     * @param middlewareChainBuilder the builder for constructing the middleware execution chain
     * @param middlewares the list of middleware to apply during query execution
     */
    public SimpleQueryBus(
            MessageHandlerFinder messageHandlerFinder,
            MiddlewareChainBuilder middlewareChainBuilder,
            List<Middleware> middlewares
    ) {
        this.messageHandlerFinder = messageHandlerFinder;
        this.middlewareChainBuilder = middlewareChainBuilder;
        this.middlewares = middlewares;
    }

    /**
     * Handles the execution of a query.
     * <p>
     * This method finds the appropriate {@link QueryHandler} for the given query, constructs a middleware chain,
     * and executes the handler with the query, applying any middleware as needed.
     *
     * @param query the query to be handled
     * @param <C> the type of query, extending {@link Query}
     * @param <R> the type of the result returned by the query handler
     * @return the result of the query
     * @throws Exception if an error occurs during query execution
     */
    @SuppressWarnings("unchecked")
    @Override
    public <C extends Query, R> R handle(C query) throws Exception {
        // Find the appropriate query handler
        QueryHandler<C, R> queryHandler = (QueryHandler<C, R>) this.messageHandlerFinder.find(query);

        // Define the handler function that will execute the query
        Function<C, R> handlerFunction = cmd -> {
            try {
                return queryHandler.execute(cmd);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        // Build the middleware chain and execute the query
        Function<C, R> chain = middlewareChainBuilder.buildChain(middlewares, handlerFunction);

        // Apply the chain to the query
        return chain.apply(query);
    }
}
