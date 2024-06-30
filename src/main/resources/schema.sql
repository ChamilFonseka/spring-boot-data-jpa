-- Create the schema if it does not exist
CREATE SCHEMA IF NOT EXISTS demo;

-- Drop tables if they exist in the demo schema
DROP TABLE IF EXISTS demo.orders_products;
DROP TABLE IF EXISTS demo.orders;
DROP TABLE IF EXISTS demo.products;
DROP TABLE IF EXISTS demo.addresses;
DROP TABLE IF EXISTS demo.customers;

-- Create the Customer table in the demo schema
CREATE TABLE demo.customers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Create the Address table in the demo schema
CREATE TABLE demo.addresses (
    id SERIAL PRIMARY KEY,
    street VARCHAR(255),
    city VARCHAR(255),
    state VARCHAR(255),
    zip_code VARCHAR(20),
    customer_id INT,
    FOREIGN KEY (customer_id) REFERENCES demo.customers(id)
);

-- Create the Order table in the demo schema
CREATE TABLE demo.orders (
    id SERIAL PRIMARY KEY,
    order_number VARCHAR(255) NOT NULL,
    order_created_time TIMESTAMP NOT NULL,
    customer_id INT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES demo.customers(id)
);

-- Create the Product table in the demo schema
CREATE TABLE demo.products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price NUMERIC(10, 2) NOT NULL
);

-- Create the Order_Product join table in the demo schema
CREATE TABLE demo.orders_products (
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES demo.orders(id),
    FOREIGN KEY (product_id) REFERENCES demo.products(id)
);
