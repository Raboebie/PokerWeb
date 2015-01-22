create table Players(
name varchar(12) not null,
password varchar(255) not null,
salt varchar(255) not null,
PRIMARY KEY (name)
);

create table Game(
GameName varchar(20) not null,
GameDate TIMESTAMP(8) not null,
name varchar(12) not null,
PRIMARY KEY (GameDate)
);

create table PlayerGames(
GameName varchar(20) not null,
name varchar(12) not null,
PRIMARY KEY (GameName)
)