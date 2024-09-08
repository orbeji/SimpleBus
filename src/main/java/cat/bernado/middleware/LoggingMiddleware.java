package cat.bernado.middleware;

import cat.bernado.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class LoggingMiddleware<M extends Message, R> implements Middleware<M, R> {

    private static final Logger logger = LoggerFactory.getLogger(LoggingMiddleware.class);

    @Override
    public R execute(M message, Function<M, R> next) {
        logger.info(String.format("Received %s", message.getClass().getName()), message);
        try {
            R result = next.apply(message);
            logger.info("Command handled successfully");
            return result;
        } catch(Exception ex) {
            logger.error(String.format("Exception while handling %s", message.getClass().getName()), ex);
            throw ex;
        }
    }
}
