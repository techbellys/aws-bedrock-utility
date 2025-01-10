package io.github.techbellys.utility.bedrock.service;

public interface BedrockAgentService {
    String invokeBedrockAgent(String prompt,
                              String agentId,
                              String agentAliasId,
                              String sessionId);
}
