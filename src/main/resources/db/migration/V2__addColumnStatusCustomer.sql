ALTER TABLE customer
ADD COLUMN status text;

UPDATE customer
SET status = 'ACTIVE';