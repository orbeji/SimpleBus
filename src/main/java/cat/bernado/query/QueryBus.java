package cat.bernado.query;

public interface QueryBus {
    <Q extends Query, R> R handle(Q query) throws Exception;
}
