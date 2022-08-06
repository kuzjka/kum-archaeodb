create sequence hibernate_sequence;

create table items(
  id int primary key,
  year int,
  number int,
  hectare int,
  depth int,
  latitude real,
  longitude real,
  description clob,
  material varchar,
  technique varchar,
  condition varchar,
  dimensions varchar,
  weight int,
  category_id int,
  remarks varchar,
  image varchar(255)
);
create table bullets(id int primary key, deformed boolean, caliber int);
create table categories(id int primary key, name varchar(255));
create table category_filters(id int primary key, categiory_id int, filter varchar (511));