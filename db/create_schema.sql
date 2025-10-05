-- We are using PostgreSQL 18

-- CSV Header is below
-- bookId,title,author,rating,description,language,isbn,bookFormat,edition,pages,publisher,publishDate,firstPublishDate,likedPercent,price
-- Create the books table
CREATE TABLE IF NOT EXISTS books (
    book_id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    rating FLOAT,
    description TEXT,
    language VARCHAR(50),
    isbn VARCHAR(20) UNIQUE,
    book_format VARCHAR(50),
    edition VARCHAR(100),
    pages INT,
    publisher VARCHAR(255),
    publish_date DATE,
    first_publish_date DATE,
    liked_percent FLOAT,
    price DECIMAL(10, 2)
);
-- Create the authors table
CREATE TABLE IF NOT EXISTS authors (
    author_id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);
-- Create the books_authors table
CREATE TABLE IF NOT EXISTS books_authors (
    book_id INT REFERENCES books(book_id) ON DELETE CASCADE,
    author_id INT REFERENCES authors(author_id) ON DELETE CASCADE,
    PRIMARY KEY (book_id, author_id)
);