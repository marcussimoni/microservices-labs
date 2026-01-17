-- Books table
CREATE TABLE IF NOT EXISTS bookstore.books (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    cover_image VARCHAR(512),
    stock INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Purchases table
CREATE TABLE IF NOT EXISTS bookstore.purchases (
    id BIGSERIAL PRIMARY KEY,
    book_id BIGINT NOT NULL,
    purchase_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    customer_id VARCHAR(255) NOT NULL,
    quantity INTEGER NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (book_id) REFERENCES bookstore.books(id) ON DELETE CASCADE
);

-- Purchases table
CREATE TABLE IF NOT EXISTS bookstore.purchase_outbox (
    id BIGSERIAL PRIMARY KEY,
    purchase_id INTEGER NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    book VARCHAR(100),
    public_identifier VARCHAR(100)
);

-- used by debezium for replication
 CREATE PUBLICATION dbz_publication FOR TABLE bookstore.purchase_outbox;