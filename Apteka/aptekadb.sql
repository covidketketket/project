select drug_name, name, city, address, time_schedule, drug_price from apteki natural join drugs natural join price
where city = '??????' and drug_name like '%????????%' order BY drug_price  asc

select drug_name, name, city, address, time_schedule,price from apteki natural join drugs natural join price where city = '??????' and drug_name like '%?????%' order BY price asc
select drug_name, name, city, address, time_schedule,price from apteki natural join drugs natural join price where city = '??????' and drug_name like '%?????%' order BY price asc
select * from price


select * from drugs


select * from apteki

drop table prices

create table price
(drug_name varchar2(50), name varchar2(50), drug_price int)
select drug_name, city, address, time_schedule,"+
                "drug_price from apteki natural join drugs natural join price "+
                "where city = '" + adminController.getCity() + "' and drug_name like '%" +
                adminController.getDrugName() + "%' order by " + adminController.getSort()

select_drug, name, city, address, time_schedule,drug_price from apteki natural join drugs natural join price where city = '??????' and drug_name like '%?????%' order by drug_price asc
select drug_name, name, city, address, time_schedule, drug_price from apteki natural join drugs natural join price where city = '??????' and drug_name like '%?????%' order BY drug_price asc



select drug_name, name, city, address, time_schedule,drug_price from apteki natural join drugs natural join price where city = '??????' and drug_name like '%?????%' order by drug_price asc
