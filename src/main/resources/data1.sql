insert into role(created_by, created_time, updated_by, updated_time, role)
VALUES ('Root','2021-01-05 00:00:00','Root','2021-01-05 00:00:00','Root'),
       ('Root','2021-01-05 00:00:00','Root','2021-01-05 00:00:00','Admin'),
       ('Root','2021-01-05 00:00:00','Root','2021-01-05 00:00:00','Manager'),
       ('Root','2021-01-05 00:00:00','Root','2021-01-05 00:00:00','Employee'),
       ('Root','2021-01-05 00:00:00','Root','2021-01-05 00:00:00','Owner');


insert into company (created_by, created_time, updated_by, updated_time, address1, address2, deleted, email, enabled, establish_date, representative, state, status, title, zip)
values ('Root','2021-03-21 18:12:00.348868','Root','2021-03-21 18:12:00.348895','7900 Brompton st','','false','waris@gmail.com','true','2020-11-01','Waris Nigmat','TEXAS','ACTIVE','Warehouse Management Ltd','778900');


insert into users (created_by, created_time, updated_by, updated_time, deleted, email, enabled, firstname, lastname, password, phone, status, company_id, role_id)
VALUES ('Root','2021-03-21 18:12:00.348868','Root','2021-03-21 18:12:00.348895','false','root@gmail.com','true','root', 'root','$2a$10$Q7ilQ6Hv11qpU0T7xfMzMeqxoPXkvhTVXxFqg0UL2xvLnhNqB7vba','7739461852','ACTIVE','1','1'),
       ('Root','2021-03-21 18:12:00.348868','Root','2021-03-21 18:12:00.348895','false','owner@gmail.com','true','owner', 'owner','$2a$10$Q7ilQ6Hv11qpU0T7xfMzMeqxoPXkvhTVXxFqg0UL2xvLnhNqB7vba','7739461852','ACTIVE','1','5');
