create database CFP17_matricola;
use CFP17_matricola;
GRANT CREATE, SELECT, INSERT, DELETE ON CFP17_matricola.* TO stigliano_642472_17 IDENTIFIED BY 'CFP17password';
GRANT ALL PRIVILEGES ON *.* TO 'stigliano_642472_17'@'%' WITH GRANT OPTION;
create table missingPlay(
outlook varchar(10),
temperature float (5,2),
umidity varchar(10),
wind varchar(10),
play varchar (10));

insert into missingPlay values('sunny',30.3,'high','weak','no');
insert into missingPlay values('sunny',30.3,'high','strong','no');
insert into missingPlay values('overcast',30.0,'high','weak','yes');
insert into missingPlay values('rain',13.0,'high','weak','yes');
insert into missingPlay values('rain',0.0,'normal','weak','yes');
insert into missingPlay values('rain',0.0,'normal','strong','no');
insert into missingPlay values('overcast',0.1,'normal','strong',null);
insert into missingPlay values('sunny',13.0,'high','weak','no');
insert into missingPlay values('sunny',0.1,'normal','weak','yes');
insert into missingPlay values('rain',12.0,'normal','weak','yes');
insert into missingPlay values('sunny',null,'normal','strong','yes');
insert into missingPlay values('overcast',12.5,'high','strong','yes');
insert into missingPlay values('overcast',29.21,'normal','weak','yes');
insert into missingPlay values('rain',12.5,'high','strong','no');
create table iris(
SepalLenght float (5,2),
SepalWidth float (5,2),
PetalLenght float (5,2),
PetalWidth float (5,2),
Class varchar (20));

insert into iris  values(5.1,3.5,1.4,0.2,'Iris-setosa');
insert into iris  values(4.9,3,1.4,0.2,'Iris-setosa');
insert into iris  values(4.7,3.2,1.3,0.2,'Iris-setosa');
insert into iris  values(4.6,3.1,1.5,0.2,'Iris-setosa');
insert into iris  values(5,3.6,1.4,0.2,'Iris-setosa');
insert into iris  values(5.4,3.9,1.7,0.4,'Iris-setosa');
insert into iris  values(4.6,3.4,1.4,0.3,'Iris-setosa');
insert into iris  values(5,3.4,1.5,0.2,'Iris-setosa');
insert into iris  values(4.4,2.9,1.4,0.2,'Iris-setosa');
insert into iris  values(4.9,3.1,1.5,0.1,'Iris-setosa');
insert into iris  values(5.4,3.7,1.5,0.2,'Iris-setosa');
insert into iris  values(4.8,3.4,1.6,0.2,'Iris-setosa');
insert into iris  values(4.8,3,1.4,0.1,'Iris-setosa');
insert into iris  values(4.3,3,1.1,0.1,'Iris-setosa');
insert into iris  values(5.8,4,1.2,0.2,'Iris-setosa');
insert into iris  values(5.7,4.4,1.5,0.4,'Iris-setosa');
insert into iris  values(5.4,3.9,1.3,0.4,'Iris-setosa');
insert into iris  values(5.1,3.5,1.4,0.3,'Iris-setosa');
insert into iris  values(5.7,3.8,1.7,0.3,'Iris-setosa');
insert into iris  values(5.1,3.8,1.5,0.3,'Iris-setosa');
insert into iris  values(5.4,3.4,1.7,0.2,'Iris-setosa');
insert into iris  values(5.1,3.7,1.5,0.4,'Iris-setosa');
insert into iris  values(4.6,3.6,1,0.2,'Iris-setosa');
insert into iris  values(5.1,3.3,1.7,0.5,'Iris-setosa');
insert into iris  values(4.8,3.4,1.9,0.2,'Iris-setosa');
insert into iris  values(5,3,1.6,0.2,'Iris-setosa');
insert into iris  values(5,3.4,1.6,0.4,'Iris-setosa');
insert into iris  values(5.2,3.5,1.5,0.2,'Iris-setosa');
insert into iris  values(5.2,3.4,1.4,0.2,'Iris-setosa');
insert into iris  values(4.7,3.2,1.6,0.2,'Iris-setosa');
insert into iris  values(4.8,3.1,1.6,0.2,'Iris-setosa');
insert into iris  values(5.4,3.4,1.5,0.4,'Iris-setosa');
insert into iris  values(5.2,4.1,1.5,0.1,'Iris-setosa');
insert into iris  values(5.5,4.2,1.4,0.2,'Iris-setosa');
insert into iris  values(4.9,3.1,1.5,0.1,'Iris-setosa');
insert into iris  values(5,3.2,1.2,0.2,'Iris-setosa');
insert into iris  values(5.5,3.5,1.3,0.2,'Iris-setosa');
insert into iris  values(4.9,3.1,1.5,0.1,'Iris-setosa');
insert into iris  values(4.4,3,1.3,0.2,'Iris-setosa');
insert into iris  values(5.1,3.4,1.5,0.2,'Iris-setosa');
insert into iris  values(5,3.5,1.3,0.3,'Iris-setosa');
insert into iris  values(4.5,2.3,1.3,0.3,'Iris-setosa');
insert into iris  values(4.4,3.2,1.3,0.2,'Iris-setosa');
insert into iris  values(5,3.5,1.6,0.6,'Iris-setosa');
insert into iris  values(5.1,3.8,1.9,0.4,'Iris-setosa');
insert into iris  values(4.8,3,1.4,0.3,'Iris-setosa');
insert into iris  values(5.1,3.8,1.6,0.2,'Iris-setosa');
insert into iris  values(4.6,3.2,1.4,0.2,'Iris-setosa');
insert into iris  values(5.3,3.7,1.5,0.2,'Iris-setosa');
insert into iris  values(5,3.3,1.4,0.2,'Iris-setosa');
insert into iris  values(7,3.2,4.7,1.4,'Iris-versicolor');
insert into iris  values(6.4,3.2,4.5,1.5,'Iris-versicolor');
insert into iris  values(6.9,3.1,4.9,1.5,'Iris-versicolor');
insert into iris  values(5.5,2.3,4,1.3,'Iris-versicolor');
insert into iris  values(6.5,2.8,4.6,1.5,'Iris-versicolor');
insert into iris  values(5.7,2.8,4.5,1.3,'Iris-versicolor');
insert into iris  values(6.3,3.3,4.7,1.6,'Iris-versicolor');
insert into iris  values(4.9,2.4,3.3,1,'Iris-versicolor');
insert into iris  values(6.6,2.9,4.6,1.3,'Iris-versicolor');
insert into iris  values(5.2,2.7,3.9,1.4,'Iris-versicolor');
insert into iris  values(5,2,3.5,1,'Iris-versicolor');
insert into iris  values(5.9,3,4.2,1.5,'Iris-versicolor');
insert into iris  values(6,2.2,4,1,'Iris-versicolor');
insert into iris  values(6.1,2.9,4.7,1.4,'Iris-versicolor');
insert into iris  values(5.6,2.9,3.6,1.3,'Iris-versicolor');
insert into iris  values(6.7,3.1,4.4,1.4,'Iris-versicolor');
insert into iris  values(5.6,3,4.5,1.5,'Iris-versicolor');
insert into iris  values(5.8,2.7,4.1,1,'Iris-versicolor');
insert into iris  values(6.2,2.2,4.5,1.5,'Iris-versicolor');
insert into iris  values(5.6,2.5,3.9,1.1,'Iris-versicolor');
insert into iris  values(5.9,3.2,4.8,1.8,'Iris-versicolor');
insert into iris  values(6.1,2.8,4,1.3,'Iris-versicolor');
insert into iris  values(6.3,2.5,4.9,1.5,'Iris-versicolor');
insert into iris  values(6.1,2.8,4.7,1.2,'Iris-versicolor');
insert into iris  values(6.4,2.9,4.3,1.3,'Iris-versicolor');
insert into iris  values(6.6,3,4.4,1.4,'Iris-versicolor');
insert into iris  values(6.8,2.8,4.8,1.4,'Iris-versicolor');
insert into iris  values(6.7,3,5,1.7,'Iris-versicolor');
insert into iris  values(6,2.9,4.5,1.5,'Iris-versicolor');
insert into iris  values(5.7,2.6,3.5,1,'Iris-versicolor');
insert into iris  values(5.5,2.4,3.8,1.1,'Iris-versicolor');
insert into iris  values(5.5,2.4,3.7,1,'Iris-versicolor');
insert into iris  values(5.8,2.7,3.9,1.2,'Iris-versicolor');
insert into iris  values(6,2.7,5.1,1.6,'Iris-versicolor');
insert into iris  values(5.4,3,4.5,1.5,'Iris-versicolor');
insert into iris  values(6,3.4,4.5,1.6,'Iris-versicolor');
insert into iris  values(6.7,3.1,4.7,1.5,'Iris-versicolor');
insert into iris  values(6.3,2.3,4.4,1.3,'Iris-versicolor');
insert into iris  values(5.6,3,4.1,1.3,'Iris-versicolor');
insert into iris  values(5.5,2.5,4,1.3,'Iris-versicolor');
insert into iris  values(5.5,2.6,4.4,1.2,'Iris-versicolor');
insert into iris  values(6.1,3,4.6,1.4,'Iris-versicolor');
insert into iris  values(5.8,2.6,4,1.2,'Iris-versicolor');
insert into iris  values(5,2.3,3.3,1,'Iris-versicolor');
insert into iris  values(5.6,2.7,4.2,1.3,'Iris-versicolor');
insert into iris  values(5.7,3,4.2,1.2,'Iris-versicolor');
insert into iris  values(5.7,2.9,4.2,1.3,'Iris-versicolor');
insert into iris  values(6.2,2.9,4.3,1.3,'Iris-versicolor');
insert into iris  values(5.1,2.5,3,1.1,'Iris-versicolor');
insert into iris  values(5.7,2.8,4.1,1.3,'Iris-versicolor');
insert into iris  values(6.3,3.3,6,2.5,'Iris-virginica');
insert into iris  values(5.8,2.7,5.1,1.9,'Iris-virginica');
insert into iris  values(7.1,3,5.9,2.1,'Iris-virginica');
insert into iris  values(6.3,2.9,5.6,1.8,'Iris-virginica');
insert into iris  values(6.5,3,5.8,2.2,'Iris-virginica');
insert into iris  values(7.6,3,6.6,2.1,'Iris-virginica');
insert into iris  values(4.9,2.5,4.5,1.7,'Iris-virginica');
insert into iris  values(7.3,2.9,6.3,1.8,'Iris-virginica');
insert into iris  values(6.7,2.5,5.8,1.8,'Iris-virginica');
insert into iris  values(7.2,3.6,6.1,2.5,'Iris-virginica');
insert into iris  values(6.5,3.2,5.1,2,'Iris-virginica');
insert into iris  values(6.4,2.7,5.3,1.9,'Iris-virginica');
insert into iris  values(6.8,3,5.5,2.1,'Iris-virginica');
insert into iris  values(5.7,2.5,5,2,'Iris-virginica');
insert into iris  values(5.8,2.8,5.1,2.4,'Iris-virginica');
insert into iris  values(6.4,3.2,5.3,2.3,'Iris-virginica');
insert into iris  values(6.5,3,5.5,1.8,'Iris-virginica');
insert into iris  values(7.7,3.8,6.7,2.2,'Iris-virginica');
insert into iris  values(7.7,2.6,6.9,2.3,'Iris-virginica');
insert into iris  values(6,2.2,5,1.5,'Iris-virginica');
insert into iris  values(6.9,3.2,5.7,2.3,'Iris-virginica');
insert into iris  values(5.6,2.8,4.9,2,'Iris-virginica');
insert into iris  values(7.7,2.8,6.7,2,'Iris-virginica');
insert into iris  values(6.3,2.7,4.9,1.8,'Iris-virginica');
insert into iris  values(6.7,3.3,5.7,2.1,'Iris-virginica');
insert into iris  values(7.2,3.2,6,1.8,'Iris-virginica');
insert into iris  values(6.2,2.8,4.8,1.8,'Iris-virginica');
insert into iris  values(6.1,3,4.9,1.8,'Iris-virginica');
insert into iris  values(6.4,2.8,5.6,2.1,'Iris-virginica');
insert into iris  values(7.2,3,5.8,1.6,'Iris-virginica');
insert into iris  values(7.4,2.8,6.1,1.9,'Iris-virginica');
insert into iris  values(7.9,3.8,6.4,2,'Iris-virginica');
insert into iris  values(6.4,2.8,5.6,2.2,'Iris-virginica');
insert into iris  values(6.3,2.8,5.1,1.5,'Iris-virginica');
insert into iris  values(6.1,2.6,5.6,1.4,'Iris-virginica');
insert into iris  values(7.7,3,6.1,2.3,'Iris-virginica');
insert into iris  values(6.3,3.4,5.6,2.4,'Iris-virginica');
insert into iris  values(6.4,3.1,5.5,1.8,'Iris-virginica');
insert into iris  values(6,3,4.8,1.8,'Iris-virginica');
insert into iris  values(6.9,3.1,5.4,2.1,'Iris-virginica');
insert into iris  values(6.7,3.1,5.6,2.4,'Iris-virginica');
insert into iris  values(6.9,3.1,5.1,2.3,'Iris-virginica');
insert into iris  values(5.8,2.7,5.1,1.9,'Iris-virginica');
insert into iris  values(6.8,3.2,5.9,2.3,'Iris-virginica');
insert into iris  values(6.7,3.3,5.7,2.5,'Iris-virginica');
insert into iris  values(6.7,3,5.2,2.3,'Iris-virginica');
insert into iris  values(6.3,2.5,5,1.9,'Iris-virginica');
insert into iris  values(6.5,3,5.2,2,'Iris-virginica');
insert into iris  values(6.2,3.4,5.4,2.3,'Iris-virginica');
insert into iris  values(5.9,3,5.1,1.8,'Iris-virginica');

create table playtennis(
outlook varchar(10),
temperature float(5,2),
umidity varchar (10),
wind varchar (10),
play varchar (10));

insert into playtennis values('sunny',30.3,'high','weak','no');
insert into playtennis values('sunny',30.3,'high','strong','no');
insert into playtennis values('overcast',30.0,'high','weak','yes');
insert into playtennis values('rain',13.0,'high','weak','yes');
insert into playtennis values('rain',0.0,'normal','weak','yes');
insert into playtennis values('rain',0.0,'normal','strong','no');
insert into playtennis values('overcast',0.1,'normal','strong','yes');
insert into playtennis values('sunny',13.0,'high','weak','no');
insert into playtennis values('sunny',0.1,'normal','weak','yes');
insert into playtennis values('rain',12.0,'normal','weak','yes');
insert into playtennis values('sunny',12.5,'normal','strong','yes');
insert into playtennis values('overcast',12.5,'high','strong','yes');
insert into playtennis values('overcast',29.21,'normal','weak','yes');
insert into playtennis values('rain',12.5,'high','strong','no');

create database estensionedb;
use estensionedb;
GRANT CREATE, SELECT, INSERT, DELETE ON CFP17_matricola.* TO stigliano_642472_17 IDENTIFIED BY 'CFP17password';
GRANT ALL PRIVILEGES ON *.* TO 'stigliano_642472_17'@'%' WITH GRANT OPTION;

create table player(
nome varchar(25),
mediapartita float (5,2),
cognome varchar(25),
squadra varchar(10));

insert into player values('nicola',10.0,'stigliano','inter');
insert into player values('pierpaolo',7.3,'masella','juve');
insert into player values('fabio',3.0,'roberti','juve');
insert into player values('domenico',6.2,'zagaria','fiorentina');
insert into player values('raffaello',5.6,'passarelli','milan');
insert into player values('stefano',10.0,'romanelli','roma');
insert into player values('gianmarco',10.0,'ninno','inter');
insert into player values('michele',2.5,'nitti','roma');
insert into player values('paolo',9.7,'pastore','lazio');
insert into player values('gerardo',8.5,'melanoscina','milan');
insert into player values('giovanni',null,'siena','juve');
insert into player values('francesco',2.5,'vultaggio','juve');
insert into player values('vito',9.21,'recchia','milan');
insert into player values('gaetano',9.7,'settembre','juve');

create table capocannoniere(
nome varchar(25),
mediaopportunità float (5,2),
cognome varchar(25),
goal float(10));

insert into capocannoniere values('mauro',170.3,'icardi','22.0');
insert into capocannoniere values('luca',170.3,'toni','22.0');
insert into capocannoniere values('francesco',203.0,'totti','26.0');
insert into capocannoniere values('edin',176.2,'dzeko','29.0');
insert into capocannoniere values('zlatan',195.6,'ibrahimovic','28.0');
insert into capocannoniere values('antonio',195.6,'dinatale','28.0');
insert into capocannoniere values('ciro',170.3,'immobile','22.0');
insert into capocannoniere values('gonzalo',225.5,'higuain','36.0');
insert into capocannoniere values('christian',189.7,'vieri','24.0');
insert into capocannoniere values('filippo',180.5,'inzaghi','24.0');
insert into capocannoniere values('alessandro',null,'delpiero','21.0');
insert into capocannoniere values('edinson',202.5,'cavani','29.0');
