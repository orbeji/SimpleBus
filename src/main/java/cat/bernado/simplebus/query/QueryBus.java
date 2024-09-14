package cat.bernado.simplebus.query;

/**
 * Message bus that matches a Query to its QueryHandler and executes it
 */
public interface QueryBus {
    <Q extends Query, R> R handle(Q query) throws Exception;
}
