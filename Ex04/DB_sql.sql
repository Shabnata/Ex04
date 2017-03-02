--Queries became complicated to read, using this site to format them automatically:
--http://www.dpriver.com/pp/sqlformat.htm

--select * from accounts
--select * from books
--select * from book_copies
--select * from categories
--select * from conditions
--select * from library_props
--select * from loaned_books
--select * from loans
--select * from students

--delete from library_props; delete from accounts; delete from loaned_books; delete from loans; delete from book_copies; delete from conditions; delete from books; delete from categories; delete from students;

--drop table library_props; drop table accounts; drop table loaned_books; drop table loans; drop table book_copies; drop table conditions; drop table books; drop table categories; drop table students;

create table students(
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	st_id varchar(9) primary key,
	f_name varchar(255) not null,
	l_name varchar(255) not null,
	email varchar(255) not null,
	fine integer check (fine >=0) not null
);
create table categories(
	id integer GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	cat_name varchar(255) primary key
);
create table books(
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	isbn varchar(13) primary key,
	title varchar(255) not null,
	author varchar(255) not null,
	book_category varchar(255) references categories(cat_name) on delete restrict not null,
	p_year date not null,
	cover varchar(255) not null,
	copy_cnt integer check (copy_cnt >=0) not null
);
create table conditions(
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	con_key integer check (con_key >=0 ) primary key,
	con_desc varchar(255) not null
);
create table book_copies(
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	isbn varchar(13) references books(isbn) on delete cascade not null,
	copy_code varchar(255) primary key,
	copy_cond integer references conditions(con_key) not null
);
create table loans(
	loan_id integer primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	st_id varchar(9) references students(st_id) on delete restrict not null,
	start_d date not null,
	ret_d date not null,
	constraint chk_d check (ret_d >= start_d)
);
create table loaned_books(
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	loan_id integer references loans(loan_id) on delete restrict not null,
	copy_code varchar(255) references book_copies(copy_code) on delete cascade not null,
	returned boolean default false,
	days_over integer default 0,
	constraint chk_days check((returned = true AND days_over >= 0) OR (returned = false AND days_over = 0)),
	primary key(loan_id, copy_code)
);
create table accounts(
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	acc_id varchar(9) primary key,
	acc_pass varchar(255) not null,
	acc_type varchar(255) check(acc_type ='admin' OR acc_type='user' ) not null
);
create table library_props(
	lib_name varchar(255) primary key,
	fine_per_day integer check(fine_per_day >= 0) not null,
	max_fines_per_student integer check(max_fines_per_student >= 0) not null,
	max_books_per_student integer check(max_books_per_student >= 0) not null
);

insert into library_props (lib_name, fine_per_day, max_fines_per_student, max_books_per_student) values('Library', 1, 10, 10);

insert into students (st_id, f_name, l_name, email, fine) values('123456789','Noah', 'Smith' , 'Noah@gmail.com@efg', 50);
insert into students (st_id, f_name, l_name, email, fine) values('234567891','Emma', 'Williams' , 'Emma@gmail.com', 0);
insert into students (st_id, f_name, l_name, email, fine) values('345678912','Liam', 'Johnson' , 'Liam@gmail.com', 0);
insert into students (st_id, f_name, l_name, email, fine) values('456789123','Daniel', 'Brown' , 'Daniel@gmail.com', 0);
insert into students (st_id, f_name, l_name, email, fine) values('567891234','Oliver', 'Davis' , 'Oliver@gmail.com', 0);
insert into students (st_id, f_name, l_name, email, fine) values('678912345','Zoey', 'Moore' , 'Zoey@gmail.com', 0);
insert into students (st_id, f_name, l_name, email, fine) values('789123456','Ellie', 'Wilson' , 'Ellie@gmail.com', 0);
insert into students (st_id, f_name, l_name, email, fine) values('891234567','Caleb', 'Taylor' , 'Caleb@gmail.com', 0);
insert into students (st_id, f_name, l_name, email, fine) values('912345678','Aria', 'Thomas' , 'Aria@gmail.com', 0);
insert into students (st_id, f_name, l_name, email, fine) values('000045678','Piper', 'Clark' , 'Piper@gmail.com', 0);


insert into categories (cat_name) values('Cooking');
insert into categories (cat_name) values('Comedy');
insert into categories (cat_name) values('Drama');
insert into categories (cat_name) values('Fiction');
insert into categories (cat_name) values('Thriller');


insert into books (isbn, title, author, book_category, p_year, cover, copy_cnt) values('0000307949486', 'The Girl with the Dragon Tattoo', 'Stieg Larsson', 'Thriller','2011-01-01', 'https://images-na.ssl-images-amazon.com/images/I/51d3vkCK7WL._SX277_BO1,204,203,200_.jpg', 6);
insert into books (isbn, title, author, book_category, p_year, cover, copy_cnt) values('0000307742539','The Girl Who Kicked the Hornet''s Nest', 'Stieg Larsson' , 'Thriller','2012-01-01','https://images-na.ssl-images-amazon.com/images/I/51hb5Jyc1WL._SX279_BO1,204,203,200_.jpg', 5);
insert into books (isbn, title, author, book_category, p_year, cover, copy_cnt) values('0000307949508','The Girl Who Played with Fire', 'Stieg Larsson', 'Thriller','2009-01-01','https://images-na.ssl-images-amazon.com/images/I/51dXFW6IkfL._SX280_BO1,204,203,200_.jpg', 3);
insert into books (isbn, title, author, book_category, p_year, cover, copy_cnt) values('0001477820019','Take Me With You', 'Catherine Ryan Hyde', 'Drama', '2014-01-01','https://images-na.ssl-images-amazon.com/images/I/51rA55ywNmL._SX332_BO1,204,203,200_.jpg', 2);
insert into books (isbn, title, author, book_category, p_year, cover, copy_cnt) values('0000956998593','The Best Friend', 'Shalini Boland', 'Fiction', '2016-01-01','https://images-na.ssl-images-amazon.com/images/I/51ZlkjKcNTL._SX331_BO1,204,203,200_.jpg', 3);
insert into books (isbn, title, author, book_category, p_year, cover, copy_cnt) values('0001786810328','The Killing Game', 'J.S. Carol', 'Comedy', '2013-01-01','https://images-na.ssl-images-amazon.com/images/I/51CQGc0nubL._SX322_BO1,204,203,200_.jpg', 3);
insert into books (isbn, title, author, book_category, p_year, cover, copy_cnt) values('0000451495969','Cravings', 'Chrissy Teigen', 'Cooking', '2016-01-01','https://images-na.ssl-images-amazon.com/images/I/51HGuoYiOZL._SX379_BO1,204,203,200_.jpg', 3);
insert into books (isbn, title, author, book_category, p_year, cover, copy_cnt) values('0000316221899','The Superfun Times Vegan Holiday Cookbook', 'Isa Chandra Moskowitz', 'Cooking', '2016-01-01','https://images-na.ssl-images-amazon.com/images/I/61K42L7CNgL._SX400_BO1,204,203,200_.jpg', 2);
insert into books (isbn, title, author, book_category, p_year, cover, copy_cnt) values('0000753459019','The Best Book of Bugs', 'Claire Llewellyn', 'Comedy', '2015-01-01','https://images-na.ssl-images-amazon.com/images/I/51KvdbVDkDL._SX422_BO1,204,203,200_.jpg', 3);
insert into books (isbn, title, author, book_category, p_year, cover, copy_cnt) values('0001623363586','Thug Kitchen', 'Thug Kitchen', 'Cooking', '2014-01-01','https://images-na.ssl-images-amazon.com/images/I/51v1Q88aB0L._SX404_BO1,204,203,200_.jpg', 3);


insert into conditions (con_key, con_desc) values(1,'AsNew');
insert into conditions (con_key, con_desc) values(2,'SlightlyUsed');
insert into conditions (con_key, con_desc) values(3,'VeryUsed');
insert into conditions (con_key, con_desc) values(4,'Damaged');
insert into conditions (con_key, con_desc) values(5,'Defective');


insert into book_copies (isbn, copy_code, copy_cond) values('0000307949486', '0000307949486_001', 1);
insert into book_copies (isbn, copy_code, copy_cond) values('0000307949486', '0000307949486_002', 1);
insert into book_copies (isbn, copy_code, copy_cond) values('0000307949486', '0000307949486_003', 1);
insert into book_copies (isbn, copy_code, copy_cond) values('0000307949486', '0000307949486_004', 1);
insert into book_copies (isbn, copy_code, copy_cond) values('0000307949486', '0000307949486_005', 1);
insert into book_copies (isbn, copy_code, copy_cond) values('0000307949486', '0000307949486_006', 1);

insert into book_copies (isbn, copy_code, copy_cond) values('0000307742539', '0000307742539_001', 1);
insert into book_copies (isbn, copy_code, copy_cond) values('0000307742539', '0000307742539_002', 1);
insert into book_copies (isbn, copy_code, copy_cond) values('0000307742539', '0000307742539_003', 1);
insert into book_copies (isbn, copy_code, copy_cond) values('0000307742539', '0000307742539_004', 1);
insert into book_copies (isbn, copy_code, copy_cond) values('0000307742539', '0000307742539_005', 1);

insert into book_copies (isbn, copy_code, copy_cond) values('0000307949508', '0000307949508_001', 1);
insert into book_copies (isbn, copy_code, copy_cond) values('0000307949508', '0000307949508_002', 1);
insert into book_copies (isbn, copy_code, copy_cond) values('0000307949508', '0000307949508_003', 1);

insert into book_copies (isbn, copy_code, copy_cond) values('0001477820019', '0001477820019_001', 1);
insert into book_copies (isbn, copy_code, copy_cond) values('0001477820019', '0001477820019_002', 1);

insert into book_copies (isbn, copy_code, copy_cond) values('0000956998593', '0000956998593_001', 1);
insert into book_copies (isbn, copy_code, copy_cond) values('0000956998593', '0000956998593_002', 1);
insert into book_copies (isbn, copy_code, copy_cond) values('0000956998593', '0000956998593_003', 1);

insert into book_copies (isbn, copy_code, copy_cond) values('0001786810328', '0001786810328_001', 1);
insert into book_copies (isbn, copy_code, copy_cond) values('0001786810328', '0001786810328_002', 1);
insert into book_copies (isbn, copy_code, copy_cond) values('0001786810328', '0001786810328_003', 1);

insert into book_copies (isbn, copy_code, copy_cond) values('0000451495969', '0000451495969_001', 1);
insert into book_copies (isbn, copy_code, copy_cond) values('0000451495969', '0000451495969_002', 1);
insert into book_copies (isbn, copy_code, copy_cond) values('0000451495969', '0000451495969_003', 1);

insert into book_copies (isbn, copy_code, copy_cond) values('0000316221899', '0000316221899_001', 1);
insert into book_copies (isbn, copy_code, copy_cond) values('0000316221899', '0000316221899_002', 1);

insert into book_copies (isbn, copy_code, copy_cond) values('0000753459019', '0000753459019_001', 1);
insert into book_copies (isbn, copy_code, copy_cond) values('0000753459019', '0000753459019_002', 1);
insert into book_copies (isbn, copy_code, copy_cond) values('0000753459019', '0000753459019_003', 1);

insert into book_copies (isbn, copy_code, copy_cond) values('0001623363586', '0001623363586_001', 1);
insert into book_copies (isbn, copy_code, copy_cond) values('0001623363586', '0001623363586_002', 1);
insert into book_copies (isbn, copy_code, copy_cond) values('0001623363586', '0001623363586_003', 1);

insert into accounts (acc_id, acc_pass, acc_type) values('123456789','123456789','user');
insert into accounts (acc_id, acc_pass, acc_type) values('admin','admin','admin');

INSERT INTO LOANS (ST_ID, START_D, RET_D) VALUES ('123456789', '2017-02-01', '2017-02-05');
INSERT INTO LOANS (ST_ID, START_D, RET_D) VALUES ('123456789', '2017-02-02', '2017-02-09');

-- Using static loan_id causes errors if the tables are not dropped and recreated first.
INSERT INTO LOANED_BOOKS (LOAN_ID, COPY_CODE, RETURNED, DAYS_OVER) VALUES (1, '0000307949486_001', DEFAULT, DEFAULT);
INSERT INTO LOANED_BOOKS (LOAN_ID, COPY_CODE, RETURNED, DAYS_OVER) VALUES (1, '0000307742539_001', DEFAULT, DEFAULT);
INSERT INTO LOANED_BOOKS (LOAN_ID, COPY_CODE, RETURNED, DAYS_OVER) VALUES (2, '0000307949508_001', DEFAULT, DEFAULT);
INSERT INTO LOANED_BOOKS (LOAN_ID, COPY_CODE, RETURNED, DAYS_OVER) VALUES (2, '0001477820019_001', true, DEFAULT);
