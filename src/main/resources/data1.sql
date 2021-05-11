insert into role(created_by, created_time, updated_by, updated_time, role)
VALUES ('Root','2021-01-05 00:00:00','Root','2021-01-05 00:00:00','Root'),
       ('Root','2021-01-05 00:00:00','Root','2021-01-05 00:00:00','Admin'),
       ('Root','2021-01-05 00:00:00','Root','2021-01-05 00:00:00','Manager'),
       ('Root','2021-01-05 00:00:00','Root','2021-01-05 00:00:00','Employee'),
       ('Root','2021-01-05 00:00:00','Root','2021-01-05 00:00:00','Owner');


insert into company (created_by, created_time, updated_by, updated_time, address1, address2, deleted, email, enabled, establish_date, representative, state, status, title, zip)
values ('Root','2021-03-21 18:12:00.348868','Root','2021-03-21 18:12:00.348895','7900 Brompton st','','false','waris@gmail.com','true','2020-11-01','Waris Nigmat','TEXAS','ACTIVE','Warehouse Management Ltd','778900'),
       ('Root','2021-05-10 18:49:58.112767','Root','2021-05-10 18:49:58.112793','7300 Brompton st','Building 12','false','nike@nike.com','true','2020-11-01','Wlliam Smith','TEXAS','ACTIVE','Nike Holding Ltd','778900');


insert into users (created_by, created_time, updated_by, updated_time, deleted, email, enabled, firstname, lastname, password, phone, status, company_id, role_id)
VALUES ('Root','2021-03-21 18:12:00.348868','Root','2021-03-21 18:12:00.348895','false','root@gmail.com','true','root', 'root','$2a$10$Q7ilQ6Hv11qpU0T7xfMzMeqxoPXkvhTVXxFqg0UL2xvLnhNqB7vba','7739461852','ACTIVE','1','1'),
       ('Root','2021-03-21 18:12:00.348868','Root','2021-03-21 18:12:00.348895','false','owner@gmail.com','true','owner', 'owner','$2a$10$Q7ilQ6Hv11qpU0T7xfMzMeqxoPXkvhTVXxFqg0UL2xvLnhNqB7vba','7739461852','ACTIVE','1','5'),
       ('Root','2021-05-10 18:53:23.255026','Root','2021-05-10 18:53:23.255042','false','admin@gmail.com','true','Admin','Admin','$2a$10$Q7ilQ6Hv11qpU0T7xfMzMeqxoPXkvhTVXxFqg0UL2xvLnhNqB7vba','12344','ACTIVE','2','2'),
       ('Root','2021-05-10 18:53:23.255026','Root','2021-05-10 18:53:23.255042','false','manager@gmail.com','true','manager','manager','$2a$10$Q7ilQ6Hv11qpU0T7xfMzMeqxoPXkvhTVXxFqg0UL2xvLnhNqB7vba','12344','ACTIVE','2','3'),
       ('Root','2021-05-10 18:53:23.255026','Root','2021-05-10 18:53:23.255042','false','employee@gmail.com','true','employee','employee','$2a$10$Q7ilQ6Hv11qpU0T7xfMzMeqxoPXkvhTVXxFqg0UL2xvLnhNqB7vba','12344','ACTIVE','2','4');

insert into vendor (created_by, created_time, updated_by, updated_time, address, company_name, deleted, email, enabled, phone, state, status, type, zip_code, company_id)
VALUES ('Root','2021-05-10 20:30:38.368133','Root','2021-05-10 20:32:33.578771','6676 Poloym East St','Upgenix','false','upgenix@upgenix.com','true','1233456','TEXAS','ACTIVE','SUPPLIER','99098','2'),
       ('Root','2021-05-10 20:30:38.368133','Root','2021-05-10 20:32:33.578771','6676 Poloym East St','Lexy','false','lexy@lexy.com','true','1233456','TEXAS','ACTIVE','CLIENT','99098','2');

insert into category (created_by, created_time, updated_by, updated_time, category, enabled, company_id)
VALUES ('Root','2021-05-10 21:41:20.602757','Root','2021-05-10 21:41:20.602784','CLOTH','true','2');

insert into product_name (created_by, created_time, updated_by, updated_time, description, enabled, low_limit_alert, product_name, tax, unit, category_id, company_id, profit_product)
VALUES ('Root','2021-05-10 21:42:32.673778','Root','2021-05-10 21:42:32.673801','Casual cloth for Adult','true','2','AIR','10','UNIT','1','2',null);