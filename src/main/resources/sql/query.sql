drop database if exists hardware;
    create database if not exists hardware;


use hardware;

create table users(
                     userId varchar(25),
                     password varchar(10),
                     email varchar(50),
                     tel varchar(10)
);
insert into users values ('sanki','123',"sanki@gmail.com","0741252528");
create table customers(
                          c_id int auto_increment primary key,
                          name varchar(25) not null ,
                          address varchar(50) not null ,
                          tel varchar(10) not null ,
                          email varchar(50) not null
);

insert into customers (name, address, tel, email)
values
("Dinidu","Horana","0741255458","dinidu@gmail.com"),
("sanki","Kaluthara","0741255558","sanki@gmail.com"),
("amali","Mathugama","0741255778","amali@gmail.com"),
("kasun","Kaluthara","0741244458","kasun@gmail.com"),
("damith","kandy","0746655558","damith@gmail.com");

create table suppliers(
                          s_id int auto_increment primary key,
                          name varchar(25) not null ,
                          company varchar(50) not null ,
                          tel varchar(10) not null ,
                          email varchar(50) not null
);
insert into suppliers (name, company, tel, email)
values
("supun","sunil(plc)","0112457834","suniplc@gmail.com"),
("amal","perera(plc)","0112667834","pereraplc@gmail.com"),
("nihal","nihal(plc)","0112666634","nihalplc@gmail.com"),
("kumari","kumari(plc)","0144667834","kumariplc@gmail.com"),
("waruna","waruna(plc)","0112667664","warunaplc@gmail.com");

create table items(
                      i_id int auto_increment primary key,
                      s_id int,
                      foreign key(s_id) references suppliers(s_id) on update cascade on delete cascade,
                      description varchar(25) not null ,
                      unit_price decimal not null ,
                      qty_on_hand int not null
);
insert into items (s_id,description, unit_price,  qty_on_hand)
values
(1,"paint",100.00,140),
(2,"spray gun",90.00,160),
(3,"brass screws",50.00,100),
(4,"concreat nails",55.00,300),
(5,"cement",55.00,50),
(4,"drywall tape",55.00,55),
(2,"hardwood",400.00,60),
(1,"hex bolts",81.00,50),
(1,"switches",94.00,10),
(1,"cables",95.00,110),
(1,"flashlights",78.00,60),
(2,"exterior paint",66.00,50),
(3,"Caulk",550.00,40),
(4,"Duct tape",150.00,50),
(5,"Shelving units",250.00,70),
(5,"brackets",600.00,40),
(4,"Safety glasses",40.00,80),
(2,"picture hangers",500.00,50),
(1,"Motor oil",65.00,100),
(2,"drawer locks",900.00,70);


create table drivers(
                        d_id int auto_increment primary key,
                        name varchar(25) not null ,
                        tel varchar(10) not null ,
                        email varchar(50) not null,
                        work_time varchar(50) not null
);
insert into drivers (name, tel, email, work_time)
values
("amil","0112227834","amil@gmail.com","8h"),
("thilan","0116227834","thilan@gmail.com","6h"),
("roshan","0116727834","roshan@gmail.com","7h"),
("wijey","0116237834","wijey@gmail.com","7h"),
("sunimal","0114227834","sunimal@gmail.com","7h");


create table employees(
                          e_id int auto_increment primary key,
                          name varchar(20) not null ,
                          address varchar(20) not null ,
                          tel varchar(10) not null ,
                          email varchar(50) not null,
                          salary decimal not null ,
                          c_id int ,
                          foreign key(c_id) references customers(c_id) on update cascade on delete cascade
);
insert into employees (name, address, tel, email, salary, c_id)
values
("kasun","gonapala",0116227834,"kasun@gmail.com",80000.00,2),
("prasad","gonapala",0116221134,"prasad@gmail.com",25000.00,2),
("gagni","pokunuwita",0116227334,"gagani@gmail.com",25000.00,3),
("bineth","wekada",0116227224,"bineth@gmail.com",25000.00,1),
("tharushika","horana",0116227774,"tharushika@gmail.com",50000.00,1);


create table transports(
                           t_id int auto_increment primary key,
                           description varchar(50) not null ,
                           d_id int ,
                           foreign key(d_id) references drivers(d_id) on update cascade on delete cascade
);
insert into transports (description, d_id)
values
("home improvement item",1),
("home improvement item",1),
("building material",3),
("building material",3),
("home improvement item",5);



create table orders(
                       o_id int auto_increment primary key,
                       date date not null ,
                       c_id int,
                       foreign key(c_id) references customers(c_id) on update cascade on delete cascade

);
insert into orders (date,c_id)
values
('2024-05-01',2),
('2024-05-05',1),
('2024-05-08',3),
('2024-05-12',4),
('2024-05-18',5);


create table order_detail(
                             o_id int,
                             foreign key(o_id) references orders(o_id) on update cascade on delete cascade,
                             i_id int,
                             foreign key(i_id) references items(i_id) on update cascade on delete cascade,
                             qty int not null ,
                             unit_price decimal not null
);
insert into order_detail (o_id,i_id,qty,unit_price)
values
    (1,2,10,12.00),
    (2,2,20,12.00),
    (3,1,5,120.00),
    (4,3,100,102.00),
    (5,2,10,12.00);

/*
create table payments(A
                         p_id varchar(5) primary key,
                         discount int not null ,
                         total_price double not null ,
                         date int not null ,
                         o_id varchar(5),
                         foreign key(o_id) references orders(o_id) on update cascade on delete cascade
);




create table get_total(
    select sum(qty * unit_price) as total from order_detail;
);


create table supplier_item_detail(
                                     o_purchase_price double not null ,
                                     o_purchase_item_count int not null ,
                                     o_date int not null ,
                                     s_id varchar(5),
                                     foreign key(s_id) references suppliers(s_id) on update cascade on delete cascade,
                                     i_id varchar(5),
                                     foreign key(i_id) references items(i_id) on update cascade on delete cascade
);

 */
