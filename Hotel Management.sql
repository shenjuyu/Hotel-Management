
drop table ht_reserve;--Ԥ����
drop table ht_bill;--�˵���
drop table ht_enter;--��ס��
drop table ht_room;--�����
drop table ht_customer;--�ͻ���
drop table ht_staff;--Ա����
drop sequence seq_economy_id;
drop sequence seq_senior_id;
drop sequence seq_best_id;
drop sequence seq_customer_id;
drop sequence seq_reserve_id;
drop sequence seq_staff_id;
drop sequence seq_e_id;


--�����
create table ht_room(
 r_id int primary key,--�����
 r_type varchar2(20),--��������   ���÷����߼�������ͳ�׷�
 r_state varchar2(10),--����״̬  ���С�����ס��Ԥ����ά��
 r_deposit varchar2(20),--Ѻ��
 r_price varchar2(20),--����
 temp01 varchar2(255),--Ԥ���ֶ�
 temp02 varchar2(255),--Ԥ���ֶ�
 temp03 varchar2(255)--Ԥ���ֶ�
);

create sequence seq_economy_id increment by 1 start with 100;--���÷����
create sequence seq_senior_id increment by 1 start with 200;--�߼������
create sequence seq_best_id increment by 1 start with 300;--��ͳ�׷����

--���÷���14��
insert into ht_room values(seq_economy_id.nextval,'���÷�','����','50','100',null,null,null);
insert into ht_room values(seq_economy_id.nextval,'���÷�','����','50','100',null,null,null);
insert into ht_room values(seq_economy_id.nextval,'���÷�','����','50','100',null,null,null);
insert into ht_room values(seq_economy_id.nextval,'���÷�','����','50','100',null,null,null);
insert into ht_room values(seq_economy_id.nextval,'���÷�','����','50','100',null,null,null);
insert into ht_room values(seq_economy_id.nextval,'���÷�','����','50','100',null,null,null);
insert into ht_room values(seq_economy_id.nextval,'���÷�','����','50','100',null,null,null);
insert into ht_room values(seq_economy_id.nextval,'���÷�','����','50','100',null,null,null);
insert into ht_room values(seq_economy_id.nextval,'���÷�','����','50','100',null,null,null);
insert into ht_room values(seq_economy_id.nextval,'���÷�','����','50','100',null,null,null);
insert into ht_room values(seq_economy_id.nextval,'���÷�','����','50','100',null,null,null);
insert into ht_room values(seq_economy_id.nextval,'���÷�','����','50','100',null,null,null);
insert into ht_room values(seq_economy_id.nextval,'���÷�','����','50','100',null,null,null);
insert into ht_room values(seq_economy_id.nextval,'���÷�','����','50','100',null,null,null);

--�߼���
insert into ht_room values(seq_senior_id.nextval,'�߼���','����','70','150',null,null,null);
insert into ht_room values(seq_senior_id.nextval,'�߼���','����','70','150',null,null,null);
insert into ht_room values(seq_senior_id.nextval,'�߼���','����','70','150',null,null,null);
insert into ht_room values(seq_senior_id.nextval,'�߼���','����','70','150',null,null,null);
insert into ht_room values(seq_senior_id.nextval,'�߼���','����','70','150',null,null,null);
insert into ht_room values(seq_senior_id.nextval,'�߼���','����','70','150',null,null,null);
insert into ht_room values(seq_senior_id.nextval,'�߼���','����','70','150',null,null,null);
insert into ht_room values(seq_senior_id.nextval,'�߼���','����','70','150',null,null,null);
insert into ht_room values(seq_senior_id.nextval,'�߼���','����','70','150',null,null,null);
insert into ht_room values(seq_senior_id.nextval,'�߼���','����','70','150',null,null,null);

--��ͳ�׷�
insert into ht_room values(seq_best_id.nextval,'��ͳ�׷�','����','150','500',null,null,null);
insert into ht_room values(seq_best_id.nextval,'��ͳ�׷�','����','150','500',null,null,null);
insert into ht_room values(seq_best_id.nextval,'��ͳ�׷�','����','150','500',null,null,null);
insert into ht_room values(seq_best_id.nextval,'��ͳ�׷�','����','150','500',null,null,null);
insert into ht_room values(seq_best_id.nextval,'��ͳ�׷�','����','150','500',null,null,null);
insert into ht_room values(seq_best_id.nextval,'��ͳ�׷�','����','150','500',null,null,null);
insert into ht_room values(seq_best_id.nextval,'��ͳ�׷�','����','150','500',null,null,null);
insert into ht_room values(seq_best_id.nextval,'��ͳ�׷�','����','150','500',null,null,null);


--�ͻ���
create table ht_customer(
 c_id int primary key,--�ͻ����
 c_name varchar2(10),--�ͻ�����
 c_tel varchar2(15),--�ֻ���
 c_id_number varchar2(20),--���֤��
 c_sex varchar2(5),--�Ա�
 temp01 varchar2(255),--Ԥ���ֶ�
 temp02 varchar2(255),--Ԥ���ֶ�
 temp03 varchar2(255)--Ԥ���ֶ�
);

create sequence seq_customer_id increment by 1 start with 1000;

insert into ht_customer values(seq_customer_id.nextval,'С��','15649816465','371624199901252564','��',null,null,null);
insert into ht_customer values(seq_customer_id.nextval,'�Է�ѫ','17684952648','423564200006261956','��',null,null,null);
insert into ht_customer values(seq_customer_id.nextval,'������','13958461205','425195196710101958','Ů',null,null,null);
insert into ht_customer values(seq_customer_id.nextval,'��˼��','15842502549','423564198202242846','��',null,null,null);
insert into ht_customer values(seq_customer_id.nextval,'��ޢ','13954805725','423564199211263495','Ů',null,null,null);


--Ԥ����
create table ht_reserve( 
 e_id int primary key,--Ԥ����Ϣ���
 c_id int constraint FK_c_id references ht_customer(c_id),--�ͻ����
 r_id int constraint FK_rid references ht_room(r_id),--������
 e_reservedate date,--��ʱԤ��������
 e_res_comdate date,--Ԥ������ס����
 e_days int,--��ס����
 e_leave_date date,--�˷�����
 e_true_leave date,--ʵ�ʵ���ס����/ȡ��Ԥ������ �����ֶ����������ã�
 temp01 varchar2(255),--Ԥ���ֶ�
 temp02 varchar2(255),--Ԥ���ֶ�
 temp03 varchar2(255)--Ԥ���ֶ�
);

create sequence seq_reserve_id increment by 1 start with 10000;

--Ա����Ϣ(����--��½����)
create table ht_staff(
 s_id int primary key,--���
 s_pwd varchar2(20),--��½����
 s_name varchar2(15),--����
 s_sex varchar2(5),--�Ա�
 s_id_number varchar2(20),--���֤��
 s_tel varchar2(15),--�ֻ���
 s_image blob,--��Ƭ
 s_power varchar2(255),--Ԥ���ֶ�
 temp02 varchar2(255),--Ԥ���ֶ�
 temp03 varchar2(255)--Ԥ���ֶ�
);

create sequence seq_staff_id increment by 1 start with 1000;

insert into ht_staff values(seq_staff_id.nextval,'a','С��','��','423560199905262549','13596490569',null,'Staff',null,null);
insert into ht_staff values(seq_staff_id.nextval,'b','����','Ů','423560200212051624','18569356492',null,'Staff',null,null);
insert into ht_staff values(seq_staff_id.nextval,'c','���','Ů','42356020000118498x','15462025186',null,'Staff',null,null);
insert into ht_staff values(seq_staff_id.nextval,'z','����','��','42356016780913459x','13672810984',null,'Boss',null,null);

--��ס
create table ht_enter(
 e_id int primary key, --���	����
 c_id int constraint fk_customer_cid references ht_customer(c_id),--�ͻ����	���
 r_id int constraint fk_room_id references ht_room(r_id),--����� ���
 join_date date ,--��ס����
 e_day varchar2(10) unique,--��ס����
 return_date date,--�˷�ʱ��
 cp varchar2(10),--ΥԼ��
 rs int,--�Ƿ�Ԥ��  0 ��    1 ��
 temp01 varchar2(255),--Ԥ���ֶ�
 temp02 varchar2(255),--Ԥ���ֶ�
 temp03 varchar2(255)--Ԥ���ֶ�
);

--�˵�
create table ht_bill(
 b_id varchar2(40) primary key,--�˵���
 now_date date ,--����ʱ��
 r_id varchar2(20) ,--�����
 r_type varchar2(20),--��������
 com_date date,--��סʱ��
 numdays varchar2(20),--��ס����
 leave_date date,--�˷�ʱ��
 violatemoney varchar2(20), --ΥԼ��
 c_name varchar2(20),--�ͻ�����
 c_tel varchar2(20),--�ֻ���
 r_deposit varchar2(20),--����Ѻ��
 r_price varchar2(20),--ס������
 b_qt varchar2(20) ,--��������
 b_joinpay varchar2(20),--����Ӧ�ս��
 b_returnpay varchar2(20),--�����˻����
 b_allpay varchar2(20),--���η�����
 temp01 varchar2(255),--Ԥ���ֶ�
 temp02 varchar2(255),--Ԥ���ֶ�
 temp03 varchar2(255),--Ԥ���ֶ�
 temp04 varchar2(255)--Ԥ���ֶ�
);

select * from ht_room; 
select * from ht_customer;
select * from ht_reserve;
select * from ht_staff;
select * from ht_enter;
select * from ht_bill;

commit;
