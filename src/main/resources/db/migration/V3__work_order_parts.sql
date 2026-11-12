CREATE TABLE work_order_parts (
    id BIGSERIAL PRIMARY KEY,
    work_order_id BIGINT NOT NULL,
    part_name VARCHAR(100) NOT NULL,
    quantity INTEGER NOT NULL,
    FOREIGN KEY (work_order_id) REFERENCES work_order(id)
);