package com.graphqljava.tutorial.bookdetails;

import com.graphqljava.tutorial.bookdetails.model.Author;
import com.graphqljava.tutorial.bookdetails.model.Book;
import com.graphqljava.tutorial.bookdetails.repository.AuthorRepository;
import com.graphqljava.tutorial.bookdetails.repository.BookRepository;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class GraphQLDataFetchers {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public GraphQLDataFetchers(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public DataFetcher<?> getBookByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            String bookId = dataFetchingEnvironment.getArgument("isbn");
            return bookRepository.findById(bookId);
        };
    }

    public DataFetcher<?> getAuthorDataFetcher() {
        return dataFetchingEnvironment -> {
            Map<String, String> book = dataFetchingEnvironment.getSource();
            String authorId = book.get("authorId");
            return authorRepository.findById(UUID.fromString(authorId));
        };
    }

    public DataFetcher<?> insert() {
        return dataFetchingEnvironment -> {
            Book book = new Book();
            book.setIsbn(dataFetchingEnvironment.getArgument("isbn"));
            book.setTitle(dataFetchingEnvironment.getArgument("title"));
            book.setPageCount(dataFetchingEnvironment.getArgument("pageCount"));
            bookRepository.saveAndFlush(book);
            List<Author> authors = new ArrayList<>();
            Object authorsInput = dataFetchingEnvironment.getArgument("authors");
            return true;
        };
    }

    public DataFetcher<?> delete() {
        return dataFetchingEnvironment -> {
            Book book = new Book();
            book.setIsbn(dataFetchingEnvironment.getArgument("isbn"));
            book.setTitle(dataFetchingEnvironment.getArgument("title"));
            book.setPageCount(Integer.parseInt(dataFetchingEnvironment.getArgument("pageCount")));
            bookRepository.delete(book);
            return true;
        };
    }
}
