package io.github.techbellys.utility.bedrock.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrock.BedrockClient;
import software.amazon.awssdk.services.bedrockagent.BedrockAgentClient;
import software.amazon.awssdk.services.bedrockagentruntime.BedrockAgentRuntimeAsyncClient;
import software.amazon.awssdk.services.bedrockagentruntime.BedrockAgentRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeAsyncClient;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;

/**
 * Configuration class for AWS Bedrock clients.
 * Provides beans for various Bedrock-related clients using AWS SDK.
 */
@Configuration
public class AwsBedrockConfig {

    /**
     * AWS region for Bedrock services.
     */
    @Value("${aws.bedrock.region}")
    private String region;

    /**
     * AWS access key for authentication.
     */
    @Value("${aws.bedrock.credentials.access-key}")
    private String accessKey;

    /**
     * AWS secret key for authentication.
     */
    @Value("${aws.bedrock.credentials.secret-key}")
    private String secretKey;

    /**
     * Creates a {@link BedrockClient} bean.
     *
     * @return the configured BedrockClient
     */
    @Bean
    public BedrockClient bedrockClient() {
        return BedrockClient.builder()
                .region(Region.of(region))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(accessKey, secretKey)
                        )
                )
                .build();
    }

    /**
     * Creates a {@link BedrockAgentClient} bean.
     *
     * @return the configured BedrockAgentClient
     */
    @Bean
    public BedrockAgentClient bedrockAgentClient() {
        return BedrockAgentClient.builder()
                .region(Region.of(region))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(accessKey, secretKey)
                        )
                )
                .build();
    }

    /**
     * Creates a {@link BedrockRuntimeClient} bean.
     *
     * @return the configured BedrockRuntimeClient
     */
    @Bean
    public BedrockRuntimeClient bedrockRuntimeClient() {
        return BedrockRuntimeClient.builder()
                .region(Region.of(region))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(accessKey, secretKey)
                        )
                )
                .build();
    }

    /**
     * Creates a {@link BedrockRuntimeAsyncClient} bean.
     *
     * @return the configured BedrockRuntimeAsyncClient
     */
    @Bean
    public BedrockRuntimeAsyncClient bedrockRuntimeAsyncClient() {
        return BedrockRuntimeAsyncClient.builder()
                .region(Region.of(region))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(accessKey, secretKey)
                        )
                )
                .build();
    }

    /**
     * Creates a {@link BedrockAgentRuntimeAsyncClient} bean.
     *
     * @return the configured BedrockAgentRuntimeAsyncClient
     */
    @Bean
    public BedrockAgentRuntimeAsyncClient bedrockAgentRuntimeAsyncClient() {
        return BedrockAgentRuntimeAsyncClient.builder()
                .region(Region.of(region))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(accessKey, secretKey)
                        )
                )
                .build();
    }

    /**
     * Creates a {@link BedrockAgentRuntimeClient} bean.
     *
     * @return the configured BedrockAgentRuntimeClient
     */
    @Bean
    public BedrockAgentRuntimeClient bedrockAgentRuntime() {
        return BedrockAgentRuntimeClient.builder()
                .region(Region.of(region))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(accessKey, secretKey)
                        )
                )
                .build();
    }
}
