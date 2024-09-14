package cat.bernado.simplebus.query;

public interface QueryBus {
    <Q extends Query, R> R handle(Q query) throws Exception;
}
