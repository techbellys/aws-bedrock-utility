package io.github.techbellys.utility.bedrock.model;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelRequest;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelResponse;

/**
 * Service class to interact with various models using AWS Bedrock runtime for content moderation and text generation.
 */
@Service
public class ModelService {

    private static final Logger logger = LoggerFactory.getLogger(ModelService.class);

    @Autowired
    private BedrockRuntimeClient bedrockRuntimeClient;

    /**
     * Invokes a specified model with the given prompt and parameters.
     *
     * @param modelId     The ID of the model to invoke.
     * @param prompt      The input prompt for the model.
     * @param temperature The temperature parameter for text generation.
     * @param maxTokens   The maximum number of tokens to generate.
     * @return The generated completion from the model.
     */
    public String invoke(String modelId, String prompt, double temperature, int maxTokens) {
        validateParameters(temperature, maxTokens);

        try {
            // Prepare the JSON body for the request
            JSONObject jsonBody = createRequestBody(prompt, temperature, maxTokens);

            // Build and execute the request
            InvokeModelRequest request = InvokeModelRequest.builder()
                    .modelId(modelId)
                    .body(SdkBytes.fromUtf8String(jsonBody.toString()))
                    .build();

            InvokeModelResponse response = bedrockRuntimeClient.invokeModel(request);

            // Parse and return the response
            return parseResponse(response);
        } catch (Exception e) {
            logger.error("Error invoking model {}. Prompt: {}, Error: {}", modelId, prompt, e.getMessage(), e);
            throw new RuntimeException("Failed to invoke model " + modelId, e);
        }
    }

    /**
     * Validates input parameters for the model invocation.
     *
     * @param temperature The temperature parameter for text generation.
     * @param maxTokens   The maximum number of tokens to generate.
     */
    private void validateParameters(double temperature, int maxTokens) {
        if (temperature < 0 || temperature > 1) {
            throw new IllegalArgumentException("Temperature must be between 0 and 1. Provided: " + temperature);
        }
        if (maxTokens <= 0 || maxTokens > 2048) {
            throw new IllegalArgumentException("maxTokens must be between 1 and 2048. Provided: " + maxTokens);
        }
    }

    /**
     * Creates the request body for the model invocation.
     *
     * @param prompt      The prompt to send to the model.
     * @param temperature The temperature for text generation.
     * @param maxTokens   The maximum tokens for the output.
     * @return A JSONObject representing the request body.
     */
    private JSONObject createRequestBody(String prompt, double temperature, int maxTokens) {
        return new JSONObject()
                .put("prompt", "Human: " + prompt + " Assistant:")
                .put("temperature", temperature)
                .put("max_tokens_to_sample", maxTokens);
    }

    /**
     * Parses the model response to extract the completion.
     *
     * @param response The response from the model.
     * @return The extracted completion text.
     */
    private String parseResponse(InvokeModelResponse response) {
        return new JSONObject(response.body().asUtf8String()).getString("completion");
    }
}
