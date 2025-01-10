package io.github.techbellys.utility.bedrock.service;

import java.util.concurrent.CompletableFuture;

/**
 * Service interface for content moderation.
 * Provides functionality to analyze text for abusive language, profanity, or negativity.
 */
public interface ModerationService {

    /**
     * Asynchronously performs content moderation by analyzing the given text for harmful language.
     *
     * @param modelId The ID of the moderation model to use.
     * @param text    The text to analyze for abusive language, profanity, or negativity.
     * @return A {@link CompletableFuture} containing a {@code Boolean} value:
     *         {@code true} if the content is safe, {@code false} otherwise.
     */
    CompletableFuture<Boolean> moderateContent(String modelId, String text);
}
