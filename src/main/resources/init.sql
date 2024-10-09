CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE purchase (
    id SERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE purchase_product (
    purchase_id BIGINT REFERENCES purchase(id),
    product_id BIGINT REFERENCES product(id),
    PRIMARY KEY (purchase_id, product_id)
);
