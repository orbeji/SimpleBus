package cat.bernado.simplebus.command;

public interface CommandBus {
    <C extends Command> void handle(C command) throws Exception;
}
