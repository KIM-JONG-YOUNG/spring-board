create table if not exists tb_role (
   	role_no bigint not null auto_increment,
    created_date datetime(6) not null,
    state integer not null,
    updated_date datetime(6) not null,
    role_access_method varchar(255) not null,
    role_access_url_pattern varchar(255) not null,
    role_name varchar(255) not null,
    primary key (role_no),
    unique(role_name)
);