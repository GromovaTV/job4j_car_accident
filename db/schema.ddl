CREATE TABLE accident_type (
id serial primary key,
name varchar(2000) not null unique
);

CREATE TABLE accident (
id serial primary key,
name varchar(2000) not null,
text text,
address text,
accident_type_id int not null references type(id)
);

CREATE TABLE rule (
id serial primary key,
name varchar(2000) not null unique
);

CREATE TABLE accident_rule (
id serial primary key,
accident_id int not null references accident(id),
rule_id int not null references rule(id)
);
