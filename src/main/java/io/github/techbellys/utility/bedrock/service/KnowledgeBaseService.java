package io.github.techbellys.utility.bedrock.service;

public interface KnowledgeBaseService {
    /**
     * Processes the user query using Bedrock Agent Runtime with RAG.
     *
     * @param query The user query.
     * @return The generated response.
     */
    String processQuery(String modelId, String knowledgeBaseId, String query);
}