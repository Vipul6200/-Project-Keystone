CREATE TABLE If NOT EXISTS notifications (
    id BIGSERIAL PRIMARY KEY,
    work_order_id BIGINT NOT NULL,
    message VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_read BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (work_order_id) REFERENCES work_order(id)
);