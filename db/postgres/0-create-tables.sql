CREATE TABLE book (
    isbn VARCHAR(14) NOT NULL,
    title VARCHAR(50) NOT NULL,
    pages INTEGER NOT NULL,
    PRIMARY KEY (isbn)
);

CREATE TABLE author (
    id UUID NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE book_author (
    isbn VARCHAR(14) NOT NULL,
    author_id UUID NOT NULL,
    PRIMARY KEY (isbn, author_id),
    CONSTRAINT fk_book_isbn FOREIGN KEY (isbn) REFERENCES book(isbn),
    CONSTRAINT fk_author_id FOREIGN KEY (author_id) REFERENCES author(id)
);