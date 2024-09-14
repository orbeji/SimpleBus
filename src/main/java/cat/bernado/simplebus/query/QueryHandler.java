package cat.bernado.simplebus.query;

import cat.bernado.simplebus.message.MessageHandler;

public interface QueryHandler<Q extends Query, R> extends MessageHandler {
    R execute(Q query) throws Exception;
}
