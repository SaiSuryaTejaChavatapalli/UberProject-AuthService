---- Add a unique constraint to the booking_id column
--ALTER TABLE booking_review
--ADD CONSTRAINT uc_booking_review_booking UNIQUE (booking_id);

-- Modify the booking_id column to set it as NOT NULL
ALTER TABLE booking_review
ALTER COLUMN booking_id SET NOT NULL;
