insert into currencies (id, name, currency_code)
values ('db41867b-682b-4121-b84b-830530fe9f2e', 'Peso', 'PHP'),
       ('3b56fe6e-6910-4b0d-863e-ac60262e7a17', 'Yuan Renminbi', 'CNY');

insert into clients (id, email, phone, first_name, last_name, country, language, comment_text, created_at, updated_at)
values ('0e288090-280c-489f-8058-bc36d534f3a5', 'bmulvagh0@jigsy.com', '791-689-9576', 'Noëlla', 'Mulvagh', 'Ukraine',
        'Arabic', '05508 Elmside Parkway', '2023-10-01T23:15:46Z', '2024-01-22T22:18:11Z'),
       ('3a49ce13-97ea-4942-b0e4-f0b814d2de17', 'mgagan1@nhs.uk', '900-469-3331', 'Stéphanie', 'Gagan', 'Brazil',
        'Hungarian', '4 Birchwood Center', '2023-10-16T03:21:48Z', '2023-07-29T07:10:50Z'),
       ('47be1383-0f58-4aa3-b0d7-d6786286bc13', 'aploughwright2@spotify.com', '377-672-4823', 'Valérie', 'Ploughwright',
        'China', 'West Frisian', '68 Parkside Court', '2023-11-23T17:37:26Z', '2023-09-05T15:09:42Z'),
       ('76104b19-d1ce-4c0f-9641-40b81b07ccbe', 'clehrer3@ebay.com', '516-555-0726', 'Nélie', 'Lehrer', 'Cameroon',
        'Maltese', '5 Haas Pass', '2023-05-30T11:08:38Z', '2024-01-28T05:59:31Z');

INSERT INTO apartments (id, parent_id, owner_id, apartment_category_id, currency_id, name, type, country, city, street,
                        FLOOR, pet, smoking, parking_place, description, created_at, UPDATED_AT)
VALUES ('f47ac10b-58cc-4372-a567-0e02b2c3d479', NULL, null, NULL, NULL, 'Apartment 1', 'Studio', 'USA', 'New York',
        'Broadway', 2, true, false, 1, 'Cozy studio apartment in the heart of New York City', '2023-09-01 13:57:40',
        '2023-09-01 13:57:40'),
       ('1ac2ab88-4efc-4ea7-a6d7-9738c7b0ca5d', NULL, NULL, NULL, NULL, 'Apartment 2', 'Apartment', 'USA',
        'Los Angeles', 'Hollywood Blvd', 3, false, true, 2, 'Spacious apartment with Hollywood sign view',
        '2023-09-01 13:57:40', '2023-09-01 13:57:40'),
       ('eccbc87e-4b5c-4331-a025-6545673431ef', NULL, NULL, NULL, NULL, 'Apartment 3', 'House', 'Canada', 'Toronto',
        'Yonge Street', 4, true, true, 0, 'Modern house with a beautiful view of Lake Ontario', '2023-09-01 13:57:40',
        '2023-09-01 13:57:40');

INSERT INTO prices (id, apartment_id, owner_id, currency_id, price, date, is_edited_price, created_at, updated_at)
VALUES ('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'f47ac10b-58cc-4372-a567-0e02b2c3d479', NULL,
        'db41867b-682b-4121-b84b-830530fe9f2e', 100.00, '2024-02-29 12:00:00', FALSE, '2024-03-02 12:00:00',
        '2024-03-02 12:00:00'),
       ('f47ac10b-58cc-4372-a567-1232b2c3d479', 'f47ac10b-58cc-4372-a567-0e02b2c3d479', NULL,
        '3b56fe6e-6910-4b0d-863e-ac60262e7a17', 150.00, '2024-03-01 12:00:00', FALSE, '2024-03-02 12:00:00',
        '2024-03-02 12:00:00'),
       ('f47ac10b-58cc-4372-a567-3212b2c3d479', 'f47ac10b-58cc-4372-a567-0e02b2c3d479', NULL,
        'db41867b-682b-4121-b84b-830530fe9f2e', 200.00, '2024-03-02 12:00:00', FALSE, '2024-03-02 12:00:00',
        '2024-03-02 12:00:00');

