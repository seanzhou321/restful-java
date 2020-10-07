drop table if exists donor;

create table donor (
    id int auto_increment primary key,
    first_name varchar(150) not null,
    last_name varchar(100) not null
);

insert into donor (first_name, last_name) values
('Mark', 'Fowler'),
('Jhon', 'Smith'),
('Jennifer', 'Kuala');