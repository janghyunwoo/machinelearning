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

select * from rating ;
delete from users where id='jang'; 

select * from genre ;

select movies.movieid,movies.title,movies.releaseday,movies.genreid,movies.img,ROUND(SUM(rating.rating)/COUNT(rating.rating),2) AS "avgrating" 
from movies,rating where movies.movieid = rating.movieid group by movies.movieid order by SUM(rating.rating)/COUNT(rating.rating) DESC limit 200;


select COUNT(rating.rating) from rating group by movieid;

select movies.movieid,movies.title,movies.releaseday,movies.genreid,movies.img,ROUND(SUM(rating.rating)/COUNT(rating.rating),2) AS "avgrating"
from movies,rating where movies.movieid = rating.movieid group by rating.movieid having COUNT(rating.rating)>100 order by avgrating limit 200;






















