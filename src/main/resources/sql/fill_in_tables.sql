INSERT INTO exhibition_halls (name, allowable_number_of_visitors_per_day)
VALUES ('Orange', 100);
INSERT INTO exhibition_halls (name, allowable_number_of_visitors_per_day)
VALUES ('Green', 120);
INSERT INTO exhibition_halls (name, allowable_number_of_visitors_per_day)
VALUES ('Blue', 70);
INSERT INTO exhibition_halls (name, allowable_number_of_visitors_per_day)
VALUES ('Black', 150);
INSERT INTO exhibition_halls (name, allowable_number_of_visitors_per_day)
VALUES ('White', 150);

INSERT INTO exhibitions (name, description, begin_date, end_date, full_ticket_price, exhibition_hall_id)
VALUES ('Water', 'Exhibition describes modern problem of water ecology', '2021-11-01', '2021-11-30', 150, 1);
INSERT INTO exhibitions (name, description, begin_date, end_date, full_ticket_price, exhibition_hall_id)
VALUES ('Forest', 'Exhibition describes modern problem of forests distinction', '2021-09-11', '2021-11-30', 100, 2);
INSERT INTO exhibitions (name, description, begin_date, end_date, full_ticket_price, exhibition_hall_id)
VALUES ('Africa', 'Exhibition describes modern problem of animals in Africa', '2021-04-01', '2021-06-30', 120, 3);
INSERT INTO exhibitions (name, description, begin_date, end_date, full_ticket_price, exhibition_hall_id)
VALUES ('Salt', 'Exhibition describes how valuable salt was during our history', '2021-07-01', '2021-09-30', 50, 5);
INSERT INTO exhibitions (name, description, begin_date, end_date, full_ticket_price, exhibition_hall_id)
VALUES ('Money', 'Exhibition describes history of money', '2021-02-01', '2021-03-31', 50, 4);

INSERT INTO exhibition_users (email, pass, first_name, last_name, phone, role)
VALUES ('alex@email.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'Alex', 'Stone', '+38(050)111-22-33', 'EMPLOYEE');
INSERT INTO exhibition_users (email, pass, first_name, last_name, phone)
VALUES ( 'ben@email.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'Ben', 'White', '+38(098)222-22-33');
INSERT INTO exhibition_users (email, pass, first_name, last_name, phone)
VALUES ('carl@email.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'Carl', 'Steel', '+38(073)333-44-33');
INSERT INTO exhibition_users (email, pass, first_name, last_name, phone)
VALUES ('tom@email.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'Tom', 'Youth', '+38(067)444-55-33');
INSERT INTO exhibition_users (email, pass, first_name, last_name, phone)
VALUES ('mag@email.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'Magi', 'Youth', '+38(067)444-55-33');

INSERT INTO tickets (visit_date, order_date, ticket_type, ticket_price, user_id, exhibition_id)
VALUES ('2021-11-20', '2020-11-10', 'EMPLOYEE', 200, 1, 1);
INSERT INTO tickets (visit_date, order_date, ticket_type, ticket_price, user_id, exhibition_id)
VALUES ('2021-11-25', '2020-11-06', 'EMPLOYEE', 200, 1, 1);
INSERT INTO tickets (visit_date, order_date, ticket_type, ticket_price, user_id, exhibition_id)
VALUES ('2021-09-16', '2020-09-01', 'PENSIONER', 200, 2, 2);
INSERT INTO tickets (visit_date, order_date, ticket_type, ticket_price, user_id, exhibition_id)
VALUES ('2021-10-15', '2020-09-27', 'PENSIONER', 200, 3, 2);
INSERT INTO tickets (visit_date, order_date, ticket_type, ticket_price, user_id, exhibition_id)
VALUES ('2021-03-05', '2020-02-18', 'KID', 200, 2, 5);
INSERT INTO tickets (visit_date, order_date, ticket_type, ticket_price, user_id, exhibition_id)
VALUES ('2021-03-05', '2020-02-18', 'KID', 200, 3, 5);
INSERT INTO tickets (visit_date, order_date, ticket_type, ticket_price, user_id, exhibition_id)
VALUES ('2021-05-21', '2020-04-04', 'STUDENT', 200, 4, 3);
INSERT INTO tickets (visit_date, order_date, ticket_type, ticket_price, user_id, exhibition_id)
VALUES ('2021-05-21', '2020-04-12', 'STUDENT', 200, 4, 3);
INSERT INTO tickets (visit_date, order_date, ticket_type, ticket_price, user_id, exhibition_id)
VALUES ('2021-07-03', '2020-07-02', 'FULL', 200, 5, 5);
INSERT INTO tickets (visit_date, order_date, ticket_type, ticket_price, user_id, exhibition_id)
VALUES ('2021-09-16', '2020-08-12', 'FULL', 200, 5, 5);
INSERT INTO tickets (visit_date, order_date, ticket_type, ticket_price, user_id, exhibition_id)
VALUES ('2021-09-28', '2020-09-07', 'FULL', 200, 5, 5);
