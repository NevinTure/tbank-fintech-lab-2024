create table translation(
    id bigint generated always as identity primary key,
    user_addr varchar(15) not null,
    source_lang varchar(50) not null,
    target_lang varchar(50) not null,
    origin_text text,
    translated_text text
)