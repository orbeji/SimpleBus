package cat.bernado.simplebus.middleware;

import cat.bernado.simplebus.message.Message;

import java.util.function.Function;

/**
 * Interface for defining middleware in the SimpleBus library.
 * <p>
 * Middleware allows for pre- and post-processing of messages before they are passed to the next handler
 * in the chain. This can be used for logging, validation, authentication, etc.
 *
 * @param <M> the type of the message, extending the {@link Message} interface
 * @param <R> the return type of the message handler
 */
public interface Middleware<M extends Message, R> {

    /**
     * Executes the middleware logic.
     * <p>
     * The middleware processes the message and then passes it to the next handler in the chain via the
     * {@code next} function. Middleware can modify the message, perform checks, or add additional behavior
     * before or after calling the next handler.
     *
     * @param message the message to be processed by the middleware
     * @param next the function that invokes the next handler in the chain
     * @return the result produced by the message handler
     */
    R execute(M message, Function<M, R> next);
}
