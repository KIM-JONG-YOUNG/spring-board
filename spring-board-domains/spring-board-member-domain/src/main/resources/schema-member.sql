create table if not exists tb_member (
    member_no bigint not null auto_increment,
    created_date timestamp not null,
    state integer not null,
    updated_date timestamp not null,
    member_email varchar(60) not null,
    member_gender char(1) not null,
    member_name varchar(30) not null,
    member_password varchar(60) not null,
    member_username varchar(30) not null,
    primary key (member_no),
    unique(member_username)
);