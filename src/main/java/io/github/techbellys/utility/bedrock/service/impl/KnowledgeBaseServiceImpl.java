package io.github.techbellys.utility.bedrock.service.impl;

import io.github.techbellys.utility.bedrock.knowledgebase.KnowledgeBase;
import io.github.techbellys.utility.bedrock.service.KnowledgeBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KnowledgeBaseServiceImpl implements KnowledgeBaseService {

    private final KnowledgeBase knowledgeBase;

    @Autowired
    public KnowledgeBaseServiceImpl(KnowledgeBase knowledgeBase) {
        this.knowledgeBase = knowledgeBase;
    }

    @Override
    public String processQuery(String modelId, String knowledgeBaseId, String query) {
        return knowledgeBase.process(modelId, knowledgeBaseId, query);
    }
}
