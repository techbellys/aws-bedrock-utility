package io.github.techbellys.utility.bedrock.service.impl;

import io.github.techbellys.utility.bedrock.service.BedrockAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.bedrockagentruntime.BedrockAgentRuntimeAsyncClient;
import software.amazon.awssdk.services.bedrockagentruntime.model.InvokeAgentRequest;
import software.amazon.awssdk.services.bedrockagentruntime.model.InvokeAgentResponseHandler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class BedrockAgentServiceImpl implements BedrockAgentService {

    @Autowired
    private BedrockAgentRuntimeAsyncClient bedrockAgentRuntimeAsyncClient;

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
        } catch (ExecutionException e) {
            // Handle the cause of the failure
            // e.g., log the error, rethrow as runtime exception, etc.
        }

        // Return the accumulated response
        return responseBuilder.toString();
    }
}
