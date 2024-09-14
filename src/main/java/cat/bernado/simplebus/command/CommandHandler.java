package cat.bernado.simplebus.command;

import cat.bernado.simplebus.message.MessageHandler;

/**
 * Message handler that handles Commands and don't return any value
 */
public interface CommandHandler <C extends Command> extends MessageHandler {
    void execute(C command) throws Exception;
}
