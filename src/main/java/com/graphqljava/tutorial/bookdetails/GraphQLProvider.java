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
    public void init() throws IOException {
        StringBuilder bldr = new StringBuilder();
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("schema.graphqls");
        if (inputStream != null) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    bldr.append(line);
                }
            }
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
