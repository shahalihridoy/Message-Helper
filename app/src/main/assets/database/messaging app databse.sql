--
-- File generated with SQLiteStudio v3.2.1 on Tue Feb 19 20:13:59 2019
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: catagory
CREATE TABLE catagory (id INTEGER PRIMARY KEY ASC AUTOINCREMENT NOT NULL, catagory STRING (100) UNIQUE NOT NULL);
INSERT INTO catagory (id, catagory) VALUES (1, 'Love');
INSERT INTO catagory (id, catagory) VALUES (2, 'Sad');
INSERT INTO catagory (id, catagory) VALUES (3, 'Riddle');

-- Table: message
CREATE TABLE message (
id integer references catagory (id),
body text (500),
favourite boolean default false
);
INSERT INTO message (id, body, favourite) VALUES (2, 'I''ve found a love ', 0);
INSERT INTO message (id, body, favourite) VALUES (2, 'Darling just dive right in follow my lead', 0);
INSERT INTO message (id, body, favourite) VALUES (1, 'Darling just kiss me slow', 0);
INSERT INTO message (id, body, favourite) VALUES (1, 'Darling just kiss me slow', 0);
INSERT INTO message (id, body, favourite) VALUES (1, 'Baby you look perfect tonight........', 1);
INSERT INTO message (id, body, favourite) VALUES (1, 'I''ve found a love ........', 1);
INSERT INTO message (id, body, favourite) VALUES (1, 'Darling just hold my hand, dancing in the dark, listening to our favourite song', 1);
INSERT INTO message (id, body, favourite) VALUES (1, 'I,I,I dancing in the dark, befoot on the grass', 0);
INSERT INTO message (id, body, favourite) VALUES (1, 'I don''t deserved it, you look perfect tonight', 1);
INSERT INTO message (id, body, favourite) VALUES (3, 'Thing that rise up up up the sky but doesn''t have foot', 1);

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
