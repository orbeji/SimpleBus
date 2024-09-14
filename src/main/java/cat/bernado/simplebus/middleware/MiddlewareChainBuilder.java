package cat.bernado.simplebus.middleware;

import cat.bernado.simplebus.message.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class MiddlewareChainBuilder {

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
