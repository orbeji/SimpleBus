package cat.bernado.simplebus.query;

import cat.bernado.simplebus.message.MessageHandler;

/**
 * Message handler that handles Queries and return some value
 */
public interface QueryHandler<Q extends Query, R> extends MessageHandler {
    R execute(Q query) throws Exception;
}
