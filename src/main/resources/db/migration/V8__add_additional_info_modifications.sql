create table cm.additional_info_modification_model
(
    id              int8 not null,
    party_name      character varying,
    comment         text,
    party_name      text[],
    primary key (id)
);