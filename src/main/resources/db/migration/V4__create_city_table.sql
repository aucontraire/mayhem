CREATE TABLE city (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    state_region_id BIGINT,
    FOREIGN KEY (state_region_id) REFERENCES state_region(id)
);