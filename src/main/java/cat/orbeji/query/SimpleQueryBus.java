package cat.orbeji.query;

import cat.orbeji.message.MessageHandlerFinder;
import cat.orbeji.middleware.Middleware;
import cat.orbeji.middleware.MiddlewareChainBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class SimpleQueryBus implements QueryBus {
    private final MessageHandlerFinder messageHandlerFinder;
    private static final Logger logger = LoggerFactory.getLogger(SimpleQueryBus.class);
    private final MiddlewareChainBuilder middlewareChainBuilder;
    private final List<Middleware> middlewares;

    public SimpleQueryBus(
            MessageHandlerFinder messageHandlerFinder,
            MiddlewareChainBuilder middlewareChainBuilder,
            List<Middleware> middlewares
    ) {
        this.messageHandlerFinder = messageHandlerFinder;
        this.middlewareChainBuilder = middlewareChainBuilder;
        this.middlewares = middlewares;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <C extends Query, R> R handle(C command) throws Exception {
        QueryHandler<C, R> queryHandler = (QueryHandler<C, R>) this.messageHandlerFinder.find(command);

        Function<C, R> handlerFunction = cmd -> {
            try {
                return queryHandler.execute(cmd);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        Function<C, R> chain = middlewareChainBuilder.buildChain(middlewares, handlerFunction);

        return chain.apply(command);
    }
}
