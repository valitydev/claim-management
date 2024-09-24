create table cm.turnover_limits_modification_model
(
    id int8 not null,
    primary key (id)
);

alter table if exists cm.turnover_limits_modification_model
    add constraint shop_modification_model_fkey foreign key (id) references cm.shop_modification_model;

create table cm.turnover_limit_modification_model
(
    id                    int8              not null,
    turnover_limits_id    int8              not null,
    limit_config_id       character varying not null,
    amount_upper_boundary int8              not null,
    domain_revision       int8,
    primary key (id)
);

alter table if exists cm.turnover_limit_modification_model
    add constraint turnover_limits_modification_model_fkey foreign key (turnover_limits_id) references cm.turnover_limits_modification_model;
