CREATE TABLE IF NOT EXISTS teachers
(
    id         BIGSERIAL PRIMARY KEY,
    first_name VARCHAR NOT NULL,
    last_name  VARCHAR NOT NULL,
    mobile     VARCHAR
);

CREATE TABLE IF NOT EXISTS subjects
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS teams
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS students
(
    id         BIGSERIAL PRIMARY KEY,
    first_name VARCHAR NOT NULL,
    last_name  VARCHAR NOT NULL,
    mobile     VARCHAR,
    team_id    BIGINT,
    CONSTRAINT team_fk
        FOREIGN KEY (team_id)
            REFERENCES teams (id)
            ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS lessons
(
    id         BIGSERIAL PRIMARY KEY,
    topic      VARCHAR   NOT NULL,
    date       TIMESTAMP NOT NULL,
    team_id    BIGINT    NOT NULL,
    teacher_id BIGINT    NOT NULL,
    subject_id BIGINT    NOT NULL,
    CONSTRAINT team_fk
        FOREIGN KEY (team_id)
            REFERENCES teams (id)
            ON DELETE SET NULL,
    CONSTRAINT teacher_fk
        FOREIGN KEY (teacher_id)
            REFERENCES teachers (id)
            ON DELETE SET NULL,
    CONSTRAINT subject_fk
        FOREIGN KEY (subject_id)
            REFERENCES subjects (id)
            ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS performances
(
    id         BIGSERIAL PRIMARY KEY,
    is_present BOOLEAN NOT NULL,
    mark       INT     NOT NULL,
    student_id BIGINT  NOT NULL,
    lesson_id  BIGINT  NOT NULL,
    CONSTRAINT student_fk
        FOREIGN KEY (student_id)
            REFERENCES students (id)
            ON DELETE SET NULL,
    CONSTRAINT lesson_fk
        FOREIGN KEY (lesson_id)
            REFERENCES lessons (id)
            ON DELETE SET NULL
);