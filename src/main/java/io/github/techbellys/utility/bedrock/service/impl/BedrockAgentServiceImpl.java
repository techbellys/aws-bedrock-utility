package io.github.techbellys.utility.bedrock.service.impl;

import io.github.techbellys.utility.bedrock.service.BedrockAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.bedrockagentruntime.BedrockAgentRuntimeAsyncClient;
import software.amazon.awssdk.services.bedrockagentruntime.model.InvokeAgentRequest;
import software.amazon.awssdk.services.bedrockagentruntime.model.InvokeAgentResponseHandler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Implementation of the {@link BedrockAgentService} interface.
 * Provides functionality to invoke an AWS Bedrock Agent asynchronously and retrieve its response.
 */
@Service
public class BedrockAgentServiceImpl implements BedrockAgentService {

    @Autowired
    private BedrockAgentRuntimeAsyncClient bedrockAgentRuntimeAsyncClient;

    /**
     * Invokes an AWS Bedrock Agent asynchronously with the given prompt and parameters.
     *
     * @param prompt       The input text to send to the agent.
     * @param agentId      The ID of the agent to invoke.
     * @param agentAliasId The alias ID of the agent.
     * @param sessionId    The session ID for maintaining agent context across interactions.
     * @return The accumulated response text from the agent.
     */
    @Override
    public String invokeBedrockAgent(String prompt,
                                     String agentId,
                                     String agentAliasId,
                                     String sessionId) {

        // Use StringBuilder for accumulating chunks from the response
        StringBuilder responseBuilder = new StringBuilder();

        // Build the chunk subscriber handler
        InvokeAgentResponseHandler handler = InvokeAgentResponseHandler.builder()
                .subscriber(InvokeAgentResponseHandler.Visitor.builder()
                        .onChunk(chunk -> responseBuilder.append(chunk.bytes().asUtf8String()))
                        .build())
                .build();

        // Create the request object
        InvokeAgentRequest request = InvokeAgentRequest.builder()
                .agentId(agentId)
                .agentAliasId(agentAliasId)
                .sessionId(sessionId)
                .inputText(prompt)
                .build();

        // Invoke the agent (asynchronously)
        CompletableFuture<Void> future = bedrockAgentRuntimeAsyncClient.invokeAgent(request, handler);

        // Wait for the async operation to complete before returning the accumulated text
        try {
            future.get(); // or future.join() if you prefer unchecked exceptions
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            // Handle interruption, e.g., log and return partial result or throw a custom exception
            throw new RuntimeException("Agent invocation was interrupted.", e);
        } catch (ExecutionException e) {
            // Handle the cause of the failure
            throw new RuntimeException("Error occurred during agent invocation: " + e.getCause().getMessage(), e);
        }

        // Return the accumulated response
        return responseBuilder.toString();
    }
}
