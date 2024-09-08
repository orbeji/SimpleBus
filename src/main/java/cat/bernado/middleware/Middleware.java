package cat.bernado.middleware;

import cat.bernado.message.Message;

import java.util.function.Function;

public interface Middleware<M extends Message, R> {
    R execute(M message, Function<M, R> next);
}
