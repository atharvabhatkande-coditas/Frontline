ALTER TABLE ticket_assignment
    DROP COLUMN priority;


ALTER TABLE history
    ADD customer_id BIGINT;

ALTER TABLE history
    ADD CONSTRAINT FK_HISTORY_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (id);