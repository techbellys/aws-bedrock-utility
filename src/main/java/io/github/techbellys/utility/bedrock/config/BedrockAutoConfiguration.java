package io.github.techbellys.utility.bedrock.config;

import io.github.techbellys.utility.bedrock.knowledgebase.KnowledgeBase;
import io.github.techbellys.utility.bedrock.model.ModelService;
import io.github.techbellys.utility.bedrock.service.ModerationService;
import io.github.techbellys.utility.bedrock.service.KnowledgeBaseService;
import io.github.techbellys.utility.bedrock.service.BedrockAgentService;
import io.github.techbellys.utility.bedrock.service.impl.ModerationServiceImpl;
import io.github.techbellys.utility.bedrock.service.impl.KnowledgeBaseServiceImpl;
import io.github.techbellys.utility.bedrock.service.impl.BedrockAgentServiceImpl;
import io.github.techbellys.utility.bedrock.knowledgebase.KnowledgeBaseSyncHelper;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrock.BedrockClient;
import software.amazon.awssdk.services.bedrockagent.BedrockAgentClient;
import software.amazon.awssdk.services.bedrockagentruntime.BedrockAgentRuntimeAsyncClient;
import software.amazon.awssdk.services.bedrockagentruntime.BedrockAgentRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeAsyncClient;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Auto-configuration for AWS Bedrock Utility Library.
 *
 * <p>This configuration class defines Spring beans for interacting with AWS Bedrock services.
 * It includes clients for Bedrock, Bedrock Agent, and runtime clients, as well as service
 * implementations for moderation, knowledge base, and agent services.</p>
 */
@Configuration
public class BedrockAutoConfiguration {

    static {
        System.out.println("BedrockAutoConfiguration loaded!");
    }

    @Value("${aws.bedrock.region}")
    private String region;

    @Value("${aws.bedrock.credentials.access-key}")
    private String accessKey;

    @Value("${aws.bedrock.credentials.secret-key}")
    private String secretKey;

    /**
     * Creates a {@link StaticCredentialsProvider} to provide AWS credentials for all Bedrock services.
     *
     * @return the configured StaticCredentialsProvider instance
     */
    private StaticCredentialsProvider credentialsProvider() {
        return StaticCredentialsProvider.create(
                AwsBasicCredentials.create(accessKey, secretKey)
        );
    }

    /**
     * Creates a {@link BedrockClient} bean for interacting with AWS Bedrock.
     *
     * @return the configured BedrockClient instance
     */
    @Bean
    public BedrockClient bedrockClient() {
        return BedrockClient.builder()
                .region(Region.of(region))
                .credentialsProvider(credentialsProvider())
                .build();
    }

    /**
     * Creates a {@link BedrockAgentClient} bean for interacting with AWS Bedrock Agents.
     *
     * @return the configured BedrockAgentClient instance
     */
    @Bean
    public BedrockAgentClient bedrockAgentClient() {
        return BedrockAgentClient.builder()
                .region(Region.of(region))
                .credentialsProvider(credentialsProvider())
                .build();
    }

    /**
     * Creates a {@link BedrockRuntimeClient} bean for synchronous interactions with AWS Bedrock Runtime.
     *
     * @return the configured BedrockRuntimeClient instance
     */
    @Bean
    public BedrockRuntimeClient bedrockRuntimeClient() {
        return BedrockRuntimeClient.builder()
                .region(Region.of(region))
                .credentialsProvider(credentialsProvider())
                .build();
    }

    /**
     * Creates a {@link BedrockRuntimeAsyncClient} bean for asynchronous interactions with AWS Bedrock Runtime.
     *
     * @return the configured BedrockRuntimeAsyncClient instance
     */
    @Bean
    public BedrockRuntimeAsyncClient bedrockRuntimeAsyncClient() {
        return BedrockRuntimeAsyncClient.builder()
                .region(Region.of(region))
                .credentialsProvider(credentialsProvider())
                .build();
    }

    /**
     * Creates a {@link BedrockAgentRuntimeAsyncClient} bean for asynchronous interactions with AWS Bedrock Agent Runtime.
     *
     * @return the configured BedrockAgentRuntimeAsyncClient instance
     */
    @Bean
    public BedrockAgentRuntimeAsyncClient bedrockAgentRuntimeAsyncClient() {
        return BedrockAgentRuntimeAsyncClient.builder()
                .region(Region.of(region))
                .credentialsProvider(credentialsProvider())
                .build();
    }

    /**
     * Creates a {@link BedrockAgentRuntimeClient} bean for synchronous interactions with AWS Bedrock Agent Runtime.
     *
     * @return the configured BedrockAgentRuntimeClient instance
     */
    @Bean
    public BedrockAgentRuntimeClient bedrockAgentRuntimeClient() {
        return BedrockAgentRuntimeClient.builder()
                .region(Region.of(region))
                .credentialsProvider(credentialsProvider())
                .build();
    }

    /**
     * Configures the {@link ModerationService} bean to provide content moderation capabilities.
     *
     * @return the configured ModerationService instance
     */
    @Bean
    public ModerationService moderationService() {
        return new ModerationServiceImpl();
    }

    /**
     * Configures the {@link KnowledgeBaseService} bean to facilitate interaction with the AWS Bedrock Knowledge Base.
     *
     * @return the configured KnowledgeBaseService instance
     */
    @Bean
    public KnowledgeBaseService knowledgeBaseService() {
        return new KnowledgeBaseServiceImpl();
    }

    /**
     * Configures the {@link BedrockAgentService} bean to manage interactions with AWS Bedrock Agents.
     *
     * @return the configured BedrockAgentService instance
     */
    @Bean
    public BedrockAgentService bedrockAgentService() {
        return new BedrockAgentServiceImpl();
    }

    /**
     * Configures the {@link KnowledgeBaseSyncHelper} bean for synchronizing documents with the AWS Bedrock Knowledge Base.
     *
     * @return the configured KnowledgeBaseSyncHelper instance
     */
    @Bean
    public KnowledgeBaseSyncHelper knowledgeBaseSyncHelper() {
        return new KnowledgeBaseSyncHelper(bedrockAgentClient());
    }

    /**
     * Configures the {@link ModelService} bean for managing model-related tasks.
     *
     * @return the configured ModelService instance
     */
    @Bean
    public ModelService modelService() {
        return new ModelService();
    }

    /**
     * Configures the {@link KnowledgeBase} bean for knowledge base operations.
     *
     * @return the configured KnowledgeBase instance
     */
    @Bean
    public KnowledgeBase knowledgeBase() {
        return new KnowledgeBase();
    }
}
