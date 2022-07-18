drop table if exists tbl_expenses;
create table tbl_expenses (
expense_id integer primary key not null,
expense_type text,
expense_limit decimal(10,2) ,
expense_phto blob
);
drop table if exists tbl_expenses_type;
create table tbl_expenses_type (
typeid integer primary key not null,
typeName text,
typelimit decimal(10,2)
);