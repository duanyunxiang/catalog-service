drop table if exists book;
create table book(
    id bigserial primary key not null,
    isbn varchar(255) unique not null,
    title varchar(255) not null,
    author varchar(255) not null,
    price float8 not null,
    create_date timestamp not null,
    last_modified_date timestamp not null,
    version integer not null,
    -- Testcontainers会使用该建表语句，初始化test用容器数据库
    publisher varchar(255)
);
