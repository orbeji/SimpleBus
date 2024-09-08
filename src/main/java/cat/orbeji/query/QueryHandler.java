package cat.orbeji.query;

import cat.orbeji.message.MessageHandler;

public interface QueryHandler<Q extends Query, R> extends MessageHandler {
    R execute(Q query) throws Exception;
}
