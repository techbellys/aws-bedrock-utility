package io.github.techbellys.utility.bedrock.knowledgebase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.bedrockagent.BedrockAgentClient;
import software.amazon.awssdk.services.bedrockagent.model.*;

/**
 * Helper service for synchronizing documents with the AWS Bedrock Knowledge Base.
 * Provides functionality to ingest documents into a specified knowledge base.
 */
public class KnowledgeBaseSyncHelper {

    private static final Logger logger = LoggerFactory.getLogger(KnowledgeBaseSyncHelper.class);

    private final BedrockAgentClient bedrockAgentClient;

    /**
     * Constructs a new {@code KnowledgeBaseSyncHelper} with the specified Bedrock Agent client.
     *
     * @param bedrockAgentClient The {@link BedrockAgentClient} used to communicate with the Bedrock service.
     */
    public KnowledgeBaseSyncHelper(BedrockAgentClient bedrockAgentClient) {
        this.bedrockAgentClient = bedrockAgentClient;
    }

    /**
     * Ingests a document into the specified knowledge base and data source.
     *
     * @param knowledgeBaseId The ID of the knowledge base where the document will be ingested.
     * @param dataSourceId    The ID of the data source associated with the knowledge base.
     * @param documentId      A unique identifier for the document.
     * @param content         The content of the document to be ingested.
     */
    public void ingestDocument(String knowledgeBaseId, String dataSourceId, String documentId, String content) {
        try {
            // Build the inline content
            InlineContent inlineContent = InlineContent.builder()
                    .textContent(TextContentDoc.builder()
                            .data(content)
                            .build())
                    .type("TEXT")
                    .build();

            // Build the custom content
            CustomContent customContent = CustomContent.builder()
                    .customDocumentIdentifier(CustomDocumentIdentifier.builder()
                            .id(documentId)
                            .build())
                    .inlineContent(inlineContent)
                    .sourceType("IN_LINE")
                    .build();

            // Build the document content
            DocumentContent documentContent = DocumentContent.builder()
                    .custom(customContent)
                    .dataSourceType("CUSTOM")
                    .build();

            // Build the knowledge base document
            KnowledgeBaseDocument document = KnowledgeBaseDocument.builder()
                    .content(documentContent)
                    .build();

            // Create the ingestion request
            IngestKnowledgeBaseDocumentsRequest request = IngestKnowledgeBaseDocumentsRequest.builder()
                    .knowledgeBaseId(knowledgeBaseId)
                    .dataSourceId(dataSourceId)
                    .documents(document)
                    .build();

            // Make the API call
            IngestKnowledgeBaseDocumentsResponse response = bedrockAgentClient.ingestKnowledgeBaseDocuments(request);
            logger.info("Document ingested successfully. Response: {}", response);

        } catch (BedrockAgentException e) {
            logger.error("Bedrock Agent error: {}", e.awsErrorDetails().errorMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected error ingesting document: {}", e.getMessage(), e);
        }
    }
}
