create table order_positions
(
    id         varchar(255) not null,
    order_id   varchar(36)  not null,
    product_id varchar(36)  not null,
    quantity   bigint       not null
);


create table orders
(
    id          varchar(36)  not null,
    customer_id varchar(36)  not null,
    order_time  timestamp    not null,
    status      varchar(255) not null,
    primary key (id)
);



alter table order_positions
    add constraint order_positions_order_id_fk
        foreign key (order_id)
            references orders;


alter table order_positions
    add constraint order_positions_product_id_fk
        foreign key (product_id)
            references products;


alter table orders
    add constraint orders_customer_id_fk
        foreign key (customer_id)
            references customers;
