CREATE TABLE booking (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    review_id BIGINT NULL,
    booking_status VARCHAR(50) CHECK (booking_status IN ('SCHEDULED','CANCELLED','CAB_ARRIVED','ASSIGNING_DRIVER','IN_RIDE','COMPLETED')),
    start_time TIMESTAMP NULL,
    end_time TIMESTAMP NULL,
    total_distance BIGINT NULL,
    driver_id BIGINT NULL,
    passenger_id BIGINT NULL
);

CREATE TABLE booking_review (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    content VARCHAR(255) NOT NULL,
    rating DOUBLE PRECISION NULL
);

CREATE TABLE driver (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    name VARCHAR(255) NULL,
    license_number VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE passenger (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    name VARCHAR(255) NULL
);

CREATE TABLE passenger_review (
    id BIGINT PRIMARY KEY,
    passenger_review_content VARCHAR(255) NOT NULL,
    passenger_rating VARCHAR(255) NOT NULL,
    CONSTRAINT fk_passengerreview_on_id FOREIGN KEY (id) REFERENCES booking_review (id)
);

ALTER TABLE booking
    ADD CONSTRAINT fk_booking_on_driver FOREIGN KEY (driver_id) REFERENCES driver (id);

ALTER TABLE booking
    ADD CONSTRAINT fk_booking_on_passenger FOREIGN KEY (passenger_id) REFERENCES passenger (id);

ALTER TABLE booking
    ADD CONSTRAINT fk_booking_on_review FOREIGN KEY (review_id) REFERENCES booking_review (id);
