package cat.bernado.simplebus.message;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Helper class to find the corresponding MessageHandler Spring service of a specific Message
 */
@Service
public class MessageHandlerFinder {
    private final ApplicationContext applicationContext;
    HashMap<String, String> cache;

    public MessageHandlerFinder(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.buildCache();
    }

    public <M extends Message> MessageHandler find(M message) {
        String handlerClass = this.cache.get(message.getClass().getName());
        return (MessageHandler) applicationContext.getBean(handlerClass);
    }

    private void buildCache() {
        cache = new HashMap<>();
        String[] handlerBeanNames = applicationContext.getBeanNamesForType(MessageHandler.class);
        for (String beanName : handlerBeanNames) {
            MessageHandler handler = (MessageHandler) applicationContext.getBean(beanName);
            String command = this.getMessage(handler);
            cache.put(command, beanName);
        }
    }

    private String getMessage(MessageHandler handler) {
        Method[] methods = handler.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals("execute") && method.getParameterCount() == 1) {
                Class<?> paramType = method.getParameterTypes()[0];
                if (!paramType.isInterface()) {
                    return paramType.getName();
                }
            }
        }
        throw new IllegalArgumentException("No suitable Message found for Handler: " + handler.getClass().getName());
    }
}