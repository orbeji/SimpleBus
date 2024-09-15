package cat.bernado.simplebus.middleware;

import cat.bernado.simplebus.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * Middleware example that logs every Message received in the Bus
 * @param <M>
 * @param <R>
 */
@Component
public class LoggingMiddleware<M extends Message, R> implements Middleware<M, R> {

    private static final Logger logger = LoggerFactory.getLogger(LoggingMiddleware.class);

    @Override
    public R execute(M message, Function<M, R> next) {
        logger.info(String.format("Received %s", message.getClass().getName()), message);
        try {
            //Important! pass the execution to the next layer
            R result = next.apply(message);
            logger.info("Command handled successfully");
            return result;
        } catch(Exception ex) {
            logger.error(String.format("Exception while handling %s", message.getClass().getName()), ex);
            throw ex;
        }
    }
}
