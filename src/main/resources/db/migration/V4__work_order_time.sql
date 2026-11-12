CREATE TABLE work_order_time (
    id BIGSERIAL PRIMARY KEY,
    work_order_id BIGINT NOT NULL,
    minutes INTEGER NOT NULL,
    note VARCHAR(500),
    logged_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (work_order_id) REFERENCES work_order(id)
);