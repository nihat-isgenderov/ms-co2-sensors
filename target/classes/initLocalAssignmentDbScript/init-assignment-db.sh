#!/bin/bash
set -e
export PGPASSWORD=$POSTGRES_PASSWORD;

# create the database
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE DATABASE $ASSIGNMENT_DB;
EOSQL

# create the extensions
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$ASSIGNMENT_DB" <<-EOSQL
    CREATE EXTENSION pg_trgm;
EOSQL

# create the application user
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$ASSIGNMENT_DB" <<-EOSQL
    CREATE USER $ASSIGNMENT_DB_USER WITH PASSWORD '$ASSIGNMENT_DB_PASS';
EOSQL

# make each users the owner of his schemas
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$ASSIGNMENT_DB" <<-EOSQL
    ALTER SCHEMA public OWNER TO $ASSIGNMENT_DB_USER;
EOSQL

#Fix for message in the logs "FATAL:  database "root" does not exist"
psql -U root -d $ASSIGNMENT_DB

echo "Initialization complete."