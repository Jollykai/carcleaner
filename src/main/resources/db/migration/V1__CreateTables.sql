CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price INT DEFAULT NULL
);

CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    product_id INT NOT NULL,
    date TIMESTAMP NOT NULL,
    customer_data VARCHAR(255) DEFAULT NULL,
    CONSTRAINT fk_product
        FOREIGN KEY (product_id)
            REFERENCES products(id)
);
