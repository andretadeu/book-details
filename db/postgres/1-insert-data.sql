INSERT INTO book (isbn, title, pages) VALUES ('978-1973391159', 'Memórias Póstumas de Brás Cubas', 223);
INSERT INTO book (isbn, title, pages) VALUES ('978-1501121968', 'The Sun Also Rises', 320);

INSERT INTO author (id, first_name, last_name) VALUES ('9a38ce59-e988-41cc-848f-1c0c2148b5d3', 'Machado', 'de Assis');
INSERT INTO author (id, first_name, last_name) VALUES ('a6f0c2dd-4598-40c0-98e2-500df737a167', 'Ernest', 'Hemingway');

INSERT INTO book_author (isbn, author_id) VALUES ('978-1973391159', '9a38ce59-e988-41cc-848f-1c0c2148b5d3');
INSERT INTO book_author (isbn, author_id) VALUES ('978-1501121968', 'a6f0c2dd-4598-40c0-98e2-500df737a167');