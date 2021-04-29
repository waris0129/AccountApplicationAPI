insert into role(created_by, created_time, updated_by, updated_time, role)
VALUES ('Root','2021-01-05 00:00:00','Root','2021-01-05 00:00:00','Root'),
       ('Root','2021-01-05 00:00:00','Root','2021-01-05 00:00:00','Admin'),
       ('Root','2021-01-05 00:00:00','Root','2021-01-05 00:00:00','Manager'),
       ('Root','2021-01-05 00:00:00','Root','2021-01-05 00:00:00','Employee'),
       ('Root','2021-01-05 00:00:00','Root','2021-01-05 00:00:00','Owner');


insert into company (created_by, created_time, updated_by, updated_time, address1, address2, deleted, email, enabled, establish_date, representative, state, status, title, zip)
values ('Root','2021-03-21 18:12:00.348868','Root','2021-03-21 18:12:00.348895','7900 Brompton st','','false','waris@gmail.com','true','2020-11-01','Waris Nigmat','TEXAS','ACTIVE','Warehouse Management Ltd','778900'),
       ('Root','2021-03-21 18:12:00.348868','Root','2021-03-21 18:12:00.348895','7900 Brompton st','','false','ginawaris@gmail.com','true','2020-11-01','Gina Mardan','TEXAS','ACTIVE','Start Car LLC','778900');


insert into users (created_by, created_time, updated_by, updated_time, deleted, email, enabled, firstname, lastname, password, phone, status, company_id, role_id)
VALUES ('Root','2021-03-21 18:12:00.348868','Root','2021-03-21 18:12:00.348895','false','root@gmail.com','true','root', 'root','$2a$10$Q7ilQ6Hv11qpU0T7xfMzMeqxoPXkvhTVXxFqg0UL2xvLnhNqB7vba','7739461852','ACTIVE','1','1'),
       ('Root','2021-03-21 18:12:00.348868','Root','2021-03-21 18:12:00.348895','false','admin@gmail.com','true','admin', 'admin','$2a$10$Q7ilQ6Hv11qpU0T7xfMzMeqxoPXkvhTVXxFqg0UL2xvLnhNqB7vba','7739461852','ACTIVE','2','2'),
       ('Root','2021-03-21 18:12:00.348868','Root','2021-03-21 18:12:00.348895','false','manager@gmail.com','true','manager', 'manager','$2a$10$Q7ilQ6Hv11qpU0T7xfMzMeqxoPXkvhTVXxFqg0UL2xvLnhNqB7vba','7739461852','ACTIVE','2','3'),
       ('Root','2021-03-21 18:12:00.348868','Root','2021-03-21 18:12:00.348895','false','employee@gmail.com','true','employee', 'employee','$2a$10$Q7ilQ6Hv11qpU0T7xfMzMeqxoPXkvhTVXxFqg0UL2xvLnhNqB7vba','7739461852','ACTIVE','2','4'),
       ('Root','2021-03-21 18:12:00.348868','Root','2021-03-21 18:12:00.348895','false','owner@gmail.com','true','owner', 'owner','$2a$10$Q7ilQ6Hv11qpU0T7xfMzMeqxoPXkvhTVXxFqg0UL2xvLnhNqB7vba','7739461852','ACTIVE','1','5');


insert into category (created_by, created_time, updated_by, updated_time, category, enabled, company_id)
VALUES ('Root','2021-04-12 22:06:44.639383','Root','2021-04-12 22:06:44.639414','TESLA','true','2'),
       ('Root','2021-04-12 22:06:44.639383','Root','2021-04-12 22:06:44.639414','TOYOTA','true','2'),
       ('Root','2021-04-12 22:06:44.639383','Root','2021-04-12 22:06:44.639414','HYUNDAI','true','2'),
       ('Root','2021-04-12 22:06:44.639383','Root','2021-04-12 22:06:44.639414','FORD','true','2');

insert into product_name (created_by, created_time, updated_by, updated_time, description, enabled, low_limit_alert, product_name, tax, unit, category_id, company_id, profit_product)
VALUES ('Root','2021-04-14 19:56:07.047276','Root','2021-04-14 19:56:07.047293','Mid level SUV compact model','true','2','ESCAPE','12','UNIT','4','2',NULL);


insert into vendor (created_by, created_time, updated_by, updated_time, address, company_name, deleted, email, enabled, phone, state, status, type, zip_code, company_id)
VALUES ('Root','2021-04-14 19:55:48.484748','Root','2021-04-14 19:55:48.484776',' ','UpGenix','false','waris0129@hotmail.com','true','773946','TEXAS','ACTIVE','SUPPLIER','77025','2');

insert into invoice1 (created_by, created_time, updated_by, updated_time, enabled, invoice_no, invoice_status, invoice_type, local_date, total_price,totalqty,year, company_id, vendor_id,due_date)
values ('Root','2021-04-14 19:56:14.233848','Root','2021-04-14 19:56:14.256971','true','STAR-2021_PURCHASE_000','PENDING','PURCHASE','2021-04-14',0,0,'2021','2','1','2021-05-14'),
       ('Root','2021-04-14 19:56:14.233848','Root','2021-04-14 19:56:14.256971','true','STAR-2021_SALES_000','PENDING','SALES','2021-04-14',0,0,'2021','2','1','2021-05-14');


-- insert into product_name (created_by, created_time, updated_by, updated_time, description, enabled, low_limit_alert, product_name, tax, unit, category_id, company_id)
-- values ('Admin','2021-03-28 20:42:30.857259','Admin','2021-03-28 20:42:30.857287','Electric car','true','2','TOYOTA','12','UNIT','1','1'),
--        ('Admin','2021-03-28 20:42:30.857259','Admin','2021-03-28 20:42:30.857287','Electric car','true','2','TESLA','12','UNIT','1','1');
--
--
-- insert into invoice1 (created_by, created_time, updated_by, updated_time, enabled, invoice_no, invoice_status, invoice_type, local_date, year, company_id, vendor_id)
-- values ('Admin','2021-03-28 20:44:24.634401','Admin','2021-03-28 20:44:24.677935','true','AAA-2021_PURCHASE_001','PENDING','PURCHASE','2021-03-28','2021','1','1');
--
-- insert into product (created_by, created_time, updated_by, updated_time, available_stock, enabled, inventory_no, price, qty, category_id, company_id, product_register_id)
-- VALUES ('Admin','2021-03-28 20:52:24.437307','Admin','2021-03-28 20:52:24.437336','30','true','AAA_TOYOTA_001','100','30','1','1','1'),
--        ('Admin','2021-03-28 20:52:24.437307','Admin','2021-03-28 20:52:24.437336','30','true','AAA_TOYOTA_002','100','30','1','1','1'),
--        ('Admin','2021-03-28 20:52:24.437307','Admin','2021-03-28 20:52:24.437336','30','true','AAA_TOYOTA_003','100','30','1','1','1'),
--        ('Admin','2021-03-28 20:52:24.437307','Admin','2021-03-28 20:52:24.437336','30','true','AAA_TOYOTA_004','100','30','1','1','1'),
--        ('Admin','2021-03-28 20:52:24.437307','Admin','2021-03-28 20:52:24.437336','30','true','AAA_TESLA_005','100','30','1','1','2'),
--        ('Admin','2021-03-28 20:52:24.437307','Admin','2021-03-28 20:52:24.437336','30','true','AAA_TESLA_006','100','30','1','1','2'),
--        ('Admin','2021-03-28 20:52:24.437307','Admin','2021-03-28 20:52:24.437336','30','true','AAA_TESLA_007','100','30','1','1','2'),
--        ('Admin','2021-03-28 20:52:24.437307','Admin','2021-03-28 20:52:24.437336','30','true','AAA_TESLA_008','100','30','1','1','2');
--
--
-- insert into invoice1_product_list (invoice1_id, product_list_id) values (1,1),
--                                                                         (1,2),
--                                                                         (1,3),
--                                                                         (1,4),
--                                                                         (1,5),
--                                                                         (1,6),
--                                                                         (1,7),
--                                                                         (1,8);

