insert into categories(id, name)
values (2, 'Спорядження коней'),
--     (3, 'Вогнепальна зброя'),
       (4, 'Артилерійський боєприпас'),
       (5, 'Монети');

insert into category_filters (category_id, filter)
values (2, 'вухнал'),
       (2, 'підков'),
--     (3, 'мушкет'),
       (4, 'сікан'),
       (5, 'монет');

insert into items(id, item_type, name, year_, number, sub_number, latitude,
                  longitude, dimensions, weight, category_id, remarks, gps_point,
                  caliber, approximate, deformed, imprints)
values (6, 'B', 'Куля свинцева з хвостиком', 2021, 1, 0, 12.345, 23.456, '17х17х20', 26, 1, 'Маршрут 1', '591',
        17, false, 'NONE', false);
insert into items(id, item_type, name, year_, number, sub_number, latitude,
                  longitude, dimensions, category_id, remarks)
values (7, 'I', 'Вухналь залізний', 2021, 103, 0, 34.456, 11.223334, 'довж - 30; шир макс - 8', 2,
        'Вільний пошук, Південний сектор');
insert into items(id, item_type, name, year_, number, sub_number, latitude,
                  longitude, dimensions, category_id, remarks, gps_point)
values (8, 'I', 'Сіканець залізний', 2021, 134, 0, 11.543, 33.3214, '52х35х22', 4, 'Вільний пошук, Трикутний ліс',
        '93');
insert into items(id, item_type, name, year_, number, sub_number, latitude,
                  longitude, category_id, remarks, gps_point)
values (9, 'I', 'Монета-солід, гаманець №1', 2021, 362, 1, 22.3333, 33.4444, 5, 'Верх верху', '23');
