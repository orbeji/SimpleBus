package cat.bernado.simplebus.middleware;

import cat.bernado.simplebus.message.Message;

import java.util.function.Function;

/**
 * Interface that all Middlewares should implement
 *
 * @param <M>
 * @param <R>
 */
public interface Middleware<M extends Message, R> {
    R execute(M message, Function<M, R> next);
}
