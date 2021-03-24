use hrm;
-- 3.24
-- 员工表
create table employ(
    uid bigint primary key, -- 工号
    password char (30), -- 密码
    sex tinyint,  -- 性别
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
-- 后台管理菜单表
create table menu(
    mid int not null primary key auto_increment, -- id
    info char (20), -- 菜单名称
    menu_path char (30) -- 菜单路径
);
-- 菜单-人员表
create table employ_menu(
    mid int,
    uid int,
    primary key (mid,uid)
);
-- 部门表
create table department(
    did int not null primary key auto_increment, -- 部门id
    name char (20), --  部门名称
    admin_employ bigint, -- 管理员
    description char (200) -- 部门职能描述
);
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
