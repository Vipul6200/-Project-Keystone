CREATE TABLE technician_locations (
    id BIGSERIAL PRIMARY KEY,
    technician_id BIGINT NOT NULL UNIQUE REFERENCES users(id),
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    updated_at TIMESTAMP
);