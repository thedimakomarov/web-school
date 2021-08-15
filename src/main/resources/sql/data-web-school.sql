--TODO: make data more realistic
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

