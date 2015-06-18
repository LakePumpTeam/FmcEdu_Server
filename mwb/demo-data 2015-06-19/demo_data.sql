#province
INSERT INTO province (NAME, last_update_date)
VALUES
	('四川', now());

#city
INSERT INTO city (
	province_id,
	NAME,
	last_update_date
)
VALUES
	(1, '成都', now());

#school
INSERT INTO school (
	NAME,
	province_id,
	city_id,
	last_update_date
)
VALUES
	(
		'成都龙泉第一中学',
		1,
		1,
		now()
	);

#class
INSERT INTO class (
	grade,
	class,
	school_id,
	available,
	head_teacher_id,
	last_update_date
)
VALUES
	('2010', 1, 1, 1, 1, now());

#teacher profile
INSERT INTO PROFILE (
	id,
	NAME,
	phone,
	PASSWORD,
	available,
	profile_type,
	last_update_date,
	salt
)
VALUES
	(
		1,
		'何绍敏',
		'15888888888',
		'f2989ae3cba57770a8f7993fe4bf473d',
		1,
		1,
		now(),
		'15888888881'
	);

INSERT INTO teacher (
	profile_id,
	school_id,
	sex,
	head_teacher,
	initialized,
	resume,
	birth,
	course
)
VALUES
	(
		1,
		1,
		0,
		1,
		1,
		'何绍敏，女，1970年3月出生于吉林省敦化市，高级教师。2013年05获得成都市龙泉区竞赛课程一等奖。',
		'1970-03-04',
		'语文'
	);

#teacher class mapping
INSERT INTO teacher_class_map (
	teacher_id,
	class_id,
	sub_title,
	head_teacher,
	last_update_date
)
VALUES
	(1, 1, '语文', 1, now());

#school activity
INSERT INTO `news` (
	author,
	`subject`,
	content,
	news_type,
	ref_id,
	approved,
	approved_by,
	approve_date,
	available,
	`like`,
	publish_date,
	creation_date,
	last_update_date
)
VALUES
	(
		1,
		'向雷锋同志学习',
		'今年的3月5日是毛泽东等老一辈无产阶级革命家“向雷锋同志学习”题词发表50周年纪念日，为进一步弘扬雷锋精神，倡导社会文明新风，深化学雷锋活动，积极争当“雷锋标兵”，我校团委组织全校团员和少先队员，将3月作为“践行雷锋精神，传递青春正能量”的活动月。通过本次活动不仅把雷锋精神熔铸在学生的灵魂深处，而且落实到具体行动上，真正做到“内化于心、外化于行”，从而使得雷锋精神在新时代散发出更加迷人的风采.',
		2,
		1,
		1,
		1,
		now(),
		1,
		0,
		now(),
		now(),
		now()
	);
INSERT INTO `news` (
	author,
	`subject`,
	content,
	news_type,
	ref_id,
	approved,
	approved_by,
	approve_date,
	available,
	`like`,
	publish_date,
	creation_date,
	last_update_date
)
VALUES
	(
		1,
		'5月30号运动会',
		'	5月30号上午8:00整，迎着温暖的春风，开幕式正式开始。伴随着热烈的音乐、激昂的旋律，同学们以整齐的方阵在国旗队、彩旗队、拉拉队的引领下，昂首步入会场。以班级运动员为方队的同学们在行进中进行特色展示，嘹亮的口号、整齐的队伍，彰显了同学们的青春风采和高昂斗志。  庄严的升旗仪式后，校长致开幕词。校长代表学校对校运会的胜利召开表示热烈的祝贺，对精心筹备运动会的工作人员表示诚挚的感谢，并向全体运动员、裁判员致以亲切的问候和美好的祝愿。',
		2,
		1,
		1,
		1,
		now(),
		1,
		0,
		now(),
		now(),
		now()
	);
#school notification
INSERT INTO `news` (
	author,
	`subject`,
	content,
	news_type,
	ref_id,
	approved,
	approved_by,
	approve_date,
	available,
	`like`,
	publish_date,
	creation_date,
	last_update_date
)
VALUES
	(
		1,
		'停水通知',
		'由于近期学校周边施工，自来水供应系统不能正常工作，预计2015年6月10日恢复正常自来水供应，请周知。',
		3,
		1,
		1,
		1,
		now(),
		1,
		0,
		now(),
		now(),
		now()
	);
#school news
INSERT INTO `news` (
	author,
	`subject`,
	content,
	news_type,
	ref_id,
	approved,
	approved_by,
	approve_date,
	available,
	`like`,
	publish_date,
	creation_date,
	last_update_date
)
VALUES
	(
		1,
		'市长走访我校',
		'2015年六一国际儿童节前夕，汉中市委书记魏增军，市委常委、汉台区委书记杨记明，市委常委、市委组织部部长王晓林，市人大常委会副主任李钧，副市长王春丽，市政协副主席王青平等市领导走访我校，看望慰问少年儿童，为我们送上节日的真挚祝福。',
		4,
		1,
		1,
		1,
		now(),
		1,
		0,
		now(),
		now(),
		now()
	);
#class activity
INSERT INTO `news` (
	author,
	`subject`,
	content,
	news_type,
	ref_id,
	approved,
	approved_by,
	approve_date,
	available,
	`like`,
	publish_date,
	creation_date,
	last_update_date
)
VALUES
	(
		1,
		'五一班运动会风采',
		'我们班的主角——五（1）班的运动员、一群充满活力、激情飞扬的运动小将，用挥洒的泪水换来了一个个珍贵的分数和一张张奖状。运动会后我们收获了丙组第二的好成绩，不光这个词是否得奖，我们都尽力了，我们交上了满意的答案。当然大家还不忘总结一些经验教训，争取明年再接再厉，争取夺得冠军的宝座。',
		5,
		1,
		1,
		1,
		now(),
		1,
		0,
		now(),
		now(),
		now()
	);
#parenting class
INSERT INTO `news` (
	author,
	`subject`,
	content,
	news_type,
	ref_id,
	approved,
	approved_by,
	approve_date,
	available,
	`like`,
	publish_date,
	creation_date,
	last_update_date
)
VALUES
	(
		1,
		'请鼓励你的孩子做一个幸福的普通人',
		'刚出生的孩子并没有什么区别，为何三年、六年、九年成长之后就产生了很大的差异，走出了不同的道路？这个差异就是由家庭造成的。

家庭教育决定孩子的一生，千万不要认为上名校才是决定一生；夫妻关系永远第一重要，千万不要把孩子放在第一位，凡是把孩子放在第一位的，等待这个家庭的多半是悲剧；永远要保持沟通，家长的言行会影响孩子的一生；孩子的任何缺点和优点都与家长有关，不了解孩子就没有成功的教育。

别把自己的梦想附加给孩子的未来

家庭教育的确是全社会都非常关注的。前两年我谈过一个观点，中国自古以来从来没有像今天一样如此重视对孩子的教育。所谓的重视多多少少有一些扭曲，但是总体来说，每一次有关家庭教育这样的活动，可以看到座无虚席，这反映了我们社会的一种心态。但是，正是由于对家庭教育的高度关注，也使得今天的教育产生了许许多多过去还没有产生过的问题。每个即将当父母或者已经成为父母的人都对即将出生或者即将长成的孩子有着许许多多的要求，有着许许多多的梦想和许许多多的希望。

请鼓励你的孩子做一个幸福的普通人

回想起来我们30多年来靠抢跑培养了这么多尖子学生、竞赛的获奖者、金牌得主，可我们并没有看到当初我们期望的从他们之中产生很多科学领域的大师，至少现在还没有。所以我们每个人对自己的孩子都有这样的过程，开始出生的时候有很多梦想，希望孩子上一个好学校、有一个好成绩，慢慢我们的期望逐渐降温。我们期望这个孩子能够正常毕业、升学、就业、将来能够成家、生儿育女、生活稳定、工作稳定、别下岗。能够在三、四十岁的时候身体健康、家庭和谐、进入老年希望孩子孝顺最后你不能动的时候，所有最初的梦想都没有了，那时候的梦想就是他能在我的身边给我倒一杯水。

如何与孩子沟通应该被当作一门艺术

在沟通这个问题上，请每一位家长都思考一下，我们有丰富的话题吗？有丰富的表达方式吗？在沟通的时候，想过沟通的技巧和艺术吗？除了学习之外，有没有其他的沟通话题？我们是否曾经向孩子说过这样的话：“孩子只要把分数搞上去，别的你什么都不用管”。我们想培养一个有责任感的孩子，可当你说这句话的时候，责任的教育已经彻底的丢掉了。什么都不用管，哪来的责任感？就会出现油瓶子倒了，孩子都会视而不见的情况。在孩子受到挫折的时候也可能无言的方式更有效。不用说什么，上去拥抱一下他，拍拍他的背、他的头，其实孩子学习问题上，家长帮不上什么忙。这种无言的动作孩子全都能够理解。

父母如何更好地与孩子建立情感纽带？

网上有一篇我写的文章，文章的观点是，千万不要把孩子看的比你的生活伴侣更加重要。在我看来，夫妻关系是第一重要，正因为有了牢固的夫妻关系，才有了牢固的家庭，有了牢固的家庭，孩子才会有一个正常、良好的成长环境。后来那篇文章有很多人给我打电话，看完了改变了他们的价值观。因为很多家长把孩子放在第一位，最后导致家庭许多矛盾的产生。

一次家长会上，我谈这个观点的时候，我谈的是我们要有足够的时间跟孩子家人在一起，有家长说我的工作每天就是应酬，晚上都在外面吃饭，很少回家吃饭。我说我不认为任何一顿饭都要在外面吃。在这儿我坦白的跟大家说，我在其他的事情都不会撒谎，只有一件事情可能会撒谎，就是别人给我打电话在外面吃饭的时候我会说“对不起，今天晚上有安排”，其实，这只是借口，我认为和谐的家庭一定要有足够的时间一家人厮守在一起。

在我很年轻的时候，有一个同学家是接待外宾的一个开放的家庭。有一次在他家碰到了一个日本高级官员的夫人，她讲到，父母一定要使自己跟孩子有足够多的皮肤接触，一定要抱孩子，这实际上感情的基础感情是发生在身体来的生物化学反应，没有这些条件这些反应不可能发生，不发生就没有情感发生，情感不是虚幻飘渺的，所以一定要有非常多的接触才有更多的感情。

我个人认为，家庭教育不要把它过于艺术化、也不要过于技术化。一个和谐的家庭、一个和睦的家庭最重要。有了和睦的家庭，孩子在家庭中能够很自然、很幸福的成长。我相信孩子的教育一定是成功的。至于是不是那么早的学更多的东西，我个人的建议是希望家长不要看重“抢跑”。希望今天能够更加理性的来对待家庭教育，对待孩子的成长。  

',
		1,
		1,
		1,
		1,
		now(),
		1,
		0,
		now(),
		now(),
		now()
	);
INSERT INTO `news` (
	author,
	`subject`,
	content,
	news_type,
	ref_id,
	approved,
	approved_by,
	approve_date,
	available,
	`like`,
	publish_date,
	creation_date,
	last_update_date
)
VALUES
	(
		1,
		'家长如何培养孩子讲文明懂礼貌的好习惯',
		'我国自古以来就是“礼仪之邦”，讲文明、重礼貌、和善待人是我们中华民族的传统美德。孔子说过“礼之用，和为贵”。并告诫“赠人以言，重于珠玉；伤人以言，胜于剑戈”。培养讲文明懂礼貌的下一代，让中华民族的传统美德发扬光大，是每一位教师和家长的重要职责。怎样培养举止文明的孩子呢?父母应根据儿童的年龄特征和个性特征对孩子进行文明礼貌教育。对儿童除了结合具体事例告诉他讲文明懂礼貌的必要性之外，更重要的是进行具体的训练，使孩子从小就养成良好的文明礼貌习惯，具体的做法是：
    1．仪表训练
    人的容貌、姿态、服饰是内心世界的外在表现。一般说来，衣冠不整、蓬头垢面的仪表，大多比较消极；而浓妆艳抹、矫揉造作的打扮，往往给人以精神空虚的感觉。所以不能忽视孩子的仪表，它对孩子的心理变化和发展有很大影响。训练孩子仪表，首先要让孩子每天坚持洗脸、刷牙、梳头各两次，饭前便后要洗手，勤剪指甲、勤理发、勤洗澡。其次，要爱护孩子天真率直的童心，及时纠正孩子那些装腔作势、忸忸怩怩、任性胡闹的表现，并在此基础上教孩子对客人表示友善，对成功表示愉悦，对挫折表示惋惜，对前途表示信心。发现孩子怪模怪样，就应心平气和地告诉他：“这样不好，大人不喜欢，你要这个样子就别在我面前。”再次，要注意孩子衣服的选择。孩子衣服的选择原则是图案色彩活泼大方，款式宽松合体，价格适中，耐用易洗。
    2．语言训练
    语言是表达思想感情的工具，礼貌语言像丝丝细雨，能滋润人们的肺腑。“请、您好、谢谢、对不起、没关系”这些常用礼貌用语，家长要教会孩子使用，而且要求孩子表里如一，真正从内心尊重他人。如说“对不起”时就应是真心表示歉意，而不是当作推卸责任的挡箭牌。上学去要对家长说：“妈妈，我上学去了，再见。”而不能说：“哎，我走了。”请别人帮助时态度要诚恳：“麻烦你帮帮我好吗?”而不可以说： “喂，你来给我……”别人帮你后，要说“谢谢”。
    3．行为训练
    孩子能否做到文明礼貌，重要的是看他如何待人接物。家长应从一件件小事上培养孩子良好的行为习惯。比如，告诉孩子到别人家去要先敲门，得到主人允许后再进人；客人来时应起身主动迎接，让座、倒茶；长辈之间交谈时孩子不要随便插嘴，长辈问话时要热情、诚恳地回答；要爱护公共卫生，不随地吐痰、乱扔废物，不损坏公共设施，要遵守公共秩序。家长们还要教育孩子对待老、弱、病、残、孕等行动不便的人，更要尊重和帮助，给他们让座，指路，帮他们拿东西，买车票。当孩子做了好事回家时，家长要以喜悦的表情对孩子的行为报以赞许，可以这样说：“你长大了，真是好样的。”
    培养孩子文明礼貌的习惯，家长也应有良好的文明礼貌习惯。一个满口脏话的家长，想要自己的孩子语言文明是不大可能的；一个经常占集体便宜的家长，想要自己的孩子关心他人、帮助别人做好事也是很难想像的。因此，家长应严格要求自己，坚决彻底地克服自己不文明的习惯，这样才能不断提高家庭的文明程度。
',
		1,
		1,
		1,
		1,
		now(),
		1,
		0,
		now(),
		now(),
		now()
	);
#images
INSERT INTO `image` (`news_id`,`img_name`,`img_path`,`creation_date`,`last_update_date`) VALUES (1,'demo_1.jpg','1/',now(),now());
INSERT INTO `image` (`news_id`,`img_name`,`img_path`,`creation_date`,`last_update_date`) VALUES (1,'demo_2.png','1/',now(),now());
INSERT INTO `image` (`news_id`,`img_name`,`img_path`,`creation_date`,`last_update_date`) VALUES (2,'demo_3.png','2/',now(),now());
INSERT INTO `image` (`news_id`,`img_name`,`img_path`,`creation_date`,`last_update_date`) VALUES (2,'demo_4.jpg','2/',now(),now());
INSERT INTO `image` (`news_id`,`img_name`,`img_path`,`creation_date`,`last_update_date`) VALUES (4,'demo_5.jpg','4/',now(),now());
INSERT INTO `image` (`news_id`,`img_name`,`img_path`,`creation_date`,`last_update_date`) VALUES (4,'demo_6.jpg','4/',now(),now());
INSERT INTO `image` (`news_id`,`img_name`,`img_path`,`creation_date`,`last_update_date`) VALUES (5,'demo_7.png','5/',now(),now());
INSERT INTO `image` (`news_id`,`img_name`,`img_path`,`creation_date`,`last_update_date`) VALUES (5,'demo_8.png','5/',now(),now());
INSERT INTO `image` (`news_id`,`img_name`,`img_path`,`creation_date`,`last_update_date`) VALUES (6,'demo_9.jpg','6/',now(),now());
INSERT INTO `image` (`news_id`,`img_name`,`img_path`,`creation_date`,`last_update_date`) VALUES (7,'demo_10.jpg','7/',now(),now());