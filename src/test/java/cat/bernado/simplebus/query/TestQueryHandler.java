package cat.bernado.simplebus.query;

public class TestQueryHandler implements QueryHandler<TestQuery, Integer> {
    @Override
    public Integer execute(TestQuery query) throws Exception {
        return 2;
    }
}
