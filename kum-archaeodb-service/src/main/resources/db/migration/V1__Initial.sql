create sequence hibernate_sequence;

create table items(
  id int primary key,
  item_type varchar(1),
  name varchar,
  year_ int,
  number int not null,
  sub_number int,
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
  image varchar(255),

  -- Bullet columns
  deformed boolean,
  caliber int,

  -- Constraints
  unique (number, sub_number)
);
create table categories(id int primary key, name varchar(255) unique);
create table category_filters(category_id int references categories, filter varchar (511));