
drop table ht_reserve;--预定表
drop table ht_bill;--账单表
drop table ht_enter;--入住表
drop table ht_room;--房间表
drop table ht_customer;--客户表
drop table ht_staff;--员工表
drop sequence seq_economy_id;
drop sequence seq_senior_id;
drop sequence seq_best_id;
drop sequence seq_customer_id;
drop sequence seq_reserve_id;
drop sequence seq_staff_id;
drop sequence seq_e_id;


--房间表
create table ht_room(
 r_id int primary key,--房间号
 r_type varchar2(20),--房间类型   经济房、高级房、总统套房
 r_state varchar2(10),--房间状态  空闲、已入住、预定、维护
 r_deposit varchar2(20),--押金
 r_price varchar2(20),--房价
 temp01 varchar2(255),--预留字段
 temp02 varchar2(255),--预留字段
 temp03 varchar2(255)--预留字段
);

create sequence seq_economy_id increment by 1 start with 100;--经济房编号
create sequence seq_senior_id increment by 1 start with 200;--高级房编号
create sequence seq_best_id increment by 1 start with 300;--总统套房编号

--经济房共14间
insert into ht_room values(seq_economy_id.nextval,'经济房','空闲','50','100',null,null,null);
insert into ht_room values(seq_economy_id.nextval,'经济房','空闲','50','100',null,null,null);
insert into ht_room values(seq_economy_id.nextval,'经济房','空闲','50','100',null,null,null);
insert into ht_room values(seq_economy_id.nextval,'经济房','空闲','50','100',null,null,null);
insert into ht_room values(seq_economy_id.nextval,'经济房','空闲','50','100',null,null,null);
insert into ht_room values(seq_economy_id.nextval,'经济房','空闲','50','100',null,null,null);
insert into ht_room values(seq_economy_id.nextval,'经济房','空闲','50','100',null,null,null);
insert into ht_room values(seq_economy_id.nextval,'经济房','空闲','50','100',null,null,null);
insert into ht_room values(seq_economy_id.nextval,'经济房','空闲','50','100',null,null,null);
insert into ht_room values(seq_economy_id.nextval,'经济房','空闲','50','100',null,null,null);
insert into ht_room values(seq_economy_id.nextval,'经济房','空闲','50','100',null,null,null);
insert into ht_room values(seq_economy_id.nextval,'经济房','空闲','50','100',null,null,null);
insert into ht_room values(seq_economy_id.nextval,'经济房','空闲','50','100',null,null,null);
insert into ht_room values(seq_economy_id.nextval,'经济房','空闲','50','100',null,null,null);

--高级房
insert into ht_room values(seq_senior_id.nextval,'高级房','空闲','70','150',null,null,null);
insert into ht_room values(seq_senior_id.nextval,'高级房','空闲','70','150',null,null,null);
insert into ht_room values(seq_senior_id.nextval,'高级房','空闲','70','150',null,null,null);
insert into ht_room values(seq_senior_id.nextval,'高级房','空闲','70','150',null,null,null);
insert into ht_room values(seq_senior_id.nextval,'高级房','空闲','70','150',null,null,null);
insert into ht_room values(seq_senior_id.nextval,'高级房','空闲','70','150',null,null,null);
insert into ht_room values(seq_senior_id.nextval,'高级房','空闲','70','150',null,null,null);
insert into ht_room values(seq_senior_id.nextval,'高级房','空闲','70','150',null,null,null);
insert into ht_room values(seq_senior_id.nextval,'高级房','空闲','70','150',null,null,null);
insert into ht_room values(seq_senior_id.nextval,'高级房','空闲','70','150',null,null,null);

--总统套房
insert into ht_room values(seq_best_id.nextval,'总统套房','空闲','150','500',null,null,null);
insert into ht_room values(seq_best_id.nextval,'总统套房','空闲','150','500',null,null,null);
insert into ht_room values(seq_best_id.nextval,'总统套房','空闲','150','500',null,null,null);
insert into ht_room values(seq_best_id.nextval,'总统套房','空闲','150','500',null,null,null);
insert into ht_room values(seq_best_id.nextval,'总统套房','空闲','150','500',null,null,null);
insert into ht_room values(seq_best_id.nextval,'总统套房','空闲','150','500',null,null,null);
insert into ht_room values(seq_best_id.nextval,'总统套房','空闲','150','500',null,null,null);
insert into ht_room values(seq_best_id.nextval,'总统套房','空闲','150','500',null,null,null);


--客户表
create table ht_customer(
 c_id int primary key,--客户编号
 c_name varchar2(10),--客户姓名
 c_tel varchar2(15),--手机号
 c_id_number varchar2(20),--身份证号
 c_sex varchar2(5),--性别
 temp01 varchar2(255),--预留字段
 temp02 varchar2(255),--预留字段
 temp03 varchar2(255)--预留字段
);

create sequence seq_customer_id increment by 1 start with 1000;

insert into ht_customer values(seq_customer_id.nextval,'小华','15649816465','371624199901252564','男',null,null,null);
insert into ht_customer values(seq_customer_id.nextval,'赵峰勋','17684952648','423564200006261956','男',null,null,null);
insert into ht_customer values(seq_customer_id.nextval,'冯晓莉','13958461205','425195196710101958','女',null,null,null);
insert into ht_customer values(seq_customer_id.nextval,'赵思豪','15842502549','423564198202242846','男',null,null,null);
insert into ht_customer values(seq_customer_id.nextval,'倪蔻','13954805725','423564199211263495','女',null,null,null);


--预定表
create table ht_reserve( 
 e_id int primary key,--预定信息编号
 c_id int constraint FK_c_id references ht_customer(c_id),--客户编号
 r_id int constraint FK_rid references ht_room(r_id),--房间编号
 e_reservedate date,--何时预定的日期
 e_res_comdate date,--预定的入住日期
 e_days int,--入住天数
 e_leave_date date,--退房日期
 e_true_leave date,--实际的入住日期/取消预定日期 （此字段有两个功用）
 temp01 varchar2(255),--预留字段
 temp02 varchar2(255),--预留字段
 temp03 varchar2(255)--预留字段
);

create sequence seq_reserve_id increment by 1 start with 10000;

--员工信息(补充--登陆密码)
create table ht_staff(
 s_id int primary key,--编号
 s_pwd varchar2(20),--登陆密码
 s_name varchar2(15),--姓名
 s_sex varchar2(5),--性别
 s_id_number varchar2(20),--身份证号
 s_tel varchar2(15),--手机号
 s_image blob,--照片
 s_power varchar2(255),--预留字段
 temp02 varchar2(255),--预留字段
 temp03 varchar2(255)--预留字段
);

create sequence seq_staff_id increment by 1 start with 1000;

insert into ht_staff values(seq_staff_id.nextval,'a','小黑','男','423560199905262549','13596490569',null,'Staff',null,null);
insert into ht_staff values(seq_staff_id.nextval,'b','晓洁','女','423560200212051624','18569356492',null,'Staff',null,null);
insert into ht_staff values(seq_staff_id.nextval,'c','赵歆','女','42356020000118498x','15462025186',null,'Staff',null,null);
insert into ht_staff values(seq_staff_id.nextval,'z','伯爵','男','42356016780913459x','13672810984',null,'Boss',null,null);

--入住
create table ht_enter(
 e_id int primary key, --编号	主键
 c_id int constraint fk_customer_cid references ht_customer(c_id),--客户编号	外键
 r_id int constraint fk_room_id references ht_room(r_id),--房间号 外键
 join_date date ,--入住日期
 e_day varchar2(10) unique,--入住天数
 return_date date,--退房时间
 cp varchar2(10),--违约金
 rs int,--是否预定  0 否    1 是
 temp01 varchar2(255),--预留字段
 temp02 varchar2(255),--预留字段
 temp03 varchar2(255)--预留字段
);

--账单
create table ht_bill(
 b_id varchar2(40) primary key,--账单号
 now_date date ,--结账时间
 r_id varchar2(20) ,--房间号
 r_type varchar2(20),--房间类型
 com_date date,--入住时间
 numdays varchar2(20),--入住天数
 leave_date date,--退房时间
 violatemoney varchar2(20), --违约金
 c_name varchar2(20),--客户姓名
 c_tel varchar2(20),--手机号
 r_deposit varchar2(20),--房间押金
 r_price varchar2(20),--住房消费
 b_qt varchar2(20) ,--其他消费
 b_joinpay varchar2(20),--本次应收金额
 b_returnpay varchar2(20),--本次退还金额
 b_allpay varchar2(20),--本次服务共收
 temp01 varchar2(255),--预留字段
 temp02 varchar2(255),--预留字段
 temp03 varchar2(255),--预留字段
 temp04 varchar2(255)--预留字段
);

select * from ht_room; 
select * from ht_customer;
select * from ht_reserve;
select * from ht_staff;
select * from ht_enter;
select * from ht_bill;

commit;
