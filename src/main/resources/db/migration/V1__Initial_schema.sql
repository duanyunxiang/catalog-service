-- 使用flyway管理数据库，文件命名格式有固定要求
-- flyway会自动创建flyway_schema_history表；修改该文件会使flyway版本控制校验失败

create table book(
    id bigserial primary key not null,
    isbn varchar(255) unique not null,
    title varchar(255) not null,
    author varchar(255) not null,
    price float8 not null,
    create_date timestamp not null,
    last_modified_date timestamp not null,
    version integer not null
);
