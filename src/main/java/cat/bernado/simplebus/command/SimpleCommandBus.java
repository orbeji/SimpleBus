package cat.bernado.simplebus.command;

import cat.bernado.simplebus.message.MessageHandlerFinder;
import cat.bernado.simplebus.middleware.Middleware;
import cat.bernado.simplebus.middleware.MiddlewareChainBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class SimpleCommandBus implements CommandBus {
    private final MessageHandlerFinder messageHandlerFinder;
    private final MiddlewareChainBuilder middlewareChainBuilder;
    private final List<Middleware> middlewares;

    public SimpleCommandBus(MessageHandlerFinder messageHandlerFinder,
                            MiddlewareChainBuilder middlewareChainBuilder,
                            List<Middleware> middlewares) {
        this.messageHandlerFinder = messageHandlerFinder;
        this.middlewareChainBuilder = middlewareChainBuilder;
        this.middlewares = middlewares;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <C extends Command> void handle(C command) throws Exception {
        CommandHandler<C> commandHandler = (CommandHandler<C>) this.messageHandlerFinder.find(command);

        Function<C, Void> handlerFunction = cmd -> {
            try {
                commandHandler.execute(cmd);
                return null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        Function<C, Void> chain = middlewareChainBuilder.buildChain(middlewares, handlerFunction);

        chain.apply(command);
    }
}
