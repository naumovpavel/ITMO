

create table if not exists Person
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    height double precision NOT NULL,
    eyeColor Color NOT NULL
);

create table if not exists Coordinates
(
    id SERIAL PRIMARY KEY,
    x INTEGER CHECK(x > -73),
    y INTEGER CHECK(y < 865)
);

create table if not exists Users
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    salt VARCHAR(255) NOT NULL,
    passwordHash bytea NOT NULL
);

create table if not exists LabWork
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    coordinates_id int NOT NULL REFERENCES Coordinates(id) ON DELETE CASCADE,
    creationDate timestamp NOT NULL DEFAULT current_timestamp ,
    minimalPoint BIGINT CHECK(minimalPoint > 0),
    tunedInWorks INT,
    owner_id INT NOT NULL REFERENCES Users(id),
    difficulty Difficulty NOT NULL,
    author_id int NOT NULL REFERENCES Person(id) ON DELETE CASCADE
);
