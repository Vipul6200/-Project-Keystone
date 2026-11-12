CREATE TABLE IF NOT EXISTS work_order_attachments (
    id BIGSERIAL PRIMARY KEY,
    work_order_id BIGINT NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_url VARCHAR(500) NOT NULL
);