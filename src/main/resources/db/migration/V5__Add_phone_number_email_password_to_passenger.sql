ALTER TABLE PASSENGER
ADD COLUMN phone_number VARCHAR(255) NOT NULL,
ADD COLUMN email VARCHAR(255) NOT NULL,
ADD COLUMN password VARCHAR(255) NOT NULL;

ALTER TABLE PASSENGER
ALTER COLUMN name TYPE VARCHAR(255),
ALTER COLUMN name SET NOT NULL;
