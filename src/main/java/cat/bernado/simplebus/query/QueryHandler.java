package cat.bernado.simplebus.query;

import cat.bernado.simplebus.message.MessageHandler;

/**
 * Message handler that handles {@link Query} objects and returns a response.
 * <p>
 * Implementations of this interface are responsible for processing a query and returning the expected result.
 *
 * @param <Q> the type of query, extending the {@link Query} interface
 * @param <R> the type of response returned by the handler
 */
public interface QueryHandler<Q extends Query, R> extends MessageHandler {

    /**
     * Executes the given query and returns the result.
     * <p>
     * This method processes the query, applying any necessary business logic, and returns the appropriate
     * result. Implementers should handle any exceptions that may occur during execution.
     *
     * @param query the query to be executed
     * @return the result of the query
     * @throws Exception if an error occurs during query execution
     */
    R execute(Q query) throws Exception;
}
