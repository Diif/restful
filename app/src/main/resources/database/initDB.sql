CREATE TABLE IF NOT EXISTS shop_unit
(
    id uuid,
    name text,
    parent_id uuid,
    type varchar(8),
    price integer,
    update_date timestamptz
);