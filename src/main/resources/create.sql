CREATE DATABASE ticket;

CREATE TABLE journey
(
    id serial,
    station_from varchar(200) NOT NULL,
    station_to varchar(200) NOT NULL,
    departure date NOT NULL,
    arrival date NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE journey OWNER to postgres;

INSERT INTO journey (station_from, station_to, departure, arrival)
VALUES ('Odessa', 'Kiev', '10.06.2021', '11.06.2021');
INSERT INTO journey (station_from, station_to, departure, arrival)
VALUES ('Odessa', 'Kiev', '10.07.2021', '11.07.2021');
INSERT INTO journey (station_from, station_to, departure, arrival)
VALUES ('Odessa', 'Kiev', '10.08.2021', '11.08.2021');

INSERT INTO journey (station_from, station_to, departure, arrival)
VALUES ('Kiev', 'Odessa', '20.06.2021', '21.06.2021');
INSERT INTO journey (station_from, station_to, departure, arrival)
VALUES ('Kiev', 'Odessa', '20.07.2021', '21.07.2021');
INSERT INTO journey (station_from, station_to, departure, arrival)
VALUES ('Kiev', 'Odessa', '20.08.2021', '21.08.2021');