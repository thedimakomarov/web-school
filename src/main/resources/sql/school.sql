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

INSERT INTO teachers(first_name, last_name, mobile)
VALUES ('arnold', 'flannagan', '000000000'),
       ('debbie', 'adams', '000000000'),
       ('zhess', 'black', '000000000');

INSERT INTO subjects(name)
VALUES ('algebra'),
       ('geography'),
       ('english');

INSERT INTO teams(name)
VALUES ('alfa');

INSERT INTO students(first_name, last_name, mobile, team_id)
VALUES ('alex', 'fitzgerald', '000000000', (SELECT id FROM teams WHERE name = 'alfa')),
       ('kate', 'dodson', '000000000', (SELECT id FROM teams WHERE name = 'alfa')),
       ('robert', 'finch', '000000000', (SELECT id FROM teams WHERE name = 'alfa'));

INSERT INTO lessons(topic, date, team_id, teacher_id, subject_id)
VALUES ('topic1',
        TIMESTAMP '2011-05-16 15:36:38',
        (SELECT id FROM teams WHERE name = 'alfa'),
        (SELECT id FROM teachers WHERE last_name = 'flannagan'),
        (SELECT id FROM subjects WHERE name = 'algebra')),
       ('topic2',
        TIMESTAMP '2011-05-16 17:36:38',
        (SELECT id FROM teams WHERE name = 'alfa'),
        (SELECT id FROM teachers WHERE last_name = 'adams'),
        (SELECT id FROM subjects WHERE name = 'geography')),
       ('topic3',
        TIMESTAMP '2011-05-16 19:36:38',
        (SELECT id FROM teams WHERE name = 'alfa'),
        (SELECT id FROM teachers WHERE last_name = 'black'),
        (SELECT id FROM subjects WHERE name = 'english'));

INSERT INTO performances(is_present, mark, student_id, lesson_id)
VALUES (true,
        80,
        (SELECT id FROM students WHERE last_name = 'fitzgerald'),
        (SELECT id FROM lessons WHERE topic = 'topic1')),
       (true,
        85,
        (SELECT id FROM students WHERE last_name = 'dodson'),
        (SELECT id FROM lessons WHERE topic = 'topic2')),
       (false,
        0,
        (SELECT id FROM students WHERE last_name = 'finch'),
        (SELECT id FROM lessons WHERE topic = 'topic3'));

