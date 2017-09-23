package com.github.maly7.config;

import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.core.CoreContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@Configuration
public class SolrConfiguration {

    @Bean
    public EmbeddedSolrServer solrServer() throws FileNotFoundException {
        String solrHome = ResourceUtils.getURL("classpath:solr").getPath();
        CoreContainer container = CoreContainer.createAndLoad(new File(solrHome).toPath(), new File(solrHome + "/solr.xml").toPath());
        return new EmbeddedSolrServer(container, "books");
    }
}
