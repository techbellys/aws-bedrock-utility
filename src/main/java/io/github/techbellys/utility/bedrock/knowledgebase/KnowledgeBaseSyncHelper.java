package io.github.techbellys.utility.bedrock.knowledgebase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.bedrockagent.BedrockAgentClient;
import software.amazon.awssdk.services.bedrockagent.model.*;

@Service
public class KnowledgeBaseSyncHelper {

    private static final Logger logger = LoggerFactory.getLogger(KnowledgeBaseSyncHelper.class);

    private final BedrockAgentClient bedrockAgentClient;

    public KnowledgeBaseSyncHelper(BedrockAgentClient bedrockAgentClient) {
        this.bedrockAgentClient = bedrockAgentClient;
    }

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

