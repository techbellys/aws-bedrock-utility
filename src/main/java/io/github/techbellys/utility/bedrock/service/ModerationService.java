package io.github.techbellys.utility.bedrock.service;

import java.util.concurrent.CompletableFuture;

public interface ModerationService {
    CompletableFuture<Boolean> moderateContent(String modelId, String text);
}
