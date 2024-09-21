package cat.bernado.simplebus.middleware;

import cat.bernado.simplebus.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * Middleware that logs every {@link Message} received by the Bus.
 * <p>
 * This middleware logs the class name of the message when it is received and logs the outcome of the message handling.
 * It delegates the processing to the next middleware or handler in the chain and logs any exceptions that occur.
 *
 * @param <M> Class that extends the {@link Message} interface
 * @param <R> Type of response expected
 */
@Component
public class LoggingMiddleware<M extends Message, R> implements Middleware<M, R> {

    private static final Logger logger = LoggerFactory.getLogger(LoggingMiddleware.class);

    /**
     * Constructor
     */
    public LoggingMiddleware() {
    }

    /**
     * Executes the middleware logic.
     * <p>
     * Logs the received message and then passes the execution to the next middleware or handler in the chain.
     * If an exception occurs during execution, it logs the error and rethrows the exception.
     *
     * @param message the message being processed
     * @param next the next function in the chain to call
     * @return the result of the next middleware or handler
     */
    @Override
    public R execute(M message, Function<M, R> next) {
        logger.info(String.format("Received %s", message.getClass().getName()), message);
        try {
            // Important! Pass the execution to the next layer in the chain
            R result = next.apply(message);
            logger.info("Command handled successfully");
            return result;
        } catch (Exception ex) {
            logger.error(String.format("Exception while handling %s", message.getClass().getName()), ex);
            throw ex;
        }
    }
}
