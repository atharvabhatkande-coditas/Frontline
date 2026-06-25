ALTER TABLE tickets
    ADD COLUMN ticket_number VARCHAR(100);

ALTER TABLE tickets
    ADD COLUMN priority VARCHAR(50);

ALTER TABLE tickets
    ADD COLUMN resolved_at TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE tickets
    ADD COLUMN resolution_comment VARCHAR(1000);

ALTER TABLE tickets
    ADD CONSTRAINT uk_ticket_number UNIQUE (ticket_number);

ALTER TABLE tickets
ALTER COLUMN description TYPE TEXT;

ALTER TABLE history
ALTER COLUMN message TYPE TEXT;

ALTER TABLE tickets
    RENAME TO ticket;

