INSERT INTO users (id, username, password, description, address, first_name, last_name, birth_date) VALUES
(10001, 'admin', '$2a$12$zyGtthEdoax1fGTX3Cs0m.TR2JcHK2hcrp/V4l5dcikQI5PegAAJW', 'Администратор приложения', 'Ул. Петровка, 57', 'Илья', 'Петров', '1990-01-01'),
(10002, 'user', '$2a$12$F234dEA.2mv.vLApZSO/1ek9wBML.malz7PGuHN32cvV.RHqpWN6G', 'Пользователь приложения', 'Ул. Тверская, 34', 'Кирилл', 'Иванов', '1997-04-11');

insert into user_roles(user_id, roles) VALUES
(10001, 0),
(10002, 1);