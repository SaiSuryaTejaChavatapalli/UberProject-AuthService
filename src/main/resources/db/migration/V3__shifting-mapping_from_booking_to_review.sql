-- Drop the foreign key constraint
ALTER TABLE booking
DROP CONSTRAINT FK_BOOKING_ON_REVIEW;

-- Add a new column to booking_review
ALTER TABLE booking_review
ADD COLUMN booking_id BIGINT;

-- Add the foreign key constraint
ALTER TABLE booking_review
ADD CONSTRAINT FK_BOOKING_REVIEW_ON_BOOKING FOREIGN KEY (booking_id) REFERENCES booking (id);

-- Drop the column from booking
ALTER TABLE booking
DROP COLUMN review_id;
