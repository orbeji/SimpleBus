package cat.bernado.command;

import cat.bernado.message.MessageHandler;

public interface CommandHandler <C extends Command> extends MessageHandler {
    void execute(C command) throws Exception;
}
