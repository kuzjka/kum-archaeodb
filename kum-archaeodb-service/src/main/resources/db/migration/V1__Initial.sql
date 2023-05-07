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
insert into categories(id, name)
values (1, 'Кулі');
insert into categories(id, name)
values (2, 'Спорядження коней');
insert into categories(id, name)
values (3, 'Вогнепальна зброя');
insert into categories (id, name) values (4, 'Артилерійський боєприпас');
insert into categories(id, name) values (5, 'Монети');
insert into category_filters (category_id, filter)
values (1, 'Куля');
insert into category_filters (category_id, filter)
values (2, 'Вухналь, Підкова');
insert into category_filters (category_id, filter)
values (3, 'Мушкет');

insert into category_filters(category_id, filter)values (4, 'Сіканець');
insert into category_filters(category_id, filter)values (5, 'монета');

insert into items(id, item_type, name, year_, number, sub_number, latitude,
                  longitude, dimensions, weight, category_id, remarks, gps_point, caliber)
values (6, 'I', 'Куля свинцева з хвостиком', 2021, 1, 0, 12.345, 23.456, '17х17х20', 26, 1, 'Маршрут 1', '591', 17);
insert into items(id, item_type, name, year_, number, sub_number, latitude,
                  longitude, dimensions, category_id, remarks)
values (7, 'I', 'Вухналь залізний', 2021, 103, 0, 34.456, 11.223334, 'довж - 30; шир макс - 8', 2,
        'Вільний пошук, Південний сектор');
insert into items(id, item_type, name, year_, number, sub_number, latitude,
                  longitude, dimensions, category_id, remarks, gps_point)
values (8, 'I', 'Сіканець залізний', 2021, 134, 0, 11.543, 33.3214, '52х35х22', 4, 'Вільний пошук, Трикутний ліс', '93');
insert into items(id, item_type, name, year_, number, sub_number, latitude,
                  longitude, category_id, remarks, gps_point)
values (9, 'I', 'Монета-солід, гаманець №1', 2021, 362, 1, 22.3333, 33.4444, 5, 'Верх верху','23');