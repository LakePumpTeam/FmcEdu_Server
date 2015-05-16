insert into profile(id, name,phone,last_update_date) values(10000,"teacher1","15888888881", '2015-05-16');
insert into profile(id,name,phone,last_update_date) values(10001,"teacher2","15888888882" '2015-05-16');
insert into profile(id,name,phone,last_update_date) values(10002,"teacher3","15888888883" '2015-05-16');
insert into profile(id,name,phone,last_update_date) values(10003,"teacher4","15888888884" '2015-05-16');

insert into teacher(profile_id,school_id,sex,head_teacher, initialized) values(10001,1, 1, 1,1);
insert into teacher(profile_id,school_id,sex,head_teacher, initialized) values(10002,2, 0, 1,1);
insert into teacher(profile_id,school_id,sex,head_teacher, initialized) values(10003,3, 0, 1,1);
insert into teacher(profile_id,school_id,sex,head_teacher, initialized) values(10004,4, 1, 1,0);


insert into teacher_class_map(teacher_id,class_id,sub_title,head_teacher) values(10001,1, "sub_title", 1);
insert into teacher_class_map(teacher_id,class_id,sub_title,head_teacher) values(10002,2, "sub_title", 1);
insert into teacher_class_map(teacher_id,class_id,sub_title,head_teacher) values(10003,3, "sub_title", 1);
insert into teacher_class_map(teacher_id,class_id,sub_title,head_teacher) values(10004,4, "sub_title", 1);