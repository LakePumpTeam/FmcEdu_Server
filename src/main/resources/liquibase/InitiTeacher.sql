INSERT INTO profile(id,name,phone,last_update_date) values(10000,"teacher1","15888888881",'2015-05-16');
INSERT INTO profile(id,name,phone,last_update_date) values(10001,"teacher2","15888888882",'2015-05-16');
INSERT INTO profile(id,name,phone,last_update_date) values(10002,"teacher3","15888888883",'2015-05-16');
INSERT INTO profile(id,name,phone,last_update_date) values(10003,"teacher4","15888888884",'2015-05-16');

INSERT INTO teacher(profile_id,school_id,sex,head_teacher,initialized) values(10001,1,1,1,1);
INSERT INTO teacher(profile_id,school_id,sex,head_teacher,initialized) values(10002,2,0,1,1);
INSERT INTO teacher(profile_id,school_id,sex,head_teacher,initialized) values(10003,3,0,1,1);
INSERT INTO teacher(profile_id,school_id,sex,head_teacher,initialized) values(10004,4,1,1,0);

INSERT INTO teacher_class_map(teacher_id,class_id,sub_title,head_teacher) values(10001,1,"Math",1);
INSERT INTO teacher_class_map(teacher_id,class_id,sub_title,head_teacher) values(10002,2,"Nature",1);
INSERT INTO teacher_class_map(teacher_id,class_id,sub_title,head_teacher) values(10003,3,"Chinese",1);
INSERT INTO teacher_class_map(teacher_id,class_id,sub_title,head_teacher) values(10004,4,"English",1);