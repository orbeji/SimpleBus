package cat.bernado.simplebus.command;

import cat.bernado.simplebus.message.MessageHandler;

/**
 * Interface for handling commands in the SimpleBus library.
 * <p>
 * Implementations of this interface are responsible for processing commands.
 * A command is typically an action that changes the state of the system.
 *
 * @param <C> the type of command to be handled, extending the {@link Command} interface
 */
public interface CommandHandler <C extends Command> extends MessageHandler {

    /**
     * Executes the given command.
     * <p>
     * This method processes the command and applies the necessary business logic.
     * Implementers should handle any exceptions that may arise during command execution.
     *
     * @param command the command to be executed
     * @throws Exception if an error occurs during command execution
     */
    void execute(C command) throws Exception;
}
