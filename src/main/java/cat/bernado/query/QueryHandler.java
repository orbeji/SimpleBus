package cat.bernado.query;

import cat.bernado.message.MessageHandler;

public interface QueryHandler<Q extends Query, R> extends MessageHandler {
    R execute(Q query) throws Exception;
}
