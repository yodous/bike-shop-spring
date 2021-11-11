-- INSERT INTO user_role(id, role) VALUES(1, 'ADMIN');
-- INSERT INTO user_role(id, role) VALUES(2, 'USER');

INSERT INTO USERS(ID, USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, EMAIL_ADDRESS, ACC_NUMBER, CITY, STREET,
    POSTAL_CODE, ROLE, CREATED_AT, MODIFIED_AT)
VALUES (1, 'seller', 'password', 'firstName0', 'lastName0', 'seller@email.com', 'account number 0', 'city0',
        'street0', '50-123', 'USER', NOW(), NOW());
INSERT INTO USERS(ID, USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, EMAIL_ADDRESS, ACC_NUMBER, CITY, STREET,
                  POSTAL_CODE, ROLE, CREATED_AT, MODIFIED_AT)
VALUES (2, 'seller1', 'password', 'firstName1Seller', 'sellerlastName0', 'seller1@email.com', 'account number 12',
        'city5', 'street6', '70-123', 'USER', NOW(), NOW());

INSERT INTO USERS(ID, USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, EMAIL_ADDRESS, ACC_NUMBER, CITY, STREET,
                  POSTAL_CODE, ROLE, CREATED_AT, MODIFIED_AT)
VALUES (3, 'consumer0', 'password', 'consumerName0', 'consumerlastName0', 'cons0@email.com', 'account number 1',
        'city1', 'street1', '11-123', 'USER', NOW(), NOW());
INSERT INTO USERS(ID, USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, EMAIL_ADDRESS, ACC_NUMBER, CITY, STREET,
                  POSTAL_CODE, ROLE, CREATED_AT, MODIFIED_AT)
VALUES (4, 'consumer1', 'password', 'consumer1Name', 'conslastName1', 'cons1@email.com', 'account number 2',
        'city2', 'street2', '22-123', 'USER', NOW(), NOW());

INSERT INTO product
VALUES (1, 1, 'product 0', 'descriptionnnnnnnnnn000', 'BEAUTY', 1.50, NOW(), NOW());
INSERT INTO product
VALUES (2, 1, 'product 1', 'descriptionnnnnnnnnn111', 'BEAUTY', 91.23, NOW(), NOW());
INSERT INTO product
VALUES (3, 1, 'product 2', 'descriptionnnnnnnnnn222', 'BEAUTY', 6.66, NOW(), NOW());
INSERT INTO product
VALUES (4, 1, 'product 3', 'descriptionnnnnnnnnn333', 'ENTERTAINMENT', 21.50, NOW(), NOW());
INSERT INTO product
VALUES (5, 1, 'product 4', 'descriptionnnnnnnnnn000', 'ENTERTAINMENT', 101.00, NOW(), NOW());
INSERT INTO product
VALUES (6, 1, 'product 5', 'descriptionnnnnnnnnn111', 'ELECTRONICS', 29923, NOW(), NOW());
INSERT INTO product
VALUES (7, 1, 'product 6', 'descriptionnnnnnnnnn222', 'FASHION', 999000, NOW(), NOW());
INSERT INTO product
VALUES (8, 1, 'product 7', 'descriptionnnnnnnnnn333', 'FASHION', 123.45, NOW(), NOW());
INSERT INTO product
VALUES (9, 1, 'product 8', 'descriptionnnnnnnnnn333', 'FASHION', 56.78, NOW(), NOW());
INSERT INTO product
VALUES (10, 1, 'product 9', 'descriptionnnnnnnnnn333', 'FASHION', 99.70, NOW(), NOW());

INSERT INTO product
VALUES (11, 1, 'product 10', 'descriptionnnnnnnnnn000', 'HEALTH', 1.50, NOW(), NOW());
INSERT INTO product
VALUES (12, 1, 'product 11', 'descriptionnnnnnnnnn111', 'HEALTH', 10.23, NOW(), NOW());
INSERT INTO product
VALUES (13, 1, 'product 12', 'descriptionnnnnnnnnn222', 'HEALTH', 66.66, NOW(), NOW());
INSERT INTO product
VALUES (14, 1, 'product 13', 'descriptionnnnnnnnnn333', 'HEALTH', 21.50, NOW(), NOW());
INSERT INTO product
VALUES (15, 1, 'product 14', 'descriptionnnnnnnnnn000', 'HOUSE', 15.50, NOW(), NOW());
INSERT INTO product
VALUES (16, 1, 'product 15', 'descriptionnnnnnnnnn111', 'HOUSE', 91.23, NOW(), NOW());
INSERT INTO product
VALUES (17, 1, 'product 16', 'descriptionnnnnnnnnn222', 'HOUSE', 6.66, NOW(), NOW());
INSERT INTO product
VALUES (18, 1, 'product 17', 'descriptionnnnnnnnnn333', 'SPORT', 211.50, NOW(), NOW());
INSERT INTO product
VALUES (19, 1, 'product 18', 'descriptionnnnnnnnnn333', 'SPORT', 321.50, NOW(), NOW());
INSERT INTO product
VALUES (20, 1, 'product 19', 'descriptionnnnnnnnnn333', 'SPORT', 721.50, NOW(), NOW());

INSERT INTO product
VALUES (21, 2, 'product 20', 'descriptionnnnnnnnnn000', 'MOTORIZATION', 1.50, NOW(), NOW());
INSERT INTO product
VALUES (22, 2, 'product 21', 'descriptionnnnnnnnnn111', 'MOTORIZATION', 1.23, NOW(), NOW());
INSERT INTO product
VALUES (23, 2, 'product 22', 'descriptionnnnnnnnnn222', 'MOTORIZATION', 766.66, NOW(), NOW());
INSERT INTO product
VALUES (24, 2, 'product 23', 'descriptionnnnnnnnnn333', 'MOTORIZATION', 21.50, NOW(), NOW());
INSERT INTO product
VALUES (25, 2, 'product 24', 'descriptionnnnnnnnnn000', 'SPORT', 561.50, NOW(), NOW());
INSERT INTO product
VALUES (26, 2, 'product 25', 'descriptionnnnnnnnnn111', 'SPORT', 51.23, NOW(), NOW());
INSERT INTO product
VALUES (27, 2, 'product 26', 'descriptionnnnnnnnnn222', 'SPORT', 6.66, NOW(), NOW());
INSERT INTO product
VALUES (28, 2, 'product 27', 'descriptionnnnnnnnnn333', 'SPORT', 121.50, NOW(), NOW());
INSERT INTO product
VALUES (29, 2, 'product 28', 'descriptionnnnnnnnnn333', 'FASHION', 2881.50, NOW(), NOW());
INSERT INTO product
VALUES (30, 2, 'product 29', 'descriptionnnnnnnnnn333', 'FASHION', 2171.50, NOW(), NOW());
