-- PS-13 create currencies table
CREATE TABLE IF NOT EXISTS currencies
(
    id   UUID PRIMARY KEY NOT NULL,
    name VARCHAR(50)      NOT NULL,
    code VARCHAR(3)       NOT NULL
);

-- PS-13 create table users
CREATE TABLE IF NOT EXISTS users
(
    id          UUID PRIMARY KEY NOT NULL,
    owner_id    UUID,
    currency_id UUID             NOT NULL,
    email       VARCHAR(80)      NOT NULL,
    phone       VARCHAR(20)      NOT NULL,
    password    VARCHAR(80)      NOT NULL,
    timezone    INT              NOT NULL,
    first_name  VARCHAR(50)      NOT NULL,
    last_name   VARCHAR(50)      NOT NULL,
    country     VARCHAR(30)      NOT NULL,
    language    VARCHAR(20)      NOT NULL,
    role        VARCHAR(20)      NOT NULL,
    created_at  TIMESTAMP        NOT NULL,
    updated_at  TIMESTAMP        NOT NULL,
    CONSTRAINT fk_users_owner FOREIGN KEY (owner_id) REFERENCES users (id),
    CONSTRAINT fk_users_currency FOREIGN KEY (currency_id) REFERENCES currencies (id)
);

-- PS-13 create apartment_categories table
CREATE TABLE IF NOT EXISTS apartment_categories
(
    id           UUID PRIMARY KEY NOT NULL,
    owner_id     UUID,
    name         VARCHAR(50)      NOT NULL,
    abbreviation VARCHAR(50)      NOT NULL,
    type         VARCHAR(30)      NOT NULL,
    sleep_place  DECIMAL(10)      NOT NULL,
    created_at   TIMESTAMP        NOT NULL,
    updated_at   TIMESTAMP        NOT NULL,
    CONSTRAINT fk_apartment_categories_owner FOREIGN KEY (owner_id) REFERENCES users (id)
);

-- PS-13 create clients table
CREATE TABLE IF NOT EXISTS clients
(
    id           UUID PRIMARY KEY NOT NULL,
    owner_id     UUID,
    email        VARCHAR(80)      NOT NULL,
    phone        VARCHAR(20)      NOT NULL,
    first_name   VARCHAR(50)      NOT NULL,
    last_name    VARCHAR(50)      NOT NULL,
    country      VARCHAR(30)      NOT NULL,
    language     VARCHAR(20)      NOT NULL,
    comment_text VARCHAR(255)     NOT NULL,
    created_at   TIMESTAMP        NOT NULL,
    updated_at   TIMESTAMP        NOT NULL,
    CONSTRAINT fk_clients_owner FOREIGN KEY (owner_id) REFERENCES users (id)
);

-- PS-13 create apartments table
CREATE TABLE IF NOT EXISTS apartments
(
    id                    UUID PRIMARY KEY NOT NULL,
    parent_id             UUID,
    owner_id              UUID,
    apartment_category_id UUID,
    name                  VARCHAR(50)      NOT NULL,
    type                  VARCHAR(30)      NOT NULL,
    country               VARCHAR(30)      NOT NULL,
    city                  VARCHAR(30)      NOT NULL,
    street                VARCHAR(50)      NOT NULL,
    floor                 INT              NOT NULL,
    pet                   BOOL             NOT NULL,
    smoking               BOOL             NOT NULL,
    parking_place         INT              NOT NULL,
    description           TEXT             NOT NULL,
    created_at            TIMESTAMP        NOT NULL,
    updated_at            TIMESTAMP        NOT NULL,
    CONSTRAINT fk_apartments_owner FOREIGN KEY (owner_id) REFERENCES users (id),
    CONSTRAINT fk_apartments_parent FOREIGN KEY (parent_id) REFERENCES apartments (id),
    CONSTRAINT fk_apartments_category FOREIGN KEY (apartment_category_id) REFERENCES apartment_categories (id)
);

-- PS-13 create bookings table
CREATE TABLE IF NOT EXISTS bookings
(
    id              UUID PRIMARY KEY NOT NULL,
    apartment_id    UUID             NOT NULL,
    client_id       UUID             NOT NULL,
    owner_id        UUID,
    currency_id     UUID             NOT NULL,
    status          VARCHAR(20)      NOT NULL,
    price           DECIMAL(15, 2)   NOT NULL,
    is_edited_price BOOL             NOT NULL,
    start_date      TIMESTAMP        NOT NULL,
    end_date        TIMESTAMP        NOT NULL,
    created_at      TIMESTAMP        NOT NULL,
    updated_at      TIMESTAMP        NOT NULL,
    CONSTRAINT fk_bookings_apartment FOREIGN KEY (apartment_id) REFERENCES apartments (id),
    CONSTRAINT fk_bookings_client FOREIGN KEY (client_id) REFERENCES clients (id),
    CONSTRAINT fk_bookings_owner FOREIGN KEY (owner_id) REFERENCES users (id),
    CONSTRAINT fk_bookings_currency FOREIGN KEY (currency_id) REFERENCES currencies (id)
);

-- PS-13 create price_categories table
CREATE TABLE IF NOT EXISTS price_categories
(
    id          UUID PRIMARY KEY NOT NULL,
    owner_id    UUID             NOT NULL,
    currency_id UUID             NOT NULL,
    name        VARCHAR(30)      NOT NULL,
    priority    VARCHAR(10)      NOT NULL,
    created_at  TIMESTAMP        NOT NULL,
    updated_at  TIMESTAMP        NOT NULL,
    CONSTRAINT fk_price_categories_owner FOREIGN KEY (owner_id) REFERENCES users (id),
    CONSTRAINT fk_price_categories_currency FOREIGN KEY (currency_id) REFERENCES currencies (id)
);

-- PS-13 create price_category_to_apartments_categories table
CREATE TABLE IF NOT EXISTS price_category_to_apartments_categories
(
    id                    UUID PRIMARY KEY NOT NULL,
    apartment_category_id UUID             NOT NULL,
    price_category_id     UUID             NOT NULL,
    owner_id              UUID             NOT NULL,
    currency_id           UUID             NOT NULL,
    period                INTEGER          NOT NULL,
    price                 DECIMAL(15, 2)   NOT NULL,
    created_at            TIMESTAMP        NOT NULL,
    updated_at            TIMESTAMP        NOT NULL,
    CONSTRAINT fk_price_cat_to_apartment_cat FOREIGN KEY (apartment_category_id) REFERENCES apartment_categories (id),
    CONSTRAINT fk_price_cat_to_price_cat FOREIGN KEY (price_category_id) REFERENCES price_categories (id),
    CONSTRAINT fk_price_cat_owner FOREIGN KEY (owner_id) REFERENCES users (id),
    CONSTRAINT fk_price_cat_currency FOREIGN KEY (currency_id) REFERENCES currencies (id)
);

-- region price_category_schedule table
CREATE TABLE IF NOT EXISTS price_category_schedule
(
    id                UUID PRIMARY KEY NOT NULL,
    owner_id          UUID             NOT NULL,
    price_category_id UUID             NOT NULL,
    start_date        TIMESTAMP        NOT NULL,
    end_date          TIMESTAMP        NOT NULL,
    created_at        TIMESTAMP        NOT NULL,
    updated_at        TIMESTAMP        NOT NULL,
    CONSTRAINT fk_price_cat_sched_owner FOREIGN KEY (owner_id) REFERENCES users (id),
    CONSTRAINT fk_price_cat_sched_price_cat FOREIGN KEY (price_category_id) REFERENCES price_categories (id)
);

-- endregion price_category_schedule table

-- region prices table
CREATE TABLE IF NOT EXISTS prices
(
    id              UUID PRIMARY KEY NOT NULL,
    apartment_id    UUID,
    owner_id        UUID,
    currency_id     UUID,
    price           DECIMAL(15, 2)   NOT NULL,
    date            TIMESTAMP        NOT NULL,
    is_edited_price BOOL             NOT NULL,
    created_at      TIMESTAMP        NOT NULL,
    updated_at      TIMESTAMP        NOT NULL,
    CONSTRAINT fk_prices_apartment FOREIGN KEY (apartment_id) REFERENCES apartments (id),
    CONSTRAINT fk_prices_owner FOREIGN KEY (owner_id) REFERENCES users (id),
    CONSTRAINT fk_prices_currency FOREIGN KEY (currency_id) REFERENCES currencies (id)
);

-- endregion prices table

-- region board_details table
CREATE TABLE IF NOT EXISTS board_details
(
    id                         UUID PRIMARY KEY ,
    booking_id                 UUID             ,
    apartment_id               UUID             ,
    owner_id                   UUID             ,
    currency_id                UUID             ,
    price                      DECIMAL(15, 2)   ,
    date                       TIMESTAMP        ,
    client_name                VARCHAR(30)      ,
    apartments_city            VARCHAR(30)      ,
    apartments_sleeping_places DECIMAL(10)      ,
    created_at                 TIMESTAMP        ,
    updated_at                 TIMESTAMP        ,
    CONSTRAINT fk_board_details_booking FOREIGN KEY (booking_id) REFERENCES bookings (id),
    CONSTRAINT fk_board_details_apartment FOREIGN KEY (apartment_id) REFERENCES apartments (id),
    CONSTRAINT fk_board_details_owner FOREIGN KEY (owner_id) REFERENCES users (id),
    CONSTRAINT fk_board_details_currency FOREIGN KEY (currency_id) REFERENCES currencies (id)
);

-- endregion board_details table

-- region workers_to_apartments
CREATE TABLE IF NOT EXISTS workers_to_apartments
(
    id           UUID PRIMARY KEY NOT NULL,
    apartment_id UUID             NOT NULL,
    owner_id     UUID             NOT NULL,
    worker_id    UUID             NOT NULL,
    CONSTRAINT fk_workers_to_apartments_apartment FOREIGN KEY (apartment_id) REFERENCES apartments (id),
    CONSTRAINT fk_workers_to_apartments_owner FOREIGN KEY (owner_id) REFERENCES users (id),
    CONSTRAINT fk_workers_to_apartments_worker FOREIGN KEY (worker_id) REFERENCES users (id)
);
