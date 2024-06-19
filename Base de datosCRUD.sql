CREATE TABLE TbUsuarios (
    uuidUsarios varchar2(50) primary key not NULL,
    nombre VARCHAR2(100) NOT NULL,
    email VARCHAR2(100) UNIQUE NOT NULL,
    contraseña VARCHAR2(100) NOT NULL
);

select * from usuarios

CREATE TABLE TbTickets (
    uuid varchar2(50) primary key not null,
    titulo VARCHAR2(200) NOT NULL,
    descripcion varchar2(50) NOT NULL,
    autor varchar2(50) not null,
    emailContacto VARCHAR2(100) NOT NULL,
    fechaCreacion VARCHAR2 (20) NOT NULL,
    estado VARCHAR2(20) NOT NULL,
    fechaFinalizacion VARCHAR2 (20) NOT NULL
);

select * from tbTickets

INSERT INTO tbTickets
VALUES (1,'Error en la aplicación', 'La aplicación se cierra inesperadamente.', 'Juan Pérez', 'juan.perez@example.com', '2024-06-15', 'Activo', '2024-06-15');


drop table tbUsuarios

drop table tbTickets
