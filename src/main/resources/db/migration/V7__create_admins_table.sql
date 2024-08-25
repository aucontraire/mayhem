CREATE TABLE admins (
    uuid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id BIGINT UNIQUE,
    created TIMESTAMP WITH TIME ZONE,
    updated TIMESTAMP WITH TIME ZONE,
    active BOOLEAN,
    joined DATE,
    last_activity DATE,
    username VARCHAR(255),
    user_number INTEGER,
    profile_url TEXT,
    profile_url_readable TEXT,
    age INTEGER,
    birth_date DATE,
    profile_details JSONB,
    email VARCHAR(255) NOT NULL UNIQUE,
    twitter TEXT,
    notes TEXT,

    country_id BIGINT,
    state_region_id BIGINT,  -- Updated to match the naming convention
    city_id BIGINT,

    CONSTRAINT fk_country
        FOREIGN KEY (country_id)
            REFERENCES country(id),

    CONSTRAINT fk_stateRegion
        FOREIGN KEY (state_region_id)  -- Updated to match the naming convention
            REFERENCES state_region(id),

    CONSTRAINT fk_city
        FOREIGN KEY (city_id)
            REFERENCES city(id)
);
