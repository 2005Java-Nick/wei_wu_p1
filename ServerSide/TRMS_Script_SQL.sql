drop table USER_ACCOUNT cascade;
create table user_account(
id SERIAL primary key,
username text Unique,
userpass text,
employee_id int
);

drop table session_token cascade;
create table session_token(
user_id int primary key,
user_token text Unique
);

drop table records_tuition_reimbursement cascade;
create table records_tuition_reimbursement(
id SERIAL primary key,
submission_date text,
event_date text,
event_location text,
event_discription text,
event_cost numeric,
event_grading_format text,
event_cutOff_grade text,
event_type text,
event_justification text,
estimated_timeoff text,
optional_comments text,

override_amount numeric,
override_reason text,
exceeds_avaliable_amount boolean,

supervisor_approval text,
department_head_approval text,
benefits_coordinator_approval text,
submitter_message_updated text,
admin_message_updated text,
status text,
supervisor_id int,
department_head_id int,
benefits_coordinator_id int
);


drop table records_tuition_reimbursement_attachments cascade;
create table records_tuition_reimbursement_attachments(
id SERIAL primary key,
records_tuition_reimbursement_id int,
file_name text,
file_size numeric,
content_type text,
file_data bytea
);


drop table employee cascade;
create table employee(
id SERIAL primary key,
first_name text,
mid_name text,
last_name text,
email text,
phone text,
address text,
addressTwo text,
city text,
state text,
zip text,
supervisor_id int,
department_id int
);


drop table reimbursement_account cascade;
create table reimbursement_account(
id SERIAL primary key,
employee_id int Unique,
reimbursement_avaliable numeric,
reimbursement_pending numeric
);


drop table reimbursement_tracker cascade;
create table reimbursement_tracker(
employee_id int,
reimbursement_records_id int,
primary key (employee_id, reimbursement_records_id)
);


drop table permission_levels cascade;
create table permission_levels(
id int primary key,
permission_type text
);

drop table employee_permissions cascade;
create table employee_permissions(
employee_id int,
permission_level_id int,
primary key (employee_id, permission_level_id)
);


drop table departments cascade;
create table departments(
id SERIAL primary key,
department_head_id int,
benefits_coordinator_id int
);

drop table reimbursement_messages cascade;
create table reimbursement_messages(
id SERIAL primary key,
reimbursement_records_id int,
message text
);

ALTER table user_account
add constraint fk_employee foreign key (employee_id) references employee(id) on delete cascade;

ALTER table session_token 
add constraint fk_user_account foreign key (user_id) references user_account(id) on delete cascade;

ALTER table records_tuition_reimbursement_attachments
add constraint fk_tuition_reimbursement_record foreign key (records_tuition_reimbursement_id) references records_tuition_reimbursement(id) on delete cascade;

ALTER table employee
add constraint fk_department_id foreign key (department_id) references departments(id) on delete cascade;

ALTER table reimbursement_account
add constraint fk_employee_id foreign key (employee_id) references employee(id) on delete cascade;

ALTER table reimbursement_tracker
add constraint fk_employee_id foreign key (employee_id) references employee(id) on delete cascade;
ALTER table reimbursement_tracker
add constraint fk_reimbursement_records_id foreign key (reimbursement_records_id) references records_tuition_reimbursement(id) on delete cascade;

ALTER table employee_permissions
add constraint fk_employee_id foreign key (employee_id) references employee(id) on delete cascade;
ALTER table employee_permissions
add constraint fk_permission_level_id foreign key (permission_level_id) references permission_levels(id) on delete cascade;

ALTER table reimbursement_messages
add constraint fk_reimbursement_records_id foreign key (reimbursement_records_id) references records_tuition_reimbursement(id) on delete cascade;

--setting permission levels
insert into permission_levels (id, permission_type ) values (1, 'department_head');
insert into permission_levels (id, permission_type ) values (2, 'supervisor');
insert into permission_levels (id, permission_type ) values (3, 'benefits_coordinator');
insert into permission_levels (id, permission_type ) values (4, 'standard');

--department add
insert into departments (department_head_id, benefits_coordinator_id) values (0,0);
-------boss employee
insert into employee (first_name,mid_name,last_name,email,phone,address,addresstwo,city,state,zip,supervisor_id, department_id)
values ('wei','ken','li','weiwuemail@email.com','999-000-9999','259 Dave Ave', 'N/A', 'Staten Island', 'NY','10041',0,1);
insert into user_account (username,userpass,employee_id ) values ('wei1','pass1',1);
insert into reimbursement_account (employee_id, reimbursement_avaliable, reimbursement_pending ) values (1,1000,0);
insert into employee_permissions (employee_id ,permission_level_id ) values (1,1);

-------department_head
insert into employee (first_name,mid_name,last_name,email,phone,address,addresstwo,city,state,zip,supervisor_id, department_id)
values ('Andy','Jo','Luon','andy@email.com','999-000-9999','5329 Uno Ave', 'N/A', 'Staten Island', 'NY','10041',1,1);
insert into user_account (username,userpass,employee_id ) values ('head1','pass1',2);
insert into reimbursement_account (employee_id, reimbursement_avaliable, reimbursement_pending ) values (2,1000,0);
insert into employee_permissions (employee_id ,permission_level_id ) values (2,1);

--benCo
insert into employee (first_name,mid_name,last_name,email,phone,address,addresstwo,city,state,zip,supervisor_id, department_id)
values ('Ben','Jo','Co','Ben@email.com','999-000-9999','5329 Uno Ave', 'N/A', 'Staten Island', 'NY','10041',1,1);
insert into user_account (username,userpass,employee_id ) values ('ben1','pass1',3);
insert into reimbursement_account (employee_id, reimbursement_avaliable, reimbursement_pending ) values (3,1000,0);
insert into employee_permissions (employee_id ,permission_level_id ) values (3,1);

--direct super
insert into employee (first_name,mid_name,last_name,email,phone,address,addresstwo,city,state,zip,supervisor_id, department_id)
values ('superVis','Ko','sup','Ben@email.com','999-000-9999','5329 Uno Ave', 'N/A', 'Staten Island', 'NY','10041',2,1);
insert into user_account (username,userpass,employee_id ) values ('direct1','pass1',4);
insert into reimbursement_account (employee_id, reimbursement_avaliable, reimbursement_pending ) values (4,1000,0);
insert into employee_permissions (employee_id ,permission_level_id ) values (4,1);

--normal employee
insert into employee (first_name,mid_name,last_name,email,phone,address,addresstwo,city,state,zip,supervisor_id, department_id)
values ('normalGuy','Ko','Nonn','Ben@email.com','999-000-9999','5329 Uno Ave', 'N/A', 'Staten Island', 'NY','10041',4,1);
insert into user_account (username,userpass,employee_id ) values ('norm1','pass1',5);
insert into reimbursement_account (employee_id, reimbursement_avaliable, reimbursement_pending ) values (5,1000,0);
insert into employee_permissions (employee_id ,permission_level_id ) values (5,2);


--get records based on token
select t.*
from records_tuition_reimbursement t
inner join reimbursement_tracker on reimbursement_records_id = t.id 
inner join employee on employee.id = reimbursement_tracker.employee_id 
inner join user_account on user_account.employee_id = employee.id
inner join session_token on session_token.user_id = user_account.id
where session_token.user_token = 'kcKoe0bvcxIcUER6UZLH0qoDW+dz/YLPulnSzSuQfRiz3dd4cMkx8g==';
--get attachments of each record based on records ID
select rtra.*
from records_tuition_reimbursement_attachments rtra
where records_tuition_reimbursement_id = 1;
--get messages of each record based on records ID
select rm.*
from reimbursement_messages rm 
where reimbursement_records_id = 1;

Select * from records_tuition_reimbursement_attachments
where id = 5 and file_name = 'stars_space_glow_planet_99744_1920x1080.jpg';


insert into reimbursement_messages (reimbursement_records_id ,message ) 
values (1,'testttt Messsage');
insert into reimbursement_messages (reimbursement_records_id ,message ) 
values (1,'tes22222sdfsdtttt Messfsdfdsfsdsage');

insert into reimbursement_messages (reimbursement_records_id ,message ) 
values (1,'testttt Messsage');

select e2.first_name, e2.last_name
from employee e2
where e2.id = 1;

select * 
from employee
inner join user_account on user_account.employee_id = employee.id
inner join session_token on session_token.user_id = user_account.id
where session_token.user_token = ?;


select reimbursement_avaliable, reimbursement_pending 
from reimbursement_account
inner join employee on employee.id = reimbursement_account.employee_id 
inner join user_account on user_account.employee_id = employee.id
inner join session_token on session_token.user_id = user_account.id
where session_token.user_token = '';

select permission_level_id
from employee_permissions
inner join employee on employee.id = employee_permissions.employee_id
inner join user_account on user_account.employee_id = employee.id
inner join session_token on session_token.user_id = user_account.id
where session_token.user_token = '';

update reimbursement_account 
set reimbursement_pending = ?
where reimbursement_account.employee_id = ?;

select *
from records_tuition_reimbursement 
where supervisor_id = 1 or department_head_id = 1 or benefits_coordinator_id = 1;

select e2.*
from employee e2
inner join reimbursement_tracker on reimbursement_tracker.employee_id = e2.id 
inner join records_tuition_reimbursement on records_tuition_reimbursement.id = reimbursement_tracker.reimbursement_records_id 
where records_tuition_reimbursement.id = 2;

select pl.*
from permission_levels pl
inner join employee_permissions on employee_permissions.permission_level_id = pl.id
inner join employee on employee.id = employee_permissions.employee_id
inner join user_account on user_account.employee_id = employee.id
inner join session_token on session_token.user_id = user_account.id
where session_token.user_token = '';

update records_tuition_reimbursement 
set department_head_approval = 'true', status = 'test'
where records_tuition_reimbursement.id = 6;
