package io.github.techbellys.utility.bedrock.service.impl;

import io.github.techbellys.utility.bedrock.model.ModelService;
import io.github.techbellys.utility.bedrock.service.ModerationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Implementation of the {@link ModerationService} interface.
 * Provides functionality for content moderation by analyzing text for abusive language, profanity, or negativity.
 */
public class ModerationServiceImpl implements ModerationService {

    private static final Logger logger = LoggerFactory.getLogger(ModerationServiceImpl.class);

    @Autowired
    private ModelService modelService;

    /**
     * Asynchronously performs content moderation by analyzing the given text for harmful language.
     *
     * @param modelId The ID of the moderation model to use.
     * @param text    The text to analyze for abusive language, profanity, or negativity.
     * @return A {@link CompletableFuture} containing a {@code Boolean} indicating if the content is safe.
     *         {@code true} if the content is clean, {@code false} otherwise.
     */
    @Async
    @Override
    public CompletableFuture<Boolean> moderateContent(String modelId, String text) {
        // Prepare the moderation prompt
        String moderationPrompt = """
            You are a content moderation tool. Analyze the following text for abusive language, profanity, or negativity.
            If the content is clean and does not contain any harmful language, respond with \"true\".
            If the content is abusive, profane, or negative, respond with \"false\".

            Text: """;

        String fullPrompt = moderationPrompt + text;

        try {
            // Invoke the model service to process the moderation request
            String response = modelService.invoke(modelId, fullPrompt, 0.5, 200);
            boolean isSafe = "true".equalsIgnoreCase(response.trim());
            return CompletableFuture.completedFuture(isSafe);
        } catch (Exception e) {
            logger.error("Content moderation failed for text: {}, Error: {}", text, e.getMessage(), e);
            throw new RuntimeException("Content moderation failed", e);
        }
    }
}
