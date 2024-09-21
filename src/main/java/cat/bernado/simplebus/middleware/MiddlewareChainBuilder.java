package cat.bernado.simplebus.middleware;

import cat.bernado.simplebus.message.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

/**
 * Builds the execution chain of all the {@link Middleware} components and the {@link cat.bernado.simplebus.message.MessageHandler}.
 * <p>
 * This class constructs a chain of responsibility where each middleware wraps the next one, and finally, the message
 * is passed to the main handler function. The chain is built in reverse order to ensure correct execution flow.
 */
@Component
public class MiddlewareChainBuilder {

    /**
     * Constructor
     */
    public MiddlewareChainBuilder() {
    }

    /**
     * Builds a chain of middlewares and the message handler function.
     * <p>
     * This method iterates through the list of {@link Middleware} instances in reverse order to ensure that the
     * last middleware in the list is the first to be executed, and the first middleware wraps the call to the next.
     * The handler function is called at the end of the chain.
     *
     * @param middlewares the list of middlewares to be applied to the message
     * @param handlerFunction the final message handler function to execute after all middlewares
     * @param <M> the type of the message, extending {@link Message}
     * @param <R> the type of the result returned by the message handler
     * @return a {@link Function} that represents the entire chain of middlewares and the message handler
     */
    @SuppressWarnings("unchecked")
    public <M extends Message, R> Function<M, R> buildChain(
            List<Middleware> middlewares,
            Function<M, R> handlerFunction
    ) {
        Function<M, R> chain = handlerFunction;
        for (int i = middlewares.size() - 1; i >= 0; i--) {
            Middleware middleware = middlewares.get(i);
            Function<M, R> next = chain;
            chain = command -> (R) middleware.execute(command, next);
        }
        return chain;
    }
}
