package cat.bernado.simplebus.message;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Helper class to find the corresponding {@link MessageHandler} Spring service for a specific {@link Message}.
 * <p>
 * This class uses the Spring {@link ApplicationContext} to locate the appropriate handler for a given message
 * and caches the results for better performance.
 */
@Service
public class MessageHandlerFinder {
    private final ApplicationContext applicationContext;
    private HashMap<String, String> cache;

    /**
     * Constructor for {@code MessageHandlerFinder}.
     * <p>
     * Initializes the class with a reference to the Spring {@link ApplicationContext} and builds the cache
     * of message-handler mappings.
     *
     * @param applicationContext the Spring application context to retrieve message handlers from
     */
    public MessageHandlerFinder(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.buildCache();
    }

    /**
     * Finds the corresponding {@link MessageHandler} for a given message.
     * <p>
     * Uses the internal cache to quickly locate the handler associated with the specific message type.
     *
     * @param message the message for which the handler needs to be found
     * @param <M> the type of the message
     * @return the message handler associated with the message
     * @throws IllegalArgumentException if no handler is found for the message
     */
    public <M extends Message> MessageHandler find(M message) {
        String handlerClass = this.cache.get(message.getClass().getName());
        return (MessageHandler) applicationContext.getBean(handlerClass);
    }

    /**
     * Builds a cache of message-handler mappings.
     * <p>
     * This method iterates over all beans of type {@link MessageHandler} and retrieves the message type that
     * each handler can process by inspecting the handler's method signature. The results are stored in a cache
     * to optimize future lookups.
     */
    private void buildCache() {
        cache = new HashMap<>();
        String[] handlerBeanNames = applicationContext.getBeanNamesForType(MessageHandler.class);
        for (String beanName : handlerBeanNames) {
            MessageHandler handler = (MessageHandler) applicationContext.getBean(beanName);
            String command = this.getMessage(handler);
            cache.put(command, beanName);
        }
    }

    /**
     * Retrieves the message type that the given {@link MessageHandler} is responsible for handling.
     * <p>
     * This method inspects the handler's declared methods and returns the name of the class that the handler
     * processes. It looks specifically for the method named {@code execute} that takes a single parameter,
     * which is the message type.
     *
     * @param handler the message handler to inspect
     * @return the name of the message type that the handler can process
     * @throws IllegalArgumentException if no suitable message type is found
     */
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
