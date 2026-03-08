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

-- Orders table
CREATE TABLE IF NOT EXISTS bookstore.orders (
    id BIGSERIAL PRIMARY KEY,
    book_id BIGINT NOT NULL,
    order_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    customer_id VARCHAR(255) NOT NULL,
    quantity INTEGER NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    status VARCHAR(200),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (book_id) REFERENCES bookstore.books(id) ON DELETE CASCADE
);

-- Orders table
CREATE TABLE IF NOT EXISTS bookstore.order_outbox (
    id BIGSERIAL PRIMARY KEY,
    order_id INTEGER NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    book VARCHAR(100),
    customer_id VARCHAR(100)
);

-- used by debezium for replication
CREATE PUBLICATION dbz_publication FOR TABLE bookstore.order_outbox;