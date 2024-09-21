package cat.bernado.simplebus.command;

/**
 * Message bus that matches a Command to its CommandHandler and executes it
 */
public interface CommandBus {
    /**
     * Method that will receive a Command and execute the corresponding CommandHandler.
     * Is also responsible to execute all the configured Middlewares in the correct order.
     *
     * @param command Command
     * @param <C> Command should extend the interface Command
     * @throws Exception Base exception that the handler can throw
     */
    <C extends Command> void handle(C command) throws Exception;
}
