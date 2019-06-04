create database if not exists shops default character set utf8;
use shops;
create table if not exists goods(
    goods_id int primary key auto_increment comment '商品编号',
    goods_name varchar(20) comment '商品名称',
    unitprice float(6,2) not null default 0 comment '商品单价',
    category varchar(10) comment '商品类别',
    provider varchar(10) not null default '' comment '供应商名称'
);
create table if not exists customer(
    customer_id int primary key auto_increment comment '客户地址',
    name varchar(20) not null default '' comment '客户姓名',
    address varchar(30) comment '客户地址',
    email varchar(20) unique default '' comment '客户邮箱',
    sex enum('男','女') default '男' comment '客户性别',
    card_id char(10) not null comment '客户身份证'
);
create table if not exists purchase(
    order_id int primary key auto_increment comment '客户订单号',
    customer_id int not null comment '客户编号',
    goods_id int not null comment '商品编号',
    nums int not null default 0 comment '购买数量',
    foreign key (customer_id) references customer(customer_id),
    foreign key (goods_id) references goods(goods_id)
);