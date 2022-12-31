INSERT INTO amenities (id, name) VALUES (1, 'pool');
INSERT INTO amenities (id, name) VALUES (2, 'internet');
INSERT INTO amenities (id, name) VALUES (3, 'wifi');
INSERT INTO amenities (id, name) VALUES (4, 'fax');
INSERT INTO amenities (id, name) VALUES (5, 'business room');

INSERT INTO hotels (id, name, address, rating) VALUES (1, 'Círculo Mexicano', 'Mexico City Historic Centre, Mexico City', 8.7);
INSERT INTO hotels_amenities (hotels_id, amenities_id) VALUES (1, 1);
INSERT INTO hotels_amenities (hotels_id, amenities_id) VALUES (1, 2);
INSERT INTO hotels_amenities (hotels_id, amenities_id) VALUES (1, 3);

INSERT INTO hotels (id, name, address, rating) VALUES (2, 'Downtown', 'Mexico City Historic Centre, Mexico City', 8.5);
INSERT INTO hotels_amenities (hotels_id, amenities_id) VALUES (2, 1);
INSERT INTO hotels_amenities (hotels_id, amenities_id) VALUES (2, 2);
INSERT INTO hotels_amenities (hotels_id, amenities_id) VALUES (2, 3);
INSERT INTO hotels_amenities (hotels_id, amenities_id) VALUES (2, 4);
INSERT INTO hotels_amenities (hotels_id, amenities_id) VALUES (2, 5);

INSERT INTO hotels (id, name, address, rating) VALUES (3, 'Hotel Metropol', 'Mexico City Historic Centre, Mexico City', 8.4);
INSERT INTO hotels_amenities (hotels_id, amenities_id) VALUES (3, 1);
INSERT INTO hotels_amenities (hotels_id, amenities_id) VALUES (3, 2);
INSERT INTO hotels_amenities (hotels_id, amenities_id) VALUES (3, 3);
INSERT INTO hotels (id, name, address, rating) VALUES (4, 'Roso Guest House', 'Mexico City Historic Centre, Mexico City', 8.7);
INSERT INTO hotels_amenities (hotels_id, amenities_id) VALUES (4, 1);
INSERT INTO hotels_amenities (hotels_id, amenities_id) VALUES (4, 2);
INSERT INTO hotels_amenities (hotels_id, amenities_id) VALUES (4, 3);
INSERT INTO hotels (id, name, address, rating) VALUES (5, 'Kali Ciudadela Mexico City', 'Mexico City Historic Centre, Mexico City', 8.8);
INSERT INTO hotels_amenities (hotels_id, amenities_id) VALUES (5, 1);
INSERT INTO hotels_amenities (hotels_id, amenities_id) VALUES (5, 2);
INSERT INTO hotels_amenities (hotels_id, amenities_id) VALUES (5, 3);
INSERT INTO hotels (id, name, address, rating) VALUES (6, 'ULIV Apartments - Polanco II', 'Mexico City', 8.8);
INSERT INTO hotels_amenities (hotels_id, amenities_id) VALUES (6, 1);
INSERT INTO hotels_amenities (hotels_id, amenities_id) VALUES (6, 2);
INSERT INTO hotels_amenities (hotels_id, amenities_id) VALUES (6, 3);



