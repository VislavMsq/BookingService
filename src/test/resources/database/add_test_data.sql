-- Insert dummy data into currencies table
INSERT INTO currencies (id, name, code)
VALUES ('3f4245b3-94cc-4b2d-bc7b-d29f6a0d7f20', 'US Dollar', 'USD'),
       ('6e0727d5-8eb9-438e-8e61-c30e0506a889', 'Euro', 'EUR'),
       ('a78c2d2b-cc71-42e1-a8c2-bc7b42bd6bae', 'British Pound', 'GBP'),
       ('db41867b-682b-4121-b84b-830530fe9f2e', 'Peso', 'PHP'),
       ('3b56fe6e-6910-4b0d-863e-ac60262e7a17', 'Yuan Renminbi', 'CNY');

-- Insert dummy data into users table
INSERT INTO users (id, owner_id, currency_id, email, phone, password, timezone, first_name, last_name, country,
                   language, role, created_at, updated_at)
VALUES ('c205da57-0722-4b5d-9c13-30adfe3b0d72', NULL, '3f4245b3-94cc-4b2d-bc7b-d29f6a0d7f20', 'user1@example.com',
        '123456789', 'password1', 0, 'John', 'Doe', 'USA', 'en', 'OWNER', '2024-03-03 12:00:00', '2024-03-03 12:00:00'),
       ('b6e4b3b5-8265-4aeb-bdb7-17f5081e56a2', 'c205da57-0722-4b5d-9c13-30adfe3b0d72',
        '6e0727d5-8eb9-438e-8e61-c30e0506a889', 'user2@example.com', '987654321', 'password2', 0, 'Jane', 'Doe', 'UK',
        'en', 'WORKER', '2024-03-03 12:01:00', '2024-03-03 12:01:00'),
       ('4a56fe6e-6910-4b0d-863e-ac60262e7a17', null, '3b56fe6e-6910-4b0d-863e-ac60262e7a17', 'aloha.test@gmail.com',
        '+48798456123', '$2a$04$ulExgnCSzgj/7Gl4w4hLd.qdr6Bv3djEb6FYwJaSK../sLhLEevLG', 2, 'Aloha', 'Test', 'Poland',
        'Russian', 'OWNER', '2023-10-01T23:15:46Z', '2024-01-22T22:18:11Z');

-- Insert dummy data into apartment_categories table
INSERT INTO apartment_categories (id, owner_id, name, abbreviation, type, sleep_place, created_at, updated_at)
VALUES ('ad99034d-4a69-492f-b65f-4aef01d21ee6', 'c205da57-0722-4b5d-9c13-30adfe3b0d72', 'Apartment Category 1', 'ABC1',
        'APARTMENT', 2, '2024-03-03 12:10:00', '2024-03-03 12:10:00'),
       ('be2f0f46-9e36-4b99-8d62-8e498b783c38', 'c205da57-0722-4b5d-9c13-30adfe3b0d72', 'Apartment Category 2', 'ABC2',
        'ROOM', 4, '2024-03-03 12:11:00', '2024-03-03 12:11:00');

-- Insert dummy data into clients table
INSERT INTO clients (id, owner_id, email, phone, first_name, last_name, country, language, comment_text, created_at,
                     updated_at)
VALUES ('f9f5e56d-740e-4a37-bcce-1c5c6781d5f8', 'c205da57-0722-4b5d-9c13-30adfe3b0d72', 'client1@example.com',
        '111222333', 'Client', 'One', 'USA', 'en', 'Lorem ipsum', '2024-03-03 12:20:00', '2024-03-03 12:20:00'),
       ('7f188df1-b63b-41c8-88e9-6050da205a2f', 'c205da57-0722-4b5d-9c13-30adfe3b0d72', 'client2@example.com',
        '444555666', 'Client', 'Two', 'UK', 'en', 'Dolor sit amet', '2024-03-03 12:21:00', '2024-03-03 12:21:00'),
       ('0e288090-280c-489f-8058-bc36d534f3a5', '4a56fe6e-6910-4b0d-863e-ac60262e7a17', 'bmulvagh0@jigsy.com',
        '791-689-9576', 'Nelly', 'Mulvagh', 'Ukraine',
        'Arabic', '05508 Elmside Parkway', '2023-10-01T23:15:46Z', '2024-01-22T22:18:11Z'),
       ('3a49ce13-97ea-4942-b0e4-f0b814d2de17', '4a56fe6e-6910-4b0d-863e-ac60262e7a17', 'mgagan1@nhs.uk',
        '900-469-3331', 'Stéphanie', 'Gagan', 'Brazil',
        'Hungarian', '4 Birchwood Center', '2023-10-16T03:21:48Z', '2023-07-29T07:10:50Z'),
       ('47be1383-0f58-4aa3-b0d7-d6786286bc13', '4a56fe6e-6910-4b0d-863e-ac60262e7a17', 'aploughwright2@spotify.com',
        '377-672-4823', 'Valérie', 'Ploughwright',
        'China', 'West Frisian', '68 Parkside Court', '2023-11-23T17:37:26Z', '2023-09-05T15:09:42Z'),
       ('76104b19-d1ce-4c0f-9641-40b81b07ccbe', '4a56fe6e-6910-4b0d-863e-ac60262e7a17', 'clehrer3@ebay.com',
        '516-555-0726', 'Nélie', 'Lehrer', 'Cameroon',
        'Maltese', '5 Haas Pass', '2023-05-30T11:08:38Z', '2024-01-28T05:59:31Z');

-- Insert dummy data into apartments table
INSERT INTO apartments (id, parent_id, owner_id, apartment_category_id, name, type, country, city, street,
                        floor, pet, smoking, parking_place, description, created_at, updated_at)
VALUES ('3f120739-8a84-4e21-84b3-7a66358157bf', NULL, 'c205da57-0722-4b5d-9c13-30adfe3b0d72',
        'ad99034d-4a69-492f-b65f-4aef01d21ee6', 'Apartment 1', 'APARTMENT', 'USA',
        'New York', '123 Main St', 5, true, false, 1, 'Lorem ipsum', '2024-03-03 12:30:00', '2024-03-03 12:30:00'),
       ('8c5fcf45-8e6d-42cd-8da3-c978c8cc58b2', NULL, 'c205da57-0722-4b5d-9c13-30adfe3b0d72',
        'be2f0f46-9e36-4b99-8d62-8e498b783c38', 'Apartment 2', 'ROOM',
        'UK', 'London', '456 Oak St', 3, false, true, 2, 'Dolor sit amet', '2024-03-03 12:31:00',
        '2024-03-03 12:31:00'),
       ('f47ac10b-58cc-4372-a567-0e02b2c3d479', NULL, 'c205da57-0722-4b5d-9c13-30adfe3b0d72', NULL, 'Apartment 1', 'APARTMENT', 'USA', 'New York',
        'Broadway', 2, true, false, 1, 'Cozy studio apartment in the heart of New York City', '2023-09-01 13:57:40',
        '2023-09-01 13:57:40'),
       ('1ac2ab88-4efc-4ea7-a6d7-9738c7b0ca5d', NULL, 'c205da57-0722-4b5d-9c13-30adfe3b0d72', NULL, 'Apartment 2', 'APARTMENT', 'USA',
        'Los Angeles', 'Hollywood Blvd', 3, false, true, 2, 'Spacious apartment with Hollywood sign view',
        '2023-09-01 13:57:40', '2023-09-01 13:57:40'),
       ('eccbc87e-4b5c-4331-a025-6545673431ef', NULL, 'c205da57-0722-4b5d-9c13-30adfe3b0d72', NULL, 'Apartment 3', 'ROOM', 'Canada', 'Toronto',
        'Yonge Street', 4, true, true, 0, 'Modern house with a beautiful view of Lake Ontario', '2023-09-01 13:57:40',
        '2023-09-01 13:57:40');

-- Insert dummy data into bookings table
INSERT INTO bookings (id, apartment_id, client_id, owner_id, currency_id, status, price, is_edited_price, start_date,
                      end_date, created_at, updated_at)
VALUES ('bc79bf93-2493-40e6-9c92-7a0da27a3d85', '3f120739-8a84-4e21-84b3-7a66358157bf',
        'f9f5e56d-740e-4a37-bcce-1c5c6781d5f8', 'c205da57-0722-4b5d-9c13-30adfe3b0d72',
        '3f4245b3-94cc-4b2d-bc7b-d29f6a0d7f20', 'Confirmed', 150.00, false, '2024-03-05 12:00:00',
        '2024-03-10 12:00:00', '2024-03-03 12:40:00', '2024-03-03 12:40:00'),
       ('75250852-158a-4bb2-bd7d-87a56d48c4b5', '8c5fcf45-8e6d-42cd-8da3-c978c8cc58b2',
        '7f188df1-b63b-41c8-88e9-6050da205a2f', 'c205da57-0722-4b5d-9c13-30adfe3b0d72',
        '6e0727d5-8eb9-438e-8e61-c30e0506a889', 'Pending', 200.00, true, '2024-03-08 12:00:00', '2024-03-15 12:00:00',
        '2024-03-03 12:41:00', '2024-03-03 12:41:00');

-- Insert dummy data into price_categories table
INSERT INTO price_categories (id, owner_id, currency_id, name, priority, created_at, updated_at)
VALUES ('f050448b-7a16-468c-8183-5f161a83db62', 'c205da57-0722-4b5d-9c13-30adfe3b0d72',
        '3f4245b3-94cc-4b2d-bc7b-d29f6a0d7f20', 'Standard', 'High', '2024-03-03 12:50:00', '2024-03-03 12:50:00'),
       ('75235102-dff9-4a66-b576-80f0b017548c', 'c205da57-0722-4b5d-9c13-30adfe3b0d72',
        '6e0727d5-8eb9-438e-8e61-c30e0506a889', 'Premium', 'Low', '2024-03-03 12:51:00', '2024-03-03 12:51:00');

-- Insert dummy data into price_category_to_apartments_categories table
INSERT INTO price_category_to_apartments_categories (id, apartment_category_id, price_category_id, owner_id,
                                                     currency_id, period, price, created_at, updated_at)
VALUES ('7d6f1727-f7aa-48a2-9971-161f30f3b497', 'ad99034d-4a69-492f-b65f-4aef01d21ee6',
        'f050448b-7a16-468c-8183-5f161a83db62', 'c205da57-0722-4b5d-9c13-30adfe3b0d72',
        '3f4245b3-94cc-4b2d-bc7b-d29f6a0d7f20', '2024', 120.00, '2024-03-03 12:55:00',
        '2024-03-03 12:55:00'),
       ('d525cd10-5aa7-4ba6-8104-52906e77e8fe', 'be2f0f46-9e36-4b99-8d62-8e498b783c38',
        '75235102-dff9-4a66-b576-80f0b017548c', 'c205da57-0722-4b5d-9c13-30adfe3b0d72',
        '6e0727d5-8eb9-438e-8e61-c30e0506a889', '2024', 180.00, '2024-03-03 12:56:00',
        '2024-03-03 12:56:00');

-- Insert dummy data into price_category_schedule table
INSERT INTO price_category_schedule (id, owner_id, price_category_id, start_date, end_date, created_at, updated_at)
VALUES ('d4a39447-14d1-49d1-b18e-4d78ce11c193', 'c205da57-0722-4b5d-9c13-30adfe3b0d72',
        'f050448b-7a16-468c-8183-5f161a83db62', '2024-01-01 00:00:00', '2024-12-31 23:59:59', '2024-03-03 13:00:00',
        '2024-03-03 13:00:00'),
       ('d7a3ce4e-8b31-4bf3-bd14-9ba4a53121d2', 'c205da57-0722-4b5d-9c13-30adfe3b0d72',
        '75235102-dff9-4a66-b576-80f0b017548c', '2024-01-01 00:00:00', '2024-12-31 23:59:59', '2024-03-03 13:01:00',
        '2024-03-03 13:01:00');

-- Insert dummy data into prices table
INSERT INTO prices (id, apartment_id, owner_id, currency_id, price, date, is_edited_price, created_at, updated_at)
VALUES ('efa6495e-bf37-4a98-9e7c-7fe3c6929ddc', '3f120739-8a84-4e21-84b3-7a66358157bf',
        'c205da57-0722-4b5d-9c13-30adfe3b0d72', '3f4245b3-94cc-4b2d-bc7b-d29f6a0d7f20', 150.00, '2024-03-03 13:10:00',
        false, '2024-03-03 13:10:00', '2024-03-03 13:10:00'),
       ('5e00c3e0-58a0-4c1e-b749-d95ac308813d', '8c5fcf45-8e6d-42cd-8da3-c978c8cc58b2',
        'c205da57-0722-4b5d-9c13-30adfe3b0d72', '6e0727d5-8eb9-438e-8e61-c30e0506a889', 200.00, '2024-03-03 13:11:00',
        true, '2024-03-03 13:11:00', '2024-03-03 13:11:00'),
       ('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'f47ac10b-58cc-4372-a567-0e02b2c3d479', 'c205da57-0722-4b5d-9c13-30adfe3b0d72',
        '3b56fe6e-6910-4b0d-863e-ac60262e7a17', 100.00, '2024-02-29', FALSE, '2024-03-02 12:00:00',
        '2024-03-02 12:00:00'),
       ('f47ac10b-58cc-4372-a567-1232b2c3d479', 'f47ac10b-58cc-4372-a567-0e02b2c3d479', 'c205da57-0722-4b5d-9c13-30adfe3b0d72',
        '3b56fe6e-6910-4b0d-863e-ac60262e7a17', 150.00, '2024-03-01', FALSE, '2024-03-02 12:00:00',
        '2024-03-02 12:00:00'),
       ('f47ac10b-58cc-4372-a567-3212b2c3d479', 'f47ac10b-58cc-4372-a567-0e02b2c3d479', 'c205da57-0722-4b5d-9c13-30adfe3b0d72',
        'db41867b-682b-4121-b84b-830530fe9f2e', 200.00, '2024-03-02', FALSE, '2024-03-02 12:00:00',
        '2024-03-02 12:00:00');

-- Insert dummy data into board_details table
INSERT INTO board_details (id, booking_id, apartment_id, owner_id, currency_id, price, date, client_name,
                           apartments_city, apartments_sleeping_places, created_at, updated_at)
VALUES ('a82c865a-844d-4a25-a9c9-1f853f1f419a', 'bc79bf93-2493-40e6-9c92-7a0da27a3d85',
        'f47ac10b-58cc-4372-a567-0e02b2c3d479', 'c205da57-0722-4b5d-9c13-30adfe3b0d72',
        '3f4245b3-94cc-4b2d-bc7b-d29f6a0d7f20', 150.00, '2024-03-03 13:20:00', 'Client One', 'New York', 2,
        '2024-03-03 13:20:00', '2024-03-03 13:20:00'),
       ('5c773cd7-bdf8-4e0d-8b7d-06e8792e7d16', '75250852-158a-4bb2-bd7d-87a56d48c4b5',
        '8c5fcf45-8e6d-42cd-8da3-c978c8cc58b2', 'c205da57-0722-4b5d-9c13-30adfe3b0d72',
        '6e0727d5-8eb9-438e-8e61-c30e0506a889', 200.00, '2024-03-03 13:21:00', 'Client Two', 'London', 4,
        '2024-03-03 13:21:00', '2024-03-03 13:21:00');

-- Insert dummy data into workers_to_apartments table
INSERT INTO workers_to_apartments (id, apartment_id, owner_id, worker_id)
VALUES ('b400166f-65b5-421a-874f-48b6e1d3ad4f', '3f120739-8a84-4e21-84b3-7a66358157bf',
        'c205da57-0722-4b5d-9c13-30adfe3b0d72', 'b6e4b3b5-8265-4aeb-bdb7-17f5081e56a2'),
       ('04df2b06-2e0c-4149-890c-3ea25fbc9835', '8c5fcf45-8e6d-42cd-8da3-c978c8cc58b2',
        'c205da57-0722-4b5d-9c13-30adfe3b0d72', 'b6e4b3b5-8265-4aeb-bdb7-17f5081e56a2');

