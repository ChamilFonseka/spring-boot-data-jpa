-- Insert sample data into Address table
INSERT INTO demo.addresses (street, city, state, zip_code) VALUES ('123 Main St', 'Anytown', 'Anystate', '12345');
INSERT INTO demo.addresses (street, city, state, zip_code) VALUES ('456 Elm St', 'Othertown', 'Otherstate', '67890');

-- Insert sample data into Customer table
INSERT INTO demo.customers (name, address_id) VALUES ('John Doe', 1);
INSERT INTO demo.customers (name, address_id) VALUES ('Jane Smith', 2);

-- Insert sample data into Product table
INSERT INTO demo.products (name, price) VALUES ('Product A', 9.99);
INSERT INTO demo.products (name, price) VALUES ('Product B', 19.99);
INSERT INTO demo.products (name, price) VALUES ('Product C', 29.99);

-- Insert sample data into Order table
INSERT INTO demo.orders (order_number, order_created_time, customer_id) VALUES ('ORD123', '2024-06-20 19:52:56.298451', 1);
INSERT INTO demo.orders (order_number, order_created_time, customer_id) VALUES ('ORD124', '2024-06-22 09:12:35.125259', 2);

-- Insert sample data into Order_Product join table
INSERT INTO demo.orders_products (order_id, product_id) VALUES (1, 1);
INSERT INTO demo.orders_products (order_id, product_id) VALUES (1, 2);
INSERT INTO demo.orders_products (order_id, product_id) VALUES (2, 2);
INSERT INTO demo.orders_products (order_id, product_id) VALUES (2, 3);
