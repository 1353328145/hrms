use hrm;
-- 3.24
-- 员工表
create table employ(
    uid bigint primary key, -- 工号
    password char (100), -- 密码
    sex tinyint,  -- 性别 0 男 1 女
    name char(10), -- 姓名
    phone char(20), -- 手机
    address char(30), -- 联系地址
    national char(10), -- 民族
    id_number char(30), -- 身份证号
    culture char(10), -- 最高学历
    school char(30), -- 毕业学校
    email char(30), -- 邮件
    contract date, -- 合同期限
    department_id int, -- 部门id
    birthday date, -- 生日 ——> 年龄
    employ_from char(200), -- 聘用形式
    role_id int, -- 角色id
    img_key char(60), -- 照片
    create_time datetime -- 加入时间
);
-- 角色表
create table role(
    rid int not null primary key auto_increment, -- 角色id
    role_name char(100) not null, -- 角色描述
    role_val tinyint default 1
    -- 1.普通用户，无法进入后台管理
    -- 2.管理员 , 根据系统管理员分配权限
    -- 3.系统管理员 ，无所不能
);
insert into role (`role_name`, `role_val`) VALUES ('后台系统管理员', 3);
insert into role (`role_name`, `role_val`) VALUES ('管理员', 2);
insert into role (`role_name`, `role_val`) VALUES ('普通员工', 1);
-- 后台管理菜单表
create table menu(
    mid int not null primary key auto_increment, -- id
    info char (20), -- 菜单名称
    menu_path char (40), -- 菜单路径
    icon char(50) -- 菜单icon
);
insert into menu (`info`, `menu_path`, `icon`) VALUES ('首页', '/home/main', 'el-icon-s-home');
insert into menu (`info`, `menu_path`, `icon`) VALUES ('公司信息管理', '/home/info', 'el-icon-s-opportunity');
insert into menu (`info`, `menu_path`, `icon`) VALUES ('组织架构管理', '/home/framework', 'el-icon-s-platform');
insert into menu (`info`, `menu_path`, `icon`) VALUES ('考勤信息管理', '/home/attendance', 'el-icon-s-flag');
insert into menu (`info`, `menu_path`, `icon`) VALUES ('权限管理', '/home/authority', 'el-icon-s-tools');
insert into menu (`info`, `menu_path`, `icon`) VALUES ('用户管理', '/home/user', 'el-icon-user-solid');
insert into menu (`info`, `menu_path`, `icon`) VALUES ('角色与菜单管理', '/home/role_menu', 'el-icon-menu');
insert into menu (`info`, `menu_path`, `icon`) VALUES ('首页公告管理', '/home/msg', 'el-icon-s-promotion');
insert into menu (`info`, `menu_path`, `icon`) VALUES ('个人信息管理', '/home/info', 'el-icon-caret-right');

-- 菜单-人员表
create table employ_menu(
    mid int,
    uid bigint,
    primary key (mid,uid)
);
-- 部门表
create table department(
    did int not null primary key auto_increment, -- 部门id
    name char (20), --  部门名称
    admin_employ bigint, -- 管理员
    description char (200), -- 部门职能描述
    pdid int -- 父部门id
);
insert into department (`name`, `admin_employ`, `description`, `pdid`) VALUES ('财务部', NULL, '财务', NULL);
insert into department (`name`, `admin_employ`, `description`, `pdid`) VALUES ('业务部', NULL, '业务', NULL);
insert into department (`name`, `admin_employ`, `description`, `pdid`) VALUES ('行政部', NULL, '行政', NULL);
insert into department (`name`, `admin_employ`, `description`, `pdid`) VALUES ('研发部', NULL, '研发', 2);
insert into department (`name`, `admin_employ`, `description`, `pdid`) VALUES ('策划部', NULL, '策划', 2);
insert into department (`name`, `admin_employ`, `description`, `pdid`) VALUES ('设计部', NULL, '设计', 2);
insert into department (`name`, `admin_employ`, `description`, `pdid`) VALUES ('平面设计部', NULL, '平面设计', 6);
insert into department (`name`, `admin_employ`, `description`, `pdid`) VALUES ('3D设计部', NULL, '3D设计', 6);
insert into department (`name`, `admin_employ`, `description`, `pdid`) VALUES ('人力资源部', NULL, '人力资源', 3);
insert into department (`name`, `admin_employ`, `description`, `pdid`) VALUES ('售后部', NULL, '售后', 3);


-- 打卡表
create table sign(
    sid bigint primary key, -- 打卡id
    uid bigint not null, -- 打卡人
    status tinyint default 1,-- 状态
    -- 1. 正常
    -- 2. 迟到
    img_key char(60), -- 打卡照片
    create_time datetime -- 打卡时间
)
