show tables;

select * from rating;
delete from rating; 
insert into rating values(186,302,3);
create table rating(

      userid int(10) unsigned not null,

      movieid int(10) not null,

      rating float(3) not null

)  DEFAULT CHARACTER SET utf8;

insert into users(id,pw,sex,birthday,isadmin) values('jang','Jang','F','2018-12-12',0);

select * from users ;
delete from users where id='jang'; 

select * from genre ;