CREATE TABLE customer(
    id UUID primary key,
    first_name text not null,
    last_name text not null,
    birth_date date not null,
    nationality text not null,
    type text not null,
    number text not null,
    unique(type, number)
);

CREATE TABLE address(
    id UUID primary key,
    type text not null,
    post_code text not null,
    address text not null,
    number text not null,
    complement text,
    customer_id uuid not null,
    foreign key (customer_id) references customer(id)
);