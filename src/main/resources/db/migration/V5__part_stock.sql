CREATE TABLE part_stock (
    id BIGSERIAL PRIMARY KEY,
    part_name VARCHAR(100) NOT NULL UNIQUE,
    stock_quantity INTEGER NOT NULL
);