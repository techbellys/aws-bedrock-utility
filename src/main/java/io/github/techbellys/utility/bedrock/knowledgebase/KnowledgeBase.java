package io.github.techbellys.utility.bedrock.knowledgebase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import software.amazon.awssdk.services.bedrockagentruntime.BedrockAgentRuntimeClient;
import software.amazon.awssdk.services.bedrockagentruntime.model.KnowledgeBaseRetrieveAndGenerateConfiguration;
import software.amazon.awssdk.services.bedrockagentruntime.model.RetrieveAndGenerateConfiguration;
import software.amazon.awssdk.services.bedrockagentruntime.model.RetrieveAndGenerateInput;
import software.amazon.awssdk.services.bedrockagentruntime.model.RetrieveAndGenerateRequest;
import software.amazon.awssdk.services.bedrockagentruntime.model.RetrieveAndGenerateResponse;
import software.amazon.awssdk.services.bedrockagentruntime.model.RetrieveAndGenerateType;

@Service
public class KnowledgeBase {

    private static final Logger logger = LoggerFactory.getLogger(KnowledgeBase.class);

    @Autowired
    private BedrockAgentRuntimeClient bedrockAgentRuntimeClient;

    public String process(String modelId, String knowledgeBaseId, String query) {
        return invokeModelWithRAG(modelId, knowledgeBaseId, query);
    }

    private String invokeModelWithRAG(String modelId, String knowledgeBaseId, String query) {
        try {
            // Config
            KnowledgeBaseRetrieveAndGenerateConfiguration kbConfig = KnowledgeBaseRetrieveAndGenerateConfiguration.builder()
                    .knowledgeBaseId(knowledgeBaseId)
                    .modelArn(modelId)
                    .build();

            RetrieveAndGenerateConfiguration config = RetrieveAndGenerateConfiguration.builder()
                    .knowledgeBaseConfiguration(kbConfig)
                    .type(RetrieveAndGenerateType.KNOWLEDGE_BASE)
                    .build();

            // Input
            RetrieveAndGenerateInput input = RetrieveAndGenerateInput.builder().text(query).build();

            RetrieveAndGenerateRequest request = RetrieveAndGenerateRequest.builder()
                    .input(input)
                    .retrieveAndGenerateConfiguration(config)
                    .build();
            // Response from Knowledge Base
            RetrieveAndGenerateResponse response = bedrockAgentRuntimeClient.retrieveAndGenerate(request);

            return response.output().text(); // Response generated from query
        } catch (Exception e) {
            logger.error("Error during RAG invocation: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to process query with RAG", e);
        }
    }
}
