package cat.bernado.simplebus.message;

import cat.bernado.simplebus.command.TestCommand;
import cat.bernado.simplebus.command.TestCommandHandler;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class MessageHandlerFinderTest {

    @Test
    void test() {
        ApplicationContext applicationContext = mock(ApplicationContext.class);
        String[] returnValue = new String[1];
        returnValue[0] = "handlerBeanName";
        Mockito.when(applicationContext.getBeanNamesForType(MessageHandler.class)).thenReturn(returnValue);
        Mockito.when(applicationContext.getBean("handlerBeanName")).thenReturn(new TestCommandHandler());
        MessageHandlerFinder messageHandlerFinder = new MessageHandlerFinder(applicationContext);
        MessageHandler messageHandler = messageHandlerFinder.find(new TestCommand());
        assertEquals(messageHandler.getClass(), TestCommandHandler.class);
    }
}