package com.graphqljava.tutorial.bookdetails;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Component
@CrossOrigin(origins = "localhost:8080")
public class GraphQLProvider {

    private GraphQL graphQL;

    @Autowired
    private GraphQLDataFetchers graphQLDataFetchers;

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    @PostConstruct
    public void init() throws IOException, URISyntaxException {
        URL url = this.getClass().getClassLoader().getResource("schema.graphqls");
        if (url != null) {
            StringBuilder bldr = new StringBuilder();
            Files.readAllLines(new File(url.toURI()).toPath(), StandardCharsets.UTF_8)
                    .forEach(bldr::append);
            GraphQLSchema graphQLSchema = buildSchema(bldr.toString());
            this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
        }
    }

    private GraphQLSchema buildSchema(String sdl) {
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        return schemaGenerator.makeExecutableSchema(typeRegistry, buildWiring());
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                        .dataFetcher("bookById", graphQLDataFetchers.getBookByIdDataFetcher()))
                .type(newTypeWiring("Book")
                        .dataFetcher("author", graphQLDataFetchers.getAuthorDataFetcher()))
                .type(newTypeWiring("Mutation")
                        .dataFetcher("insert", graphQLDataFetchers.insert()))
                .build();
    }
}
