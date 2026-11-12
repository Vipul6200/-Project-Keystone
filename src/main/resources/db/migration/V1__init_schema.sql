-- User Table 
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL, password VARCHAR(255)
    NOT NULL,
    role VARCHAR(50) NOT NULL
);

-- Customer Table
CREATE TABLE customer (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20)
);

-- Sites Table 
CREATE TABLE site (
    id BIGSERIAL PRIMARY KEY,
    site_name VARCHAR(100) NOT NULL,
    location VARCHAR(255),
    customer_id BIGINT REFERENCES customer(id) ON DELETE CASCADE
);

-- Work Orders Table 
CREATE TABLE work_order (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    description TEXT,
    status VARCHAR(50) DEFAULT 'PENDING',
    site_id BIGINT REFERENCES site(id) ON DELETE CASCADE,
    assigned_to BIGINT REFERENCES users(id)
);
