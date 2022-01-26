
create table cm.wallet_modification_model
(
    id      int8              not null,
    wallet_id character varying not null,
    primary key (id)
);

alter table if exists cm.wallet_modification_model
    add constraint modification_model_fkey foreign key (id) references cm.modification_model;

create table cm.wallet_creation_modification_model
(
    id             int8 not null,
    wallet_params_id int8 not null,
    primary key (id)
);

alter table if exists cm.wallet_creation_modification_model
    add constraint wallet_modification_model_fkey foreign key (id) references cm.wallet_modification_model;

create table cm.wallet_account_creation_modification_model
(
    id                     int8 not null,
    wallet_account_params_id int8 not null,
    primary key (id)
);

alter table if exists cm.wallet_account_creation_modification_model
    add constraint wallet_modification_model_fkey foreign key (id) references cm.wallet_modification_model;

create table cm.wallet_params_model
(
    id               int8              not null,
    contract_id      character varying not null,
    name             character varying,
    primary key (id)
);

alter table if exists cm.wallet_creation_modification_model
    add constraint wallet_params_model_fkey foreign key (wallet_params_id) references cm.wallet_params_model;

create table cm.wallet_account_params_model
(
    id                     int8              not null,
    currency_symbolic_code character varying not null,
    primary key (id)
);

alter table if exists cm.wallet_account_creation_modification_model
    add constraint wallet_account_params_model_fkey foreign key (wallet_account_params_id) references cm.wallet_account_params_model;
