insert into global_settings (id, code, name, value)
    values (3, '1111', 'example', 'MULTIUSER_MODE:true POST_PREMODERATION:false STATISTICS_IS_PUBLIC:false');
insert into users (id, code, email, is_moderator, name, password, reg_time)
    values (1, '1234', 'leshak549@gmail.com', true, 'Alexey', 'qweasd123', '06.06.2019');
insert into users (id, code, email, is_moderator, name, password, reg_time)
    values (2, '4321', 'noname@gmail.com', false, 'Vlad', '12345', '09.05.2019');
insert into posts (id, is_active, moderation_status, title, text, time_post, user_id, view_count)
    values (1, true, 1, 'first post','hello world!', '2020.10.12 08:08:11', 1, 240);
insert into posts (id, is_active, moderation_status, title, text, time_post, user_id, view_count)
    values (2, true, 1, 'second post','new post', '2020.10.12 08:08:11', 1, 236);
insert into posts (id, is_active, moderation_status, title, text, time_post, user_id, view_count)
    values (3, true, 1, 'third post','blablablabla', '2020.10.12 08:08:11', 2, 510);
insert into post_comments (id, post_id, text, time, user_id)
    values (1, 1, 'hello!', '12.10.2020', 1);
insert into post_votes (id, post_id, time_votes, user_id, value)
    values (1, 1, '12.10.2020', 1, 1);
insert into post_votes (id, post_id, time_votes, user_id, value)
    values (2, 2, '12.10.2020', 1, -1);
insert into tags (id, name, weight)
    values (1, 'java', 1);
insert into tags (id, name, weight)
    values (2, 'php', 0.5);
insert into tags (id, name, weight)
    values (3, 'c#', 0.8);