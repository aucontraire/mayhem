CREATE TABLE state_region (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255),
  country_id BIGINT,
  FOREIGN KEY (country_id) REFERENCES country(id)
);