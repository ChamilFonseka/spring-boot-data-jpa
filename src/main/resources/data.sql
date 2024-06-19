-- Insert sample data into Address table
INSERT INTO address (street, city, state, zip_code) VALUES ('123 Main St', 'Anytown', 'Anystate', '12345');
INSERT INTO address (street, city, state, zip_code) VALUES ('456 Elm St', 'Othertown', 'Otherstate', '67890');

-- Insert sample data into Customer table
INSERT INTO customer (name, address_id) VALUES ('John Doe', 1);
INSERT INTO customer (name, address_id) VALUES ('Jane Smith', 2);

-- Insert sample data into Product table
INSERT INTO product (name, price) VALUES ('Product A', 9.99);
INSERT INTO product (name, price) VALUES ('Product B', 19.99);
INSERT INTO product (name, price) VALUES ('Product C', 29.99);

-- Insert sample data into Order table
INSERT INTO orders (order_number, customer_id) VALUES ('ORD123', 1);
INSERT INTO orders (order_number, customer_id) VALUES ('ORD124', 2);

-- Insert sample data into Order_Product join table
INSERT INTO order_product (order_id, product_id) VALUES (1, 1);
INSERT INTO order_product (order_id, product_id) VALUES (1, 2);
INSERT INTO order_product (order_id, product_id) VALUES (2, 2);
INSERT INTO order_product (order_id, product_id) VALUES (2, 3);
