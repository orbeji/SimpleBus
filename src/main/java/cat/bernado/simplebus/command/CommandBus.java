package cat.bernado.simplebus.command;

/**
 * Message bus that matches a Command to its CommandHandler and executes it
 */
public interface CommandBus {
    <C extends Command> void handle(C command) throws Exception;
}
