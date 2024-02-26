#!/bin/bash
set -e
export PGPASSWORD=$POSTGRES_PASSWORD;

# create the database
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE DATABASE $APP_DB;
EOSQL

# create the schemas
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$APP_DB" <<-EOSQL
    CREATE SCHEMA $APP_DB_schema;
    CREATE EXTENSION pg_trgm WITH SCHEMA $APP_DB_schema;
    CREATE EXTENSION tablefunc WITH SCHEMA $APP_DB_schema;
EOSQL

# create the users and set their schema as their default schema
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$APP_DB" <<-EOSQL
    CREATE USER $APP_DB_USER WITH PASSWORD '$APP_DB_PASS';
    ALTER USER $APP_DB_USER SET search_path TO $APP_DB_schema;
EOSQL

# make each users the owner of his schemas
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$APP_DB" <<-EOSQL
    ALTER SCHEMA $APP_DB_schema OWNER TO $APP_DB_USER;
EOSQL

#Fix for message in the logs "FATAL:  database "root" does not exist"
psql -U root -d $APP_DB

echo "Initialization complete."