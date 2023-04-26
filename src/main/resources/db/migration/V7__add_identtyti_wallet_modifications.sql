create table cm.identity_modification_model
(
    id      int8              not null,
    identity_id character varying not null,
    primary key (id)
);

alter table if exists cm.identity_modification_model
    add constraint modification_model_fkey foreign key (id) references cm.modification_model;


create table cm.identity_creation_modification_model
(
    id             int8 not null,
    identity_params_id int8 not null,
    primary key (id)
);

alter table if exists cm.identity_creation_modification_model
    add constraint identity_modification_model_fkey foreign key (id) references cm.identity_modification_model;

create table cm.identity_params_model
(
    id               int8              not null,
    party_id         character varying not null,
    name             character varying not null,
    provider         character varying not null,
    metadata         character varying,
    primary key (id)
);

alter table if exists cm.identity_creation_modification_model
    add constraint identity_params_model_fkey foreign key (identity_params_id) references cm.identity_params_model;

create table cm.new_wallet_modification_model
(
    id      int8              not null,
    new_wallet_id character varying not null,
    primary key (id)
);

alter table if exists cm.new_wallet_modification_model
    add constraint modification_model_fkey foreign key (id) references cm.modification_model;

create table cm.new_wallet_creation_modification_model
(
    id             int8 not null,
    new_wallet_params_id int8 not null,
    primary key (id)
);

alter table if exists cm.new_wallet_creation_modification_model
    add constraint new_wallet_modification_model_fkey foreign key (id) references cm.new_wallet_modification_model;

create table cm.new_wallet_params_model
(
    id               int8              not null,
    identity_id      character varying not null,
    name             character varying not null,
    currency         character varying not null,
    metadata         character varying,
    primary key (id)
);

alter table if exists cm.new_wallet_creation_modification_model
    add constraint new_wallet_params_model_fkey foreign key (new_wallet_params_id) references cm.new_wallet_params_model;
