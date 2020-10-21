CREATE TABLE credit_cards
(
    id          bigint                NOT NULL,
    card_number character varying(19) NOT NULL,
    owner_name  character varying(50) NOT NULL,
    valid_thru  date                  NOT NULL,
    cvv         integer               NOT NULL,
    user_id     bigint                NOT NULL
);

CREATE TABLE order_positions
(
    id         bigint  NOT NULL,
    quantity   integer NOT NULL,
    product_id bigint  NOT NULL,
    order_id   bigint  NOT NULL
);

CREATE TABLE orders
(
    id      bigint                   NOT NULL,
    date    timestamp with time zone NOT NULL,
    user_id bigint                   NOT NULL
);

CREATE TABLE procurement_positions
(
    id             bigint  NOT NULL,
    quantity       integer NOT NULL,
    product_id     bigint  NOT NULL,
    procurement_id bigint  NOT NULL
);

CREATE TABLE procurements
(
    id      bigint                   NOT NULL,
    date    timestamp with time zone NOT NULL,
    user_id bigint                   NOT NULL
);

CREATE TABLE product_categories
(
    id          bigint                 NOT NULL,
    name        character varying(50)  NOT NULL,
    description character varying(500) NOT NULL
);

CREATE TABLE products
(
    id                  bigint                 NOT NULL,
    name                character varying(100) NOT NULL,
    price               bigint                 NOT NULL,
    recipe_required     boolean                NOT NULL,
    product_category_id bigint                 NOT NULL
);

CREATE TABLE recipes
(
    id                bigint                   NOT NULL,
    description       character varying(255)   NOT NULL,
    quantity          integer                  NOT NULL,
    valid_thru        timestamp with time zone NOT NULL,
    product_id        bigint                   NOT NULL,
    order_position_id bigint                   NOT NULL
);

CREATE TABLE reserve_positions
(
    id         bigint  NOT NULL,
    quantity   integer NOT NULL,
    product_id bigint  NOT NULL,
    order_id   bigint  NOT NULL
);

CREATE TABLE roles
(
    id   bigint                NOT NULL,
    name character varying(50) NOT NULL
);

CREATE TABLE stock_positions
(
    id         bigint  NOT NULL,
    quantity   integer NOT NULL,
    product_id bigint  NOT NULL
);

CREATE TABLE users
(
    id            bigint                NOT NULL,
    first_name    character varying(50) NOT NULL,
    middle_name   character varying(50),
    last_name     character varying(50) NOT NULL,
    date_of_birth date                  NOT NULL,
    email         character varying(50) NOT NULL,
    password      character varying(255),
    role_id       bigint                NOT NULL
);
