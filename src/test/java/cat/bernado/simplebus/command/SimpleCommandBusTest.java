package cat.bernado.simplebus.command;

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

class SimpleCommandBusTest {
    @Test
    void test() throws Exception {
        ApplicationContext applicationContext = mock(ApplicationContext.class);
        String[] returnValue = new String[1];
        returnValue[0] = "handlerBeanName";
        Mockito.when(applicationContext.getBeanNamesForType(MessageHandler.class)).thenReturn(returnValue);
        TestCommandHandler testCommandHandler = spy(new TestCommandHandler());
        Mockito.when(applicationContext.getBean("handlerBeanName")).thenReturn(testCommandHandler);
        MessageHandlerFinder messageHandlerFinder = new MessageHandlerFinder(applicationContext);

        List<Middleware> middlewares = new ArrayList<>();
        SimpleCommandBus simpleCommandBus = new SimpleCommandBus(messageHandlerFinder, new MiddlewareChainBuilder(), middlewares);

        TestCommand testCommand = new TestCommand();
        simpleCommandBus.handle(testCommand);

        verify(testCommandHandler).execute(testCommand);
    }
}