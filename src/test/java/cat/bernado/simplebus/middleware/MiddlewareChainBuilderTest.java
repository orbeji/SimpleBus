package cat.bernado.simplebus.middleware;

import cat.bernado.simplebus.command.TestCommand;
import cat.bernado.simplebus.command.TestCommandHandler;
import cat.bernado.simplebus.query.TestQuery;
import cat.bernado.simplebus.query.TestQueryHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class MiddlewareChainBuilderTest {
    private MiddlewareChainBuilder middlewareChainBuilder;
    @BeforeEach
    void setUp() {
        middlewareChainBuilder = new MiddlewareChainBuilder();
    }

    @Test
    void testExecution() throws Exception {
        // Crear mocks para el middleware y el handler
        LoggingMiddleware loggingMiddleware = new LoggingMiddleware();
        TestQueryHandler testQueryHandler = new TestQueryHandler();

        // Lista de middlewares
        ArrayList<Middleware> middlewareArrayList = new ArrayList<>();
        middlewareArrayList.add(loggingMiddleware);

        // Definir el handlerFunction con el handler mockeado
        Function<TestQuery, Integer> handlerFunction = cmd -> {
            try {
                return testQueryHandler.execute(cmd);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        // Construir la cadena con el middleware y el handler
        Function<TestQuery, Integer> chain = middlewareChainBuilder.buildChain(middlewareArrayList, handlerFunction);

        // Ejecutar el chain con un comando de prueba
        TestQuery testQuery = new TestQuery();
        Integer result = chain.apply(testQuery);

        assertEquals(2, result);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testExecutionOrder() throws Exception {
        LoggingMiddleware loggingMiddleware = spy(new LoggingMiddleware());
        TestCommandHandler testCommandHandler = mock(TestCommandHandler.class);

        ArrayList<Middleware> middlewareArrayList = new ArrayList<>();
        middlewareArrayList.add(loggingMiddleware);

        Function<TestCommand, Void> handlerFunction = cmd -> {
            try {
                testCommandHandler.execute(cmd);
                return null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        Function<TestCommand, Void> chain = middlewareChainBuilder.buildChain(middlewareArrayList, handlerFunction);

        TestCommand testCommand = new TestCommand();
        chain.apply(testCommand);

        verify(loggingMiddleware).execute(eq(testCommand), any());

        verify(testCommandHandler).execute(testCommand);
    }
}