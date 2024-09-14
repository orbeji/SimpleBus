package cat.bernado.simplebus.command;

import cat.bernado.simplebus.message.MessageHandler;

public interface CommandHandler <C extends Command> extends MessageHandler {
    void execute(C command) throws Exception;
}
