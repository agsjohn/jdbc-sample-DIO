ALTER TABLE accesses
    ADD COLUMN read_data boolean DEFAULT false,
    ADD COLUMN create_data boolean DEFAULT false,
    ADD COLUMN delete_data boolean DEFAULT false,
    ADD COLUMN update_data boolean DEFAULT false;