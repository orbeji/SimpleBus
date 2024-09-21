package cat.bernado.simplebus.command;

import cat.bernado.simplebus.message.MessageHandlerFinder;
import cat.bernado.simplebus.middleware.Middleware;
import cat.bernado.simplebus.middleware.MiddlewareChainBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

/**
 * Implementation of the {@link CommandBus} that handles the execution of commands.
 * <p>
 * This class is responsible for finding the correct {@link CommandHandler} for a given command, building a middleware
 * execution chain, and invoking the handler. It leverages {@link Middleware} for additional processing logic.
 */
@Service
public class SimpleCommandBus implements CommandBus {
    private final MessageHandlerFinder messageHandlerFinder;
    private final MiddlewareChainBuilder middlewareChainBuilder;
    private final List<Middleware> middlewares;

    /**
     * Constructs a {@code SimpleCommandBus} with the required dependencies.
     *
     * @param messageHandlerFinder the service to find the appropriate {@link CommandHandler}
     * @param middlewareChainBuilder the builder for constructing the middleware execution chain
     * @param middlewares the list of middleware to apply during command execution
     */
    public SimpleCommandBus(
            MessageHandlerFinder messageHandlerFinder,
            MiddlewareChainBuilder middlewareChainBuilder,
            List<Middleware> middlewares
    ) {
        this.messageHandlerFinder = messageHandlerFinder;
        this.middlewareChainBuilder = middlewareChainBuilder;
        this.middlewares = middlewares;
    }

    /**
     * Handles the execution of a command.
     * <p>
     * This method finds the appropriate {@link CommandHandler} for the given command, constructs a middleware chain,
     * and executes the handler with the command.
     *
     * @param command the command to be handled
     * @param <C> the type of command, extending {@link Command}
     * @throws Exception if an error occurs during command execution
     */
    @SuppressWarnings("unchecked")
    @Override
    public <C extends Command> void handle(C command) throws Exception {
        // Find the appropriate command handler
        CommandHandler<C> commandHandler = (CommandHandler<C>) this.messageHandlerFinder.find(command);

        // Define the handler function that will execute the command
        Function<C, Void> handlerFunction = cmd -> {
            try {
                commandHandler.execute(cmd);
                return null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        // Build the middleware chain and execute the command
        Function<C, Void> chain = middlewareChainBuilder.buildChain(middlewares, handlerFunction);

        // Apply the chain to the command
        chain.apply(command);
    }
}
