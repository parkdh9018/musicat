

INSERT INTO authority (authority_name) VALUES ('admin');
INSERT INTO authority (authority_name) VALUES ('user');

INSERT INTO user (user_id, user_nickname, user_profile_image, user_thumbnail_image, user_email, user_created_at, user_money, user_warn_count, user_unread_message, user_is_darkmode, user_is_Chatting_ban, user_is_ban, user_is_user)
VALUES ('admin', 'Admin', 'profile_image_admin.png', 'thumbnail_image_admin.png', 'admin@email.com', NOW(), 0, 0, 0, false, false, false, false);

INSERT INTO user (user_id, user_nickname, user_profile_image, user_thumbnail_image, user_email, user_created_at, user_money, user_warn_count, user_unread_message, user_is_darkmode, user_is_Chatting_ban, user_is_ban, user_is_user)
VALUES ('user', 'User', 'profile_image_user.png', 'thumbnail_image_user.png', 'user@email.com', NOW(), 0, 0, 0, false, false, false, false);



INSERT INTO user_authority (user_seq, authority_name) VALUES (1, 'admin');
INSERT INTO user_authority (user_seq, authority_name) VALUES (1, 'user');
INSERT INTO user_authority (user_seq, authority_name) VALUES (2, 'user');



