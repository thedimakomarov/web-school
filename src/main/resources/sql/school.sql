CREATE TABLE IF NOT EXISTS teachers
(
    id              BIGSERIAL PRIMARY KEY,
    first_name      VARCHAR NOT NULL,
    middle_name     VARCHAR,
    last_name       VARCHAR NOT NULL,
    patronymic_name VARCHAR,
    phone_number    VARCHAR
);

CREATE TABLE IF NOT EXISTS groups
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS students
(
    id              BIGSERIAL PRIMARY KEY,
    first_name      VARCHAR NOT NULL,
    middle_name     VARCHAR,
    last_name       VARCHAR NOT NULL,
    patronymic_name VARCHAR,
    phone_number    VARCHAR,
    group_id        BIGINT NOT NULL,
    CONSTRAINT group_fk
        FOREIGN KEY (group_id)
            REFERENCES groups (id)
                ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS lessons
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR NOT NULL,
    group_id BIGINT NOT NULL,
    teacher_id BIGINT NOT NULL,
    CONSTRAINT group_fk
        FOREIGN KEY (group_id)
            REFERENCES groups(id)
            ON DELETE SET NULL,
    CONSTRAINT teacher_fk
        FOREIGN KEY (teacher_id)
            REFERENCES teachers(id)
                ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS marks (
    id BIGSERIAL PRIMARY KEY,
    mark INT NOT NULL,
    student_id BIGINT NOT NULL,
    lesson_id BIGINT NOT NULL,
    CONSTRAINT student_fk
        FOREIGN KEY (student_id)
            REFERENCES students(id)
                ON DELETE SET NULL,
    CONSTRAINT lesson_fk
        FOREIGN KEY (lesson_id)
            REFERENCES lessons(id)
                ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS absentees (
     id BIGSERIAL PRIMARY KEY,
     is_present BOOLEAN NOT NULL,
     student_id BIGINT NOT NULL,
     lesson_id BIGINT NOT NULL,
     CONSTRAINT student_fk
         FOREIGN KEY (student_id)
             REFERENCES students(id)
                ON DELETE SET NULL,
     CONSTRAINT lesson_fk
         FOREIGN KEY (lesson_id)
             REFERENCES lessons(id)
                ON DELETE SET NULL
);

-- INSERT INTO teachers(first_name, middle_name, last_name, patronymic_name, phone_number)
-- VALUES ('', '', '', '', ''),
--        ('', '', '', '', ''),
--        ('', '', '', '', '');

