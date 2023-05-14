create sequence hibernate_sequence start with 100;

create table items
(
    id            int primary key,
    item_type     varchar(1),
    name          varchar,
    year_         int,
    number        int not null,
    sub_number    int,
    hectare       int,
    depth         int,
    latitude      real,
    longitude     real,
    description   clob,
    material      varchar,
    technique     varchar,
    condition     varchar,
    dimensions    varchar,
    weight        real,
    category_id   int,
    remarks       varchar,
    context       int,
    image         varchar(255),
    museum_number varchar(255),
    gps_point     varchar(31),

    -- Bullet columns
    caliber       int,
    approximate   boolean,
    standard      varchar(31),
    deformed      varchar(15),
    imprints      boolean,

    -- Constraints
    unique (number, sub_number)
);

create table categories
(
    id   int primary key,
    name varchar(255) unique
);

create table category_filters
(
    category_id int references categories,
    filter      varchar(511)
);

-- Built-in bullets category
insert into categories(id, name)
values (1, 'Кулі');

insert into category_filters (category_id, filter)
values (1, 'куля');
