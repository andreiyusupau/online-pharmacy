
CREATE TABLE public.credit_cards (
    id bigint NOT NULL,
    card_number character varying(19) NOT NULL,
    owner_name character varying(50) NOT NULL,
    valid_thru date NOT NULL,
    cvv integer NOT NULL,
    user_id bigint NOT NULL
);



CREATE SEQUENCE public.credit_cards_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.credit_cards_id_seq OWNED BY public.credit_cards.id;



CREATE TABLE public.order_positions (
    id bigint NOT NULL,
    quantity integer NOT NULL,
    product_id bigint NOT NULL,
    order_id bigint NOT NULL
);



CREATE SEQUENCE public.order_positions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



ALTER SEQUENCE public.order_positions_id_seq OWNED BY public.order_positions.id;


--
-- TOC entry 207 (class 1259 OID 16422)
-- Name: orders; Type: TABLE; Schema: public; Owner: pharmadmin
--

CREATE TABLE public.orders (
    id bigint NOT NULL,
    date timestamp with time zone NOT NULL,
    user_id bigint NOT NULL
);



CREATE SEQUENCE public.orders_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.orders_id_seq OWNED BY public.orders.id;


--
-- TOC entry 211 (class 1259 OID 16446)
-- Name: procurement_positions; Type: TABLE; Schema: public; Owner: pharmadmin
--

CREATE TABLE public.procurement_positions (
    id bigint NOT NULL,
    quantity integer NOT NULL,
    product_id bigint NOT NULL,
    procurement_id bigint NOT NULL
);


CREATE SEQUENCE public.procurement_positions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.procurement_positions_id_seq OWNED BY public.procurement_positions.id;


CREATE TABLE public.procurements (
    id bigint NOT NULL,
    date timestamp with time zone NOT NULL,
    user_id bigint NOT NULL
);



CREATE SEQUENCE public.procurements_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.procurements_id_seq OWNED BY public.procurements.id;


CREATE TABLE public.product_categories (
    id bigint NOT NULL,
    name character varying(50) NOT NULL,
    description character varying(500) NOT NULL
);


CREATE SEQUENCE public.product_categories_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



ALTER SEQUENCE public.product_categories_id_seq OWNED BY public.product_categories.id;

CREATE TABLE public.products (
    id bigint NOT NULL,
    name character varying(100) NOT NULL,
    price bigint NOT NULL,
    recipe_required boolean NOT NULL,
    product_category_id bigint NOT NULL
);


CREATE SEQUENCE public.products_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



ALTER SEQUENCE public.products_id_seq OWNED BY public.products.id;



CREATE TABLE public.recipes (
    id bigint NOT NULL,
    description character varying(255) NOT NULL,
    quantity integer NOT NULL,
    valid_thru timestamp with time zone NOT NULL,
    product_id bigint NOT NULL,
    order_position_id bigint NOT NULL
);


CREATE SEQUENCE public.recipes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.recipes_id_seq OWNED BY public.recipes.id;


CREATE TABLE public.reserve_positions (
    id bigint NOT NULL,
    quantity integer NOT NULL,
    product_id bigint NOT NULL,
    order_id bigint NOT NULL
);


CREATE TABLE public.roles (
    id bigint NOT NULL,
    name character varying(50) NOT NULL
);


CREATE SEQUENCE public.roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;


CREATE TABLE public.stock_positions (
    id bigint NOT NULL,
    quantity integer NOT NULL,
    product_id bigint NOT NULL
);


CREATE SEQUENCE public.stock_positions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.stock_positions_id_seq OWNED BY public.stock_positions.id;


CREATE TABLE public.users (
    id bigint NOT NULL,
    first_name character varying(50) NOT NULL,
    middle_name character varying(50),
    last_name character varying(50) NOT NULL,
    date_of_birth date NOT NULL,
    email character varying(50) NOT NULL,
    password character varying(255),
    role_id bigint NOT NULL
);


CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


ALTER TABLE ONLY public.credit_cards ALTER COLUMN id SET DEFAULT nextval('public.credit_cards_id_seq'::regclass);


ALTER TABLE ONLY public.order_positions ALTER COLUMN id SET DEFAULT nextval('public.order_positions_id_seq'::regclass);


ALTER TABLE ONLY public.orders ALTER COLUMN id SET DEFAULT nextval('public.orders_id_seq'::regclass);


ALTER TABLE ONLY public.procurement_positions ALTER COLUMN id SET DEFAULT nextval('public.procurement_positions_id_seq'::regclass);


ALTER TABLE ONLY public.procurements ALTER COLUMN id SET DEFAULT nextval('public.procurements_id_seq'::regclass);

ALTER TABLE ONLY public.product_categories ALTER COLUMN id SET DEFAULT nextval('public.product_categories_id_seq'::regclass);

ALTER TABLE ONLY public.products ALTER COLUMN id SET DEFAULT nextval('public.products_id_seq'::regclass);

ALTER TABLE ONLY public.recipes ALTER COLUMN id SET DEFAULT nextval('public.recipes_id_seq'::regclass);

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);

ALTER TABLE ONLY public.stock_positions ALTER COLUMN id SET DEFAULT nextval('public.stock_positions_id_seq'::regclass);

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);

ALTER TABLE ONLY public.credit_cards
    ADD CONSTRAINT credit_cards_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.order_positions
    ADD CONSTRAINT order_positions_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.procurement_positions
    ADD CONSTRAINT procurement_positions_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.procurements
    ADD CONSTRAINT procurements_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.product_categories
    ADD CONSTRAINT product_categories_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.recipes
    ADD CONSTRAINT recipes_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.reserve_positions
    ADD CONSTRAINT reserve_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.stock_positions
    ADD CONSTRAINT stock_positions_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);

CREATE INDEX "fki_credit_cards|_user_id_fkey" ON public.credit_cards USING btree (user_id);

CREATE INDEX fki_order_positions_order_id_fkey ON public.order_positions USING btree (order_id);

CREATE INDEX fki_order_positions_product_id_fkey ON public.order_positions USING btree (product_id);

CREATE INDEX fki_orders_user_id_fkey ON public.orders USING btree (user_id);

CREATE INDEX fki_procurement_positions_procurement_id_fkey ON public.procurement_positions USING btree (procurement_id);

CREATE INDEX fki_procurement_positions_product_id_fkey ON public.procurement_positions USING btree (product_id);

CREATE INDEX fki_procurements_user_id_fkey ON public.procurements USING btree (user_id);

CREATE INDEX fki_product_id_fkey ON public.stock_positions USING btree (product_id);

CREATE INDEX fki_products_product_category_id_fkey ON public.products USING btree (product_category_id);

CREATE INDEX fki_recipes_order_position_id_fkey ON public.recipes USING btree (order_position_id);

CREATE INDEX fki_recipes_product_id_fkey ON public.recipes USING btree (product_id);

CREATE INDEX fki_reserve_positions_order_id_fkey ON public.reserve_positions USING btree (order_id);

CREATE INDEX fki_reserve_positions_product_id_fkey ON public.reserve_positions USING btree (product_id);

CREATE INDEX fki_rore_id_fkey ON public.users USING btree (role_id);

ALTER TABLE ONLY public.credit_cards
    ADD CONSTRAINT credit_cards_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);

ALTER TABLE ONLY public.order_positions
    ADD CONSTRAINT order_positions_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id);

ALTER TABLE ONLY public.order_positions
    ADD CONSTRAINT order_positions_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id);

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);

ALTER TABLE ONLY public.procurement_positions
    ADD CONSTRAINT procurement_positions_procurement_id_fkey FOREIGN KEY (procurement_id) REFERENCES public.procurements(id);

ALTER TABLE ONLY public.procurement_positions
    ADD CONSTRAINT procurement_positions_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id);

ALTER TABLE ONLY public.procurements
    ADD CONSTRAINT procurements_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_product_category_id_fkey FOREIGN KEY (product_category_id) REFERENCES public.product_categories(id);

ALTER TABLE ONLY public.recipes
    ADD CONSTRAINT recipes_order_position_id_fkey FOREIGN KEY (order_position_id) REFERENCES public.order_positions(id);

ALTER TABLE ONLY public.recipes
    ADD CONSTRAINT recipes_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id);

ALTER TABLE ONLY public.reserve_positions
    ADD CONSTRAINT reserve_positions_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id);

ALTER TABLE ONLY public.reserve_positions
    ADD CONSTRAINT reserve_positions_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id);

ALTER TABLE ONLY public.stock_positions
    ADD CONSTRAINT stock_positions_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id);

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_role_id_fkey FOREIGN KEY (role_id) REFERENCES public.roles(id);