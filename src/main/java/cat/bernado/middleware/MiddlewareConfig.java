package cat.bernado.middleware;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Configuration
public class MiddlewareConfig {

    @Bean
    public List<Middleware> middlewares(LoggingMiddleware loggingMiddleware) {
        return Collections.singletonList(loggingMiddleware);
    }
}