package cat.orbeji.command;

import cat.orbeji.message.MessageHandler;

public interface CommandHandler <C extends Command> extends MessageHandler {
    void execute(C command) throws Exception;
}
