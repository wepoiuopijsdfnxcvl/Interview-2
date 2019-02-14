#题目：设有一数据库，包括四个表：学生表（Student）、课程表（Course）、成绩表（Score）以及教师信息表（Teacher）。四个表的结构分别如表1-1的表（一）~表（四）所示，数据如表1-2的表（一）~表（四）所示。用SQL语句创建四个表并完成相关题目。


###数据库的表结构
#表1：Student (学生表) 
	CREATE TABLE student
	(
	sno VARCHAR(20) NOT NULL PRIMARY KEY COMMENT '学号',
	sname VARCHAR(20) NOT NULL COMMENT '学生姓名',
	ssex VARCHAR(20) NOT NULL COMMENT '学生性别',
	sbirthday DATETIME COMMENT '出生年月',
	class VARCHAR(20) COMMENT '所在班级'
	);

#表2：Teacher(教师表)
	CREATE TABLE teacher
	 (
	tno VARCHAR(20) NOT NULL PRIMARY KEY COMMENT '教师编号',
	tname VARCHAR(20) NOT NULL COMMENT '教师姓名',
	tsex VARCHAR(20) NOT NULL COMMENT '教师性别',
	tbirthday DATETIME COMMENT '出生年月',
	prof VARCHAR(20) COMMENT '职称',
	depart VARCHAR(20) NOT NULL COMMENT '所在部门'
	);

#表3：Course（课程表）
	CREATE TABLE course
	(
	cno VARCHAR(20) NOT NULL PRIMARY KEY COMMENT '课程编号',
	cname VARCHAR(20) NOT NULL COMMENT '课程名称',
	tno VARCHAR(20) NOT NULL COMMENT '教师编号（外键）',
	FOREIGN KEY(tno) REFERENCES teacher(tno)
	);

#表4：Score(成绩表)
	CREATE TABLE score
	(
	sno VARCHAR(20) NOT NULL COMMENT '学号（外键）',
	FOREIGN KEY(sno) REFERENCES student(sno),
	cno VARCHAR(20) NOT NULL COMMENT '课程编号（外键）',
	FOREIGN KEY(cno) REFERENCES course(cno),
	degree DECIMAL COMMENT '成绩'
	);


###数据库中的数据

#Student(学生表)
INSERT INTO student VALUES('108','曾华','男','1977-09-01','95033');
INSERT INTO student VALUES('105','匡明','男','1975-10-02','95031');
INSERT INTO student VALUES('107','王丽','女','1976-01-23','95033');
INSERT INTO student VALUES('101','李军','男','1976-02-20','95033');
INSERT INTO student VALUES('109','王芳','女','1975-02-10','95031');
INSERT INTO student VALUES('103','陆君','男','1974-06-03','95031');

#Teacher(教师表)
INSERT INTO teacher VALUES (804,'李成','男','1958-12-2','副教授','计算机系');
INSERT INTO teacher VALUES (856,'张旭','男','1969-3-12','讲师','电子工程系');
INSERT INTO teacher VALUES  (825,'王萍','女','1972-5-5','助教','计算机系'); 
INSERT INTO teacher VALUES (831,'刘冰','女','1977-8-14','助教','电子工程系');

#Course(课程表)
INSERT INTO course VALUES('3-105','计算机导论','825');
INSERT INTO course VALUES('3-245','操作系统','804');
INSERT INTO course VALUES('6-166','数字电路','856');
INSERT INTO course VALUES('9-888','高等数学','831');

#Score(成绩表)
INSERT INTO score VALUES('103','3-245','86');
INSERT INTO score VALUES('105','3-245','75');
INSERT INTO score VALUES('109','3-245','68');
INSERT INTO score VALUES('103','3-105','92');
INSERT INTO score VALUES('105','3-105','88');
INSERT INTO score VALUES('109','3-105','76');
INSERT INTO score VALUES('103','3-105','64');
INSERT INTO score VALUES('105','3-105','91');
INSERT INTO score VALUES('109','3-105','78');
INSERT INTO score VALUES('103','6-166','85');
INSERT INTO score VALUES('105','6-166','79');
INSERT INTO score VALUES('109','6-166','81');


###45小题答案

#1、 查询Student表中的所有记录的Sname、Ssex和Class列。
	SELECT Sname,Ssex,Class FROM student;

#2、 查询教师所有的单位即不重复的Depart列。
	SELECT DISTINCT depart FROM teacher;

#3、查询Student表的所有记录。
	SELECT * FROM student;
	
#4、查询Score表中成绩在60到80之间的所有记录。
	SELECT * FROM score WHERE degree >=60 AND degree<=80;

#5、查询Score表中成绩为85，86或88的记录。
	SELECT * FROM score WHERE degree IN(85,86,88);

#6、查询Student表中“95031”班或性别为“女”的同学记录。
	SELECT * FROM student WHERE ssex='女' OR class=95031

#7、以Class降序查询Student表的所有记录。
	SELECT * FROM student ORDER BY class DESC;































































































































































