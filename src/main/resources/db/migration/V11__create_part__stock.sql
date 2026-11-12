CREATE TABLE IF NOT EXISTS part_stock (
    id BIGSERIAL PRIMARY KEY,
    part_name VARCHAR(255) NOT NULL,
    stock_quantity INTEGER NOT NULL
);