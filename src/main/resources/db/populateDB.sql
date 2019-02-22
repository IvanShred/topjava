DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id) VALUES
  ('2015-05-30 10:00:00', 'Завтрак', 500, 100000),
  ('2015-05-30 13:00:00', 'Обед', 1000, 100000),
  ('2015-05-30 20:00:00', 'Ужин', 500, 100000),
  ('2015-05-31 10:00:00', 'Завтрак', 1000, 100000),
  ('2015-05-31 13:00:00', 'Обед', 500, 100000),
  ('2015-05-31 20:00:00', 'Ужин', 510, 100000),
  ('2019-02-16 10:00:00', 'Бутерброды', 1000, 100001),
  ('2019-02-16 13:00:00', 'Котлеты', 510, 100001),
  ('2019-02-16 20:00:00', 'Суп', 500, 100001),
  ('2019-02-17 10:00:00', 'Яичница', 500, 100001),
  ('2019-02-17 13:00:00', 'Макороны', 500, 100001),
  ('2019-02-17 20:00:00', 'Салат', 1000, 100001);