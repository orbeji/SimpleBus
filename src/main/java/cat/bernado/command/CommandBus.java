package cat.bernado.command;

public interface CommandBus {
    <C extends Command> void handle(C command) throws Exception;
}
