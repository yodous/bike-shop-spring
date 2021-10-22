CREATE TABLE user
(
    id            INT(10) NOT NULL AUTO_INCREMENT,
    username      VARCHAR(30) UNIQUE,
    password      VARCHAR(100) UNIQUE,
    first_name    VARCHAR(50) NOT NULL,
    last_name     VARCHAR(50) NOT NULL,
    email_address VARCHAR(50) UNIQUE,
    city          VARCHAR(30) NOT NULL,
    street        VARCHAR(50) NOT NULL,
    postal_code   VARCHAR(6)  NOT NULL,
    created_at    TIMESTAMP,
    modified_at   TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE shopping_session
(
    id          INT(10) NOT NULL AUTO_INCREMENT,
    user_id     INT(10) DEFAULT NULL,
    total_price DECIMAL(10) NOT NULL DEFAULT '0.00',
    created_at  TIMESTAMP   NOT NULL,
    modified_at TIMESTAMP,
    UNIQUE KEY session_index (id,user_id) USING BTREE,
    PRIMARY KEY (id),
    CONSTRAINT fk_shopping_user
        FOREIGN KEY (user_id)
            REFERENCES user (id)
            ON DELETE SET NULL
            ON UPDATE SET NULL
);

CREATE TABLE cart_item
(
    id          INT(10) NOT NULL AUTO_INCREMENT,
    session_id  INT NOT NULL,
    product_id  INT NOT NULL,
    quantity    INT DEFAULT 0,
    created_at  TIMESTAMP,
    modified_at TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_session_id
        FOREIGN KEY (session_id)
            REFERENCES shopping_session (id)
            ON DELETE SET NULL
            ON UPDATE SET NULL,
    CONSTRAINT fk_product_id
        FOREIGN KEY (product_id)
            REFERENCES product (id)
            ON DELETE SET NULL
            ON UPDATE SET NULL
);


CREATE TABLE product
(
    id          INT(10) NOT NULL AUTO_INCREMENT,
    name        VARCHAR(100) NOT NULL,
    description TEXT         NOT NULL,
    category    VARCHAR(50)  NOT NULL,
    price       DECIMAL(6)   NOT NULL,
    created_at  TIMESTAMP    NOT NULL,
    modified_at TIMESTAMP,
    UNIQUE KEY prod_index (id),
    PRIMARY KEY (id),
);

CREATE TABLE order_items
(
    id          INT(10) NOT NULL AUTO_INCREMENT,
    order_id    INT NOT NULL,
    product_id  INT NOT NULL,
    created_at  TIMESTAMP,
    modified_at TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_order_id
        FOREIGN KEY (order_id)
            REFERENCES order_details (id)
            ON DELETE SET NULL
            ON UPDATE SET NULL,
    CONSTRAINT fk_product_id
        FOREIGN KEY (product_id)
            REFERENCES product (id)
            ON DELETE SET NULL
            ON UPDATE SET NULL
);

CREATE TABLE order_details
(
    id          INT(10) NOT NULL AUTO_INCREMENT,
    user_id     INT(10),
    total       DECIMAL(10) NOT NULL,
    payment_id  INT(20) NOT NULL,
    created_at  TIMESTAMP   NOT NULL,
    modified_at TIMESTAMP,
    UNIQUE KEY order_index (id) USING BTREE,
    UNIQUE KEY customer_order_index (id,user_id) USING BTREE,
    PRIMARY KEY (id),
    CONSTRAINT fk_shopping_user_order
        FOREIGN KEY (user_id)
            REFERENCES user (id)
            ON DELETE SET NULL
            ON UPDATE SET NULL,
    CONSTRAINT fk_order_payment
        FOREIGN KEY (payment_id)
            REFERENCES payment_details (id)
            ON DELETE SET NULL
            ON UPDATE SET NULL
);

CREATE TABLE payment_details
(
    id          INT(10) NOT NULL AUTO_INCREMENT,
    order_id    INT         NOT NULL,
    amount      INT DEFAULT 0,
    provider    VARCHAR(50) NOT NULL,
    status      VARCHAR(50) NOT NULL,
    created_at  TIMESTAMP   NOT NULL,
    modified_at TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_order_id
        FOREIGN KEY (order_id)
            REFERENCES order_details (id)
            ON DELETE SET NULL
            ON UPDATE SET NULL
);