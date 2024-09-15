package cat.bernado.simplebus.query;

import cat.bernado.simplebus.message.MessageHandler;
import cat.bernado.simplebus.message.MessageHandlerFinder;
import cat.bernado.simplebus.middleware.Middleware;
import cat.bernado.simplebus.middleware.MiddlewareChainBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class SimpleQueryBusTest {
    @Test
    void test() throws Exception {
        ApplicationContext applicationContext = mock(ApplicationContext.class);
        String[] returnValue = new String[1];
        returnValue[0] = "handlerBeanName";
        Mockito.when(applicationContext.getBeanNamesForType(MessageHandler.class)).thenReturn(returnValue);
        TestQueryHandler testQueryHandler = spy(new TestQueryHandler());
        Mockito.when(applicationContext.getBean("handlerBeanName")).thenReturn(testQueryHandler);
        MessageHandlerFinder messageHandlerFinder = new MessageHandlerFinder(applicationContext);

        List<Middleware> middlewares = new ArrayList<>();
        SimpleQueryBus simpleQueryBus = new SimpleQueryBus(messageHandlerFinder, new MiddlewareChainBuilder(), middlewares);

        TestQuery testQuery = new TestQuery();
        simpleQueryBus.handle(testQuery);

        verify(testQueryHandler).execute(testQuery);
    }
}