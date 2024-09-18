<a name="readme-top"></a>


<!-- PROJECT LOGO -->
<br />
<div align="center">
<h1 align="center">SimpleBus</h1>
  <p align="center">
    <a href="https://github.com/orbeji/SimpleBus/issues">Report Bug</a>
    ·
    <a href="https://github.com/orbeji/SimpleBus/issues">Request Feature</a>
  </p>
</div>

<!-- ABOUT THE PROJECT -->
## About The Project
This project provides a simple implementation of the **Command Bus** and **Query Bus** patterns, tailored for **Spring Boot** applications. It enables the decoupling of commands and queries from their handling logic, promoting cleaner architecture, better separation of concerns, and improved scalability.

## Features

- **Command Bus and Query Bus**:
  - Separate buses for handling **commands** (write operations) and **queries** (read operations), ensuring clear responsibility boundaries.

- **Spring Boot Integration**:
  - Designed to work seamlessly with Spring Boot, leveraging its dependency injection and configuration capabilities.

- **Decoupled Architecture**:
  - Commands and queries are fully decoupled from their handlers, making the system more modular and maintainable.

- **Extensibility**:
  - Supports adding custom middlewares for logging, validation, or other cross-cutting concerns, ensuring flexibility and adaptability to project needs.

- **Scalable**:
  - Easily add new commands or queries without affecting existing functionality, allowing the system to grow with minimal friction.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Use Cases

- **CQRS (Command Query Responsibility Segregation)**:
  - Ideal for applications implementing the **CQRS** pattern, separating write and read models effectively.

- **Event-Driven Architecture**:
  - Can be used in event-driven systems where commands trigger events or messages that are handled asynchronously.

- **Enterprise Applications**:
  - Suitable for large, complex systems that require a clean separation of business logic and application layers.


<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- GETTING STARTED -->
## Getting Started

To integrate the Command and Query Bus into your Spring Boot application:

### 1. Importing the project
**TODO**

### 2. Implementing a Command
A command represents an action or a request for changing the state of the application. For example, creating a new user.

#### Create the Command:

```java
import cat.bernado.simplebus.command.Command;

public class CreateUserCommand implements Command {
    private String username;
    private String email;

    public CreateUserCommand(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
```
#### Create the Command Handler:
The handler contains the logic that will be executed when the command is dispatched.

```java
import cat.bernado.simplebus.command.CommandHandler;

@Service
public class CreateUserCommandHandler implements CommandHandler<CreateUserCommand> {

    @Override
    public void handle(CreateUserCommand command) {
        // Business logic for creating a user
        System.out.println("User created: " + command.getUsername() + " with email: " + command.getEmail());
    }
}
```
### 3. Dispatching a Command
   You can dispatch a command using the Command Bus. This is typically done in a service or controller.

```java
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final CommandBus commandBus;

    public UserController(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @PostMapping("/create-user")
    public void createUser() {
        CreateUserCommand command = new CreateUserCommand("username", "email@example.es");
        commandBus.dispatch(command);
    }
}
```

### 4. Creating a Middleware
   Middlewares allow you to add extra logic, such as logging, validation, or metrics, to the command and query handling process.

**Example: Logging Middleware**
Create a middleware that logs commands before they are handled.

```java
@Component
public class LoggingMiddleware<M extends Message, R> implements Middleware<M, R> {

  private static final Logger logger = LoggerFactory.getLogger(LoggingMiddleware.class);

  @Override
  public R execute(M message, Function<M, R> next) {
    logger.info(String.format("Received %s", message.getClass().getName()), message);
    try {
      //Important! pass the execution to the next layer
      R result = next.apply(message);
      logger.info("Command handled successfully");
      return result;
    } catch(Exception ex) {
      logger.error(String.format("Exception while handling %s", message.getClass().getName()), ex);
      throw ex;
    }
  }
}
```
### 5. Configuring the Middleware
   To load middlewares into the command or query bus, configure them in your Spring Boot project.

**Example: LoggingMiddleware Configuration**
```java
@Configuration
public class MiddlewareConfig {

    @Bean
    public <M extends Message, R> List<Middleware<M, R>> middlewares(LoggingMiddleware<M, R> loggingMiddleware) {
        return Collections.singletonList(loggingMiddleware);
    }
}
```
You can create more middlewares for various purposes, such as:

* **Validation Middleware**: Validate commands or queries before they are handled.
* **Authorization Middleware**: Ensure the user has the right permissions before executing a command.
* **Exception Handling Middleware**: Capture and handle exceptions globally in your command or query bus.
Simply implement the Middleware interface and register them in the configuration as shown above.

<!-- ROADMAP -->
## Roadmap

- [ ] Publish to Maven Central Repository
- [ ] Automate creation of jar for every release
- [ ] Finish README.md
- [ ] Configure GitHub Actions to execute the tests on every change
- [ ] Create Event Bus
    - [ ] RabbitMQ transport adapter


See the [open issues](https://github.com/orbeji/SimpleBus/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTRIBUTING -->
## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to update tests as appropriate and that all the Github Actions are passing.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- LICENSE -->
## License

Distributed under the [MIT](https://choosealicense.com/licenses/mit/) License.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Project Link: [https://github.com/orbeji/SimpleBus](https://github.com/orbeji/SimpleBus)

<p align="right">(<a href="#readme-top">back to top</a>)</p>
