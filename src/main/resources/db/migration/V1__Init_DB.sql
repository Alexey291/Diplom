create table captcha_code (
    id integer not null auto_increment,
    code varchar(15000),
    secret_code varchar(255),
    time datetime,
    primary key (id));
create table global_settings (
    id integer not null auto_increment,
    code varchar(255),
    name varchar(255),
    value varchar(255),
    primary key (id));
create table post_comments (
    id integer not null auto_increment,
    post_id integer not null,
    text varchar(255),
    time datetime,
    user_id integer not null,
    primary key (id));
create table post_votes (
    id integer not null auto_increment,
    post_id integer not null,
    time_votes datetime,
    user_id integer not null,
    value tinyint not null,
    primary key (id));
create table posts (
    id integer not null auto_increment,
    is_active bit,
    moderation_status enum('ACCEPTED','NEW','DECLINED') NOT NULL DEFAULT 'NEW',
    title varchar(255) not null,
    text varchar(255),
    time_post datetime,
    user_id integer not null,
    view_count integer, primary key (id));
create table posts_comments (
    post_id integer not null,
    comments_id integer not null);
create table posts_votes (
    post_id integer not null,
    votes_id integer not null);
create table tag2post (
    id integer not null auto_increment,
    post_id integer not null,
    tag_id integer not null,
    primary key (id));
create table tags (
    id integer not null auto_increment,
    name varchar(255),
    weight double not null,
    primary key (id));
create table users (
    id integer not null auto_increment,
    code varchar(255),
    email varchar(255),
    is_moderator bit,
    name varchar(255),
    password varchar(255),
    photo varchar(255),
    reg_time datetime,
    primary key (id));
create table users_comments (
    user_id integer not null,
    comments_id integer not null);
create table users_posts (
    user_id integer not null,
    posts_id integer not null);
create table users_votes (
    user_id integer not null,
    votes_id integer not null);
alter table posts_comments
    add constraint UK_sjeadiuvloecnqe9psjjdcjqr
    unique (comments_id);
alter table posts_votes
    add constraint UK_crm5sclc3yfcpml4nq2s14taf
    unique (votes_id);
alter table users_comments
    add constraint UK_8a9ff54pt2w205r0hlcbe8mm6
    unique (comments_id);
alter table users_posts
    add constraint UK_4nsbfp7tf7f9rlw76oop43w15
    unique (posts_id);
alter table users_votes
    add constraint UK_icymrcdiuew3ps04v3bsln8bl
    unique (votes_id);
alter table post_comments
    add constraint FKaawaqxjs3br8dw5v90w7uu514
    foreign key (post_id)
    references posts (id);
alter table post_comments
    add constraint FKsnxoecngu89u3fh4wdrgf0f2g
    foreign key (user_id)
    references users (id);
alter table post_votes
    add constraint FK9jh5u17tmu1g7xnlxa77ilo3u
    foreign key (post_id)
    references posts (id);
alter table post_votes
    add constraint FK9q09ho9p8fmo6rcysnci8rocc
    foreign key (user_id)
    references users (id);
alter table posts
    add constraint FK5lidm6cqbc7u4xhqpxm898qme
    foreign key (user_id)
    references users (id);
alter table posts_comments
    add constraint FKmgq9w74kt015s3woxt49dek5j
    foreign key (comments_id)
    references post_comments (id);
alter table posts_comments
    add constraint FKbjdq8a62c5siv1mk27umswg9
    foreign key (post_id)
    references posts (id);
alter table posts_votes
    add constraint FK4ki1xa8v05b0app2yqsnaep0q
    foreign key (votes_id)
    references post_votes (id);
alter table posts_votes
    add constraint FKlrfkxx7a139tfhmb9ukur8agx
    foreign key (post_id)
    references posts (id);
alter table users_comments
    add constraint FK24dwlgdyrty9y29mubl230wfy
    foreign key (comments_id)
    references post_comments (id);
alter table users_comments
    add constraint FKlpp3854cd31i91y1pc26727p6
    foreign key (user_id)
    references users (id);
alter table users_posts
    add constraint FK5onvreh8s1tjdipg18df7fe6r
    foreign key (posts_id)
    references posts (id);
alter table users_posts
    add constraint FKaf5ya0qw7hle6m94xv6th02q7
    foreign key (user_id)
    references users (id);
alter table users_votes
    add constraint FKpabl0j3ii6pgs2oqns5vh5rda
    foreign key (votes_id)
    references post_votes (id);
alter table users_votes
    add constraint FKccci0x8j7aptav6h9val4wvr8
    foreign key (user_id)
    references users (id);