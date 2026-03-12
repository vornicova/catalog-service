CREATE TABLE cake_designs (
                              id SERIAL PRIMARY KEY,
                              code INTEGER NOT NULL UNIQUE,
                              name VARCHAR(255) NOT NULL,
                              description TEXT,
                              image_url VARCHAR(500),
                              design_category VARCHAR(50) NOT NULL,
                              decor_price NUMERIC(10, 2) NOT NULL,
                              is_active BOOLEAN NOT NULL DEFAULT TRUE
);