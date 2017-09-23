package com.github.maly7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.github.maly7.data.jpa")
@EnableSolrRepositories(basePackages = "com.github.maly7.data.solr")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
