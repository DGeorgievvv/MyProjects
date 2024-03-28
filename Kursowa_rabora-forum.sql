create database forum;
use forum;

create table users(
userID int primary key not null auto_increment,
username varchar(50) not null unique,
password varchar(30) not null,
email varchar(50) not null
);

create table topics(
topicID int primary key not null auto_increment,
title varchar(255) not null,
content text not null,
authorID int not null,
createdAt timestamp default current_timestamp,
foreign key (authorID) references users(userID)
);

create table comments(
commentID int primary key not null auto_increment,
content text not null,
authorID int not null,
topicID int not null,
createdAt timestamp default current_timestamp,
foreign key (authorID) references users(userID),
foreign key (topicID) references topics(topicID)
);

create table privateMessages(
messageID int primary key not null auto_increment,
content text not null,
senderID int not null,
receiverID int not null,
sentAt timestamp default current_timestamp,
foreign key (senderID) references users(userID),
foreign key (receiverID) references users(userID),
constraint different_sender_receiver check (senderID != receiverID)
);

/*примет за SELECT заявка  ограничение*/
select * from privateMEssages
where senderID = 1;

/*пример за заявка с функция GROUP BY*/
select authorID, count(*) as MessageCount from comments 
group by authorID;

/*пример за заявка с INNER JOIN*/
select users.username, privateMessages.content from privateMessages
inner join users on privateMessages.senderID = users.userID;

/*пример за заявка с OUTER JOIN*/
select users.username, privateMessages.content from users
left outer join privateMessages on users.userID = privateMessages.receiverID;

/*пример за заявка с взложен SELECT*/
select topicID, title, (select count(*) from comments where comments.topicID = topics.topicID) as CommentCount
from topics;

/*пример за заявка с INNER JOIN и агрегатна функция*/
select topics.topicID, topics.title, count(*) as CommentCount from topics
inner join comments on topics.topicID = comments.topicID
group by topics.topicID, topics.title;

create table privateMessageNew(
messageID int,
content text,
senderID int,
receiverID int,
sentAt timestamp
);

/*създаване на TRIGGER*/
create trigger AfterPrivateMessageInsert
after insert on privateMessages
for each row
insert into privateMessageNew (messageID, content, senderID, receiverID, sentAt)
values (new.messageID, new.content, new.senderID, new.receiverID, new.sentAt);

/*създавне на процедура с курсор*/
delimiter //
create procedure GetAllUsers()
begin
declare done int default false;
declare userId int;
declare username varchar(50);
declare cur cursor for select userID, username from users;
declare continue handler for not found set done = true;
open cur;
read_loop: loop
fetch cur into userId, username;
if done then leave read_loop;
end if;
select userID, username;
end loop;
close cur;
end//
delimiter ;
