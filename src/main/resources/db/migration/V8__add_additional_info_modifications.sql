create table cm.additional_info_modification_model
(
    id                          int8 not null,
    party_name                  character varying,
    comment                     text,
    manager_contact_emails      text[],
    primary key (id)
);