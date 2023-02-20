CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS university
(
    id   UUID DEFAULT uuid_generate_v4() UNIQUE PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS lecturer
(
    id     UUID DEFAULT uuid_generate_v4() UNIQUE PRIMARY KEY,
    name   VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS universities_lecturers
(
    id UUID DEFAULT uuid_generate_v4() UNIQUE PRIMARY KEY,
    university_id UUID references university(id),
    lecturer_id UUID references lecturer(id)
);

CREATE TABLE IF NOT EXISTS student
(
    id            UUID DEFAULT uuid_generate_v4() UNIQUE PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    age           INTEGER      NOT NULL,
    university_id UUID REFERENCES university (id)
);



