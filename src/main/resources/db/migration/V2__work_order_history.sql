CREATE TABLE work_order_history (
    id BIGSERIAL PRIMARY KEY,
    work_order_id BIGINT NOT NULL,
    old_status VARCHAR(50),
    new_status VARCHAR(50),
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (work_order_id) REFERENCES work_order(id)
);