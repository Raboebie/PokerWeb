create table Players(
name varchar(12) not null,
password varchar(255) not null,
hand varchar(255) not null,
PRIMARY KEY (name)
);

create table Game(
GameName varchar(20) not null,
GameDate TIMESTAMP(8) not null,
PRIMARY KEY (GameDate)
);

create table PlayerGames(
    name varchar(100) not null,
    GameName varchar(100) not null,
    hand varchar(100) not null,
    constraint username_fk foreign key (name) references Players(name),
    constraint gameName_fk foreign key (GameName) references Game(gameName),
    primary key(name, gameName)
);