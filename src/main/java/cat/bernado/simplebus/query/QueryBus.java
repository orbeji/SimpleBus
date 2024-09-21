package cat.bernado.simplebus.query;

/**
 * Message bus that matches a {@link Query} to its corresponding {@link QueryHandler} and executes it.
 * <p>
 * The {@code QueryBus} is responsible for locating the appropriate handler for a given query and executing
 * any middleware that has been configured in the correct order before the query handler is invoked.
 */
public interface QueryBus {

    /**
     * Receives a {@link Query} and executes the corresponding {@link QueryHandler}.
     * <p>
     * This method will also ensure that any configured middlewares are executed in the proper order, wrapping
     * the query handling process.
     *
     * @param query the query to be handled
     * @param <Q> the type of the query, extending {@link Query}
     * @param <R> the type of the result returned by the query handler
     * @return the result returned by the query handler
     * @throws Exception if an error occurs during query handling
     */
    <Q extends Query, R> R handle(Q query) throws Exception;
}
