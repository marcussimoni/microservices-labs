### boostore service

Useful commands to troubleshooting Kafka connect + Debezium read changes and create evetns

````
--if does not exists in postgres
CREATE PUBLICATION dbz_publication FOR TABLE bookstore.purchase_outbox;

--must be logical
SHOW wal_level;

--must be greater than number_of_connectors
SHOW max_replication_slots;
SHOW max_wal_senders;

Verify the outbox table exists and structure is correct
SELECT table_schema, table_name
FROM information_schema.tables
WHERE table_schema = 'bookstore'
  AND table_name = 'purchase_outbox';
````
### Verify the table is part of a publication
````
SELECT pubname, puballtables
FROM pg_publication;
````

### Check which tables are published
````
SELECT pubname, schemaname, tablename
FROM pg_publication_tables
WHERE schemaname = 'bookstore';

````

### Verify replication slots (CDC health indicator)
````
SELECT slot_name, plugin, active, restart_lsn
FROM pg_replication_slots;
````

### Watch LSN move
````
SELECT restart_lsn
FROM pg_replication_slots
WHERE slot_name = 'bookstore_purchases_slot';
````


### Check debezium connections