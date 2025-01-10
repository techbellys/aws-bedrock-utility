# AWS Bedrock Utility Library

This library provides utility services for interacting with AWS Bedrock, including support for Bedrock Agents, Knowledge Base queries, and content moderation.

## Features
- **Bedrock Agent Service**: Invoke Bedrock Agents to retrieve responses based on a given prompt.
- **Knowledge Base Service**: Process user queries using AWS Bedrock Knowledge Base with Retrieve and Generate (RAG) methodology.
- **Moderation Service**: Analyze text for abusive language, profanity, or negativity using a moderation model.

---

## Installation
Add the library dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>io.github.techbellys.utility</groupId>
    <artifactId>bedrock</artifactId>
    <version>1.0.5</version>
</dependency>
```

---

## Configuration
Provide the necessary AWS Bedrock credentials and configuration in your Spring Boot application's `application.yml`:

```yaml
aws:
  bedrock:
    region: ${BEDROCK_REGION}
    credentials:
      access-key: ${BEDROCK_AWS_ACCESS_KEY}
      secret-key: ${BEDROCK_AWS_SECRET_KEY}
```

You can set these environment variables (`BEDROCK_REGION`, `BEDROCK_AWS_ACCESS_KEY`, and `BEDROCK_AWS_SECRET_KEY`) in your deployment environment.

---

## Usage

### Import Configuration
To enable the `BedrockAutoConfiguration`, make sure to import it in your Spring Boot application entry point or any configuration class.

#### Example:
```java
import io.github.techbellys.utility.bedrock.config.BedrockAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(BedrockAutoConfiguration.class)
public class StartApplication {
    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }
}
```

---

### Bedrock Agent Service
The `BedrockAgentService` allows you to invoke an AWS Bedrock Agent with a given prompt.

#### Example:
```java
import io.github.techbellys.utility.bedrock.service.BedrockAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AgentExample {

    @Autowired
    private BedrockAgentService bedrockAgentService;

    public void invokeAgentExample() {
        String prompt = "Hello, how can I help you?";
        String agentId = "your-agent-id";
        String agentAliasId = "your-agent-alias-id";
        String sessionId = "unique-session-id";

        String response = bedrockAgentService.invokeBedrockAgent(prompt, agentId, agentAliasId, sessionId);
        System.out.println("Agent Response: " + response);
    }
}
```

---

### Knowledge Base Service
The `KnowledgeBaseService` allows you to process user queries using AWS Bedrock Knowledge Base.

#### Example:
```java
import io.github.techbellys.utility.bedrock.service.KnowledgeBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KnowledgeBaseExample {

    @Autowired
    private KnowledgeBaseService knowledgeBaseService;

    public void processQueryExample() {
        String modelId = "your-model-id";
        String knowledgeBaseId = "your-knowledge-base-id";
        String query = "What is the capital of France?";

        String response = knowledgeBaseService.processQuery(modelId, knowledgeBaseId, query);
        System.out.println("Knowledge Base Response: " + response);
    }
}
```

---

### Moderation Service
The `ModerationService` asynchronously analyzes text for harmful language.

#### Example:
```java
import io.github.techbellys.utility.bedrock.service.ModerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class ModerationExample {

    @Autowired
    private ModerationService moderationService;

    public void moderateContentExample() {
        String modelId = "your-moderation-model-id";
        String text = "This is a test message.";

        CompletableFuture<Boolean> isSafeFuture = moderationService.moderateContent(modelId, text);

        isSafeFuture.thenAccept(isSafe -> {
            if (isSafe) {
                System.out.println("The content is safe.");
            } else {
                System.out.println("The content contains harmful language.");
            }
        });
    }
}
```

---

## License
This library is licensed under the [MIT License](LICENSE).

---

## Contributing
Contributions are welcome! Please fork the repository and submit a pull request.

---

## Support
If you encounter any issues, please create a new issue on the [GitHub Issues page](https://github.com/techbellys/aws-bedrock-utility/issues).
