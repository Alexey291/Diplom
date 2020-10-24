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
insert into posts (id, is_active, moderation_status, title, text, time_post, user_id, view_count)
    values (4, true, 1, 'New post','text', '2020.10.24 17:08:11', 2, 405);
insert into posts (id, is_active, moderation_status, title, text, time_post, user_id, view_count)
    values (5, true, 1, 'New post1','text1', '2020.10.23 17:08:11', 1, 565);
insert into posts (id, is_active, moderation_status, title, text, time_post, user_id, view_count)
    values (6, true, 1, 'New post2','text2', '2020.10.22 14:08:11', 2, 452);
insert into posts (id, is_active, moderation_status, title, text, time_post, user_id, view_count)
    values (7, true, 1, 'New post3','text3', '2020.10.22 14:08:11', 2, 812);
insert into posts (id, is_active, moderation_status, title, text, time_post, user_id, view_count)
    values (8, true, 1, 'New post4','text4', '2020.10.22 14:56:11', 1, 754);
insert into posts (id, is_active, moderation_status, title, text, time_post, user_id, view_count)
    values (9, true, 1, 'New post5','text5', '2020.10.22 14:56:11', 1, 759);
insert into posts (id, is_active, moderation_status, title, text, time_post, user_id, view_count)
    values (10, true, 1, 'New post6','text6', '2020.10.21 12:53:05', 2, 832);
insert into posts (id, is_active, moderation_status, title, text, time_post, user_id, view_count)
    values (11, true, 1, 'New post7','text7', '2020.10.24 12:48:02', 1, 531);
insert into posts (id, is_active, moderation_status, title, text, time_post, user_id, view_count)
    values (12, true, 1, 'New post8','text8', '2020.10.24 14:28:15', 2, 458);
insert into post_comments (id, post_id, text, time, user_id)
    values (1, 1, 'hello!', '12.10.2020', 1);
insert into posts_comments (post_id, comments_id)
    values(1, 1);
insert into users_comments (user_id, comments_id)
    values (1, 1);
insert into users_posts (user_id, posts_id)
    values (1, 1);
insert into post_votes (id, post_id, time_votes, user_id, value)
    values (1, 1, '12.10.2020', 1, 1);
insert into posts_votes(post_id, votes_id)
    values (1, 1);
insert into users_votes(user_id, votes_id)
    values (1, 1);
insert into post_votes (id, post_id, time_votes, user_id, value)
    values (2, 2, '12.10.2020', 1, -1);
insert into posts_votes(post_id, votes_id)
    values (2, 2);
insert into users_votes(user_id, votes_id)
    values (1, 2);
insert into tags (id, name, weight)
    values (1, 'java', 1);
insert into tags (id, name, weight)
    values (2, 'php', 0.5);
insert into tags (id, name, weight)
    values (3, 'c#', 0.8);