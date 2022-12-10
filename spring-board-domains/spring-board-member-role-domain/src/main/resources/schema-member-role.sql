create table if not exists tb_member_role (
   	member_no bigint not null,
    role_no bigint not null,
    created_date timestamp not null,
    state integer not null,
    updated_date timestamp not null,
    primary key (member_no, role_no),
    foreign key(member_no) 
    	references tb_member(member_no) on delete cascade,
    foreign key(role_no) 
    	references tb_role(role_no) on delete cascade
);