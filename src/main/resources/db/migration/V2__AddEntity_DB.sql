insert into global_settings (id, code, name, value)
    values (3, '1111', 'example', 'MULTIUSER_MODE:true POST_PREMODERATION:false STATISTICS_IS_PUBLIC:false');
insert into users (id, code, email, is_moderator, name, password, reg_time)
    values (1, '1234', 'leshak549@gmail.com', true, 'Alexey', 'qweasd123', '06.06.2019');
insert into users (id, code, email, is_moderator, name, password, reg_time)
    values (2, '4321', 'noname@gmail.com', false, 'Vlad', '12345', '09.05.2019');
insert into users (id, code, email, is_moderator, name, password, reg_time)
    values (3, '9754', 'example@gmail.com', false, 'Andrey', 'qweasdzxc', '08.05.2020');
insert into users (id, code, email, is_moderator, name, password, reg_time)
    values (4, '1754', 'example1@gmail.com', false, 'Konstantin', '123qweasd', '12.05.2020');
insert into users (id, code, email, is_moderator, name, password, reg_time)
    values (5, '2754', 'example2@gmail.com', false, 'Kirill', '123qweasd123', '14.05.2020');
insert into users (id, code, email, is_moderator, name, password, reg_time)
    values (6, '3754', 'example3@gmail.com', false, 'Petr', '123qweasd', '20.05.2020');
insert into users (id, code, email, is_moderator, name, password, reg_time)
    values (7, '4754', 'example4@gmail.com', false, 'Ivan', '153qweasd793dfa', '22.05.2020');
insert into posts (id, is_active, moderation_status, title, text, time_post, user_id, view_count)
    values (1, true, 'ACCEPTED', 'first post','hello world!', '2020.10.12 08:08:11', 1, 240);
insert into posts (id, is_active, moderation_status, title, text, time_post, user_id, view_count)
    values (2, true, 'ACCEPTED', 'second post','new post', '2020.10.12 08:08:11', 1, 236);
insert into posts (id, is_active, moderation_status, title, text, time_post, user_id, view_count)
    values (3, true, 'ACCEPTED', 'third post','blablablabla', '2020.10.12 08:08:11', 2, 510);
insert into posts (id, is_active, moderation_status, title, text, time_post, user_id, view_count)
    values (4, true, 'ACCEPTED', 'New post','text', '2020.10.24 17:08:11', 2, 405);
insert into posts (id, is_active, moderation_status, title, text, time_post, user_id, view_count)
    values (5, true, 'ACCEPTED', 'New post1','text1', '2020.10.23 17:08:11', 3, 565);
insert into posts (id, is_active, moderation_status, title, text, time_post, user_id, view_count)
    values (15, true, 'NEW', 'New post111','text112', '2020.10.23 17:08:11', 3, 5651);
insert into posts (id, is_active, moderation_status, title, text, time_post, user_id, view_count)
    values (6, true, 'ACCEPTED', 'New post2','text2', '2020.10.22 14:08:11', 3, 452);
insert into posts (id, is_active, moderation_status, title, text, time_post, user_id, view_count)
    values (7, true, 'ACCEPTED', 'New post3','text3', '2020.10.18 14:08:11', 4, 812);
insert into posts (id, is_active, moderation_status, title, text, time_post, user_id, view_count)
    values (8, true, 'ACCEPTED', 'New post4','text4', '2020.10.17 14:56:11', 4, 754);
insert into posts (id, is_active, moderation_status, title, text, time_post, user_id, view_count)
    values (9, true, 'ACCEPTED', 'New post5','text5', '2020.10.16 14:56:11', 5, 759);
insert into posts (id, is_active, moderation_status, title, text, time_post, user_id, view_count)
    values (10, true, 'ACCEPTED', 'New post6','text6', '2020.10.21 12:53:05', 6, 832);
insert into posts (id, is_active, moderation_status, title, text, time_post, user_id, view_count)
    values (11, true, 'ACCEPTED', 'New post7','text7', '2020.10.24 12:48:02', 7, 531);
insert into posts (id, is_active, moderation_status, title, text, time_post, user_id, view_count)
    values (12, true, 'ACCEPTED', 'New post8','text8', '2020.10.24 14:28:15', 7, 712);
insert into posts (id, is_active, moderation_status, title, text, time_post, user_id, view_count)
    values (13, true, 'ACCEPTED', 'New post9','text9', '2020.10.12 13:56:15', 5, 513);
insert into posts (id, is_active, moderation_status, title, text, time_post, user_id, view_count)
    values (14, true, 'ACCEPTED', 'New post10','text10', '2020.10.13 12:58:13', 6, 256);
insert into post_comments (id, post_id, text, time, user_id)
    values (1, 1, 'hello!', '12.10.2020', 1);
insert into posts_comments (post_id, comments_id)
    values(1, 1);
insert into users_comments (user_id, comments_id)
    values (1, 1);
insert into post_comments (id, post_id, text, time, user_id)
    values (2, 1, 'hello!', '15.10.2020', 5);
insert into posts_comments (post_id, comments_id)
    values(1, 2);
insert into users_comments (user_id, comments_id)
    values (5, 2);
insert into users_posts (user_id, posts_id)
    values (1, 1);
insert into post_votes (id, post_id, time_votes, user_id, value)
    values (1, 1, '12.10.2020', 1, 1);
insert into posts_votes(post_id, votes_id)
    values (1, 1);
insert into users_votes(user_id, votes_id)
    values (1, 1);
insert into post_votes (id, post_id, time_votes, user_id, value)
    values (2, 3, '12.10.2020', 4, 1);
insert into posts_votes(post_id, votes_id)
    values (3, 2);
insert into users_votes(user_id, votes_id)
    values (4, 2);
insert into post_votes (id, post_id, time_votes, user_id, value)
    values (3, 3, '12.10.2020', 5, 1);
insert into posts_votes(post_id, votes_id)
    values (3, 3);
insert into users_votes(user_id, votes_id)
    values (5, 3);
insert into post_comments (id, post_id, text, time, user_id)
    values (3, 3, 'cool!', '15.10.2020', 5);
insert into posts_comments (post_id, comments_id)
    values(3, 3);
insert into users_comments (user_id, comments_id)
    values (5, 3);
insert into post_votes (id, post_id, time_votes, user_id, value)
    values (4, 2, '12.10.2020', 1, -1);
insert into posts_votes(post_id, votes_id)
    values (2, 4);
insert into users_votes(user_id, votes_id)
    values (1, 4);
insert into tags (id, name, weight)
    values (1, 'java', 1);
insert into tags (id, name, weight)
    values (2, 'php', 0.5);
insert into tags (id, name, weight)
    values (3, 'c#', 0.8);
insert into tag2post (id, post_id, tag_id)
    values (1, 1, 1);
insert into tag2post (id, post_id, tag_id)
    values (2, 3, 2);
insert into tag2post (id, post_id, tag_id)
    values (3, 5, 3);