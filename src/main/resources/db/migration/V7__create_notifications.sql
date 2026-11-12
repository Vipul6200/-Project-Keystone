CREATE TABLE notifications (
    id BIGSERIAL PRIMARY KEY,
    work_order_id BIGINT NOT NULL,
    message VARCHAR(500) NOT NULL,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_read BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (work_order_id) REFERENCES work_order(id)
);