drop  DATABASE IF EXISTS superKinal;

CREATE DATABASE IF NOT EXISTS superKinal;

use superKinal;

create table Clientes( --
	clienteId int not null auto_increment,
    nombre varchar(30) not null,
    apellido varchar(30) not null,
    telefono varchar(15),
    direccion varchar(150) not null,
    nit varchar(15) default 'CF', 
    
    primary key PK_clienteId(clienteId)
); 

create table Cargos( --
	cargoId int not null auto_increment,
    nombreCargo varchar(30) not null,
    descripcionCargo varchar(100) not null,
    
    primary key PK_cargoId(cargoId)
);

create table Compras ( -- 3
	compraId int not null auto_increment,
    fechaCompra date not null,
    totalCompra decimal (10,2),
    
    primary key PK_compraId(compraId)
);

create table CategoriaProductos( --
	categoriaProductoId int  not null auto_increment,
    nombreCategoria varchar(30) not null,
    descripcionCategoria varchar(100) not null,
    
    primary key PK_categoriaProductoId(categoriaProductoId)
);

create table Distribuidores( -- 
	distribuidorId int not null auto_increment,
    nombreDistribuidor varchar (30)  not null,
    direccionDistribuidor varchar (200) not null,
    nitDistribuidor varchar (15) not null,
    telefonoDistribuidor varchar(15) not null,
    web varchar (50),
    
    primary key PK_distribuidorId(distribuidorId)
);

create table Productos(
	productoId int not null auto_increment,
    nombreProducto varchar(50) not null,
    descripcionProducto varchar(100) ,
    cantidadStock int not null,
    precioVentaUnitario decimal (10,2) not null,
    precioVentaMayor decimal(10,2) not null,
    precioCompra decimal(10,2) not null,
	imagen longblob,
	distribuidorId int not null,
	categoriaProductoId int not null,
    
    primary key PK_productoId(productoId),
	constraint FK_Productos_Distribuidores foreign key Productos(distribuidorId)
		references Distribuidores(distribuidorId),
	constraint FK_Productos_CategoriaProductos foreign key Productos(categoriaProductoId)
		references CategoriaProductos(categoriaProductoId)
);

create table DetallesCompras( -- 3
	detalleCompraId int not null auto_increment,
    cantidadCompra int not null,
    productoId int not null,
    compraId int not null,
    
    primary key PK_detalleCompraId(detalleCompraId),
    constraint FK_DetallesCompras_Productos foreign key DetallesCompras(productoId)
		references Productos(productoId),
    constraint FK_DetallesCompras_Compras foreign key DetallesCompras(compraId)
		references Compras(compraId)
    
);

create table Promociones(
	promocionId int not null auto_increment,
    precioPromocion decimal(10,2)not null,
    descripcionPromocion varchar(1000),
	fechaInicio date not null,
    fechaFinalizacion date not null,
    productoId int not null,
    
    primary key PK_promocionId(promocionId),
    constraint FK_Promociones_Productos foreign key Promociones(productoId)
		references Productos(productoId)
);

create table Empleados(
	empleadoId int not null auto_increment,
    nombreEmpleado varchar(30) not null,
    apellidoEmpleado varchar (30) not null,
    sueldo decimal (10,2) not null,
    horaEntrada time not null,
    horaSalida time not null,
    cargoId int not null,
    encargadoId int ,
    
    primary key PK_Empleados(empleadoId),
    constraint FK_Empleados_Cargos foreign key Empleados(cargoId)
		references Cargos(cargoId),
	constraint FK_encargadoId foreign key Empleados(encargadoId)
		references Empleados(empleadoId)

);

create table Facturas(
	facturaId int not null auto_increment,
    fecha date not null,
    hora time not null,
    clienteId int not null,
	empleadoId int not null,
    total decimal (10,2),
    
    primary key PK_Facturas(facturaId),
    constraint FK_Facturas_Clientes foreign key Facturas(clienteId)
		references Clientes (clienteId),
	constraint PK_Facturas_Empleados foreign key Facturas(empleadoId)
		references Empleados(empleadoId)
);

create table DetallesFacturas(
	detalleFacturaId int not null auto_increment,
    facturaId int not null,
    productoId int not null,
    
    primary key PK_detalleFacturaId(detalleFacturaId),
    constraint FK_DetallesFacturas_Facturas foreign key DetallesFacturas(facturaId)
		references Facturas(facturaId),
	constraint FK_DetallesFacturas_Productos foreign key DetallesFacturas(productoId)
		references Productos(productoId)
);

create table TicketsSoportes( --
	ticketSoporteId int not null auto_increment,
    descripcionTicket varchar(250) not null,
    estatus varchar (30) not null,
    clienteId int not null,
    facturaId int null,
    
	primary key PK_TicketsSoportes(ticketSoporteId),
    constraint FK_TicketsSoportes_Clientes foreign key TicketsSoportes(clienteId)
		references Clientes(clienteId),
	constraint FK_TicketsSoportes_Facturas foreign key TicketsSoportes(facturaId)
		references Facturas(facturaId)
	
);

create table NivelesAcceso(
	nivelAccesoId int not null auto_increment,
    nivelAcceso varchar(40) not null,
    
    primary key PK_nivelAccesoId(nivelAccesoId)
);

create table Usuarios(
	usuarioId int not null auto_increment,
    usuario varchar(30) not null,
    contrasenia varchar(100) not null,
    nivelAccesoId int not null,
    empleadoId int not null,
    
    primary key PK_usuarioId(usuarioId),
    constraint FK_Usuarios_NivelesAcceso foreign key Usuarios(nivelAccesoId)
		references NivelesAcceso(nivelAccesoId),
	constraint PK_Usuarios_Empleados foreign key Usuarios(empleadoId)
		references Empleados(empleadoId)
);

insert into Clientes(nombre,apellido,telefono,direccion,nit)values
		('José ','Alfredo ','2193-3234','Mixco','3124324'),
		('Jorge ',' Peralta ','3235-4345','Chinique Quiche','4253423');      
        
insert into TicketsSoportes(descripcionTicket,estatus,clienteId,facturaId)values
		('error al iniciar','en proceso',1,null);
        
insert into Cargos(nombreCargo,descripcionCargo)values
		('Jefe','Dueño de la empresa'),
		('Empleado ','ayuda a vender en la tienda '),
		('Secretaria','Asiste al jefe');
        
        
insert into Empleados(nombreEmpleado,apellidoEmpleado,sueldo,horaEntrada,horaSalida,cargoId)values
		('José','Alfredo',55000.00 ,'06:30:00','17:30:00',1),
		('Manuel','Parras',3000.00 ,'06:30:00','17:30:00',2),
		('Abi','Lopez',4000.00 ,'06:30:00','17:30:00',3);

insert into Facturas(fecha,hora,clienteId,empleadoId,total)values         
		('2024-05-02','13:58:34',1,1,null);
        
insert into CategoriaProductos(nombreCategoria,descripcionCategoria)values
		('Jefe ','Dueño de la Empresa ');
        
insert into Distribuidores(nombreDistribuidor,direccionDistribuidor,nitDistribuidor,telefonoDistribuidor,web)values
		('Pedro','Zona 1 Ciudad de Guatemala','CF','7392-7361','link');

insert into Productos(nombreProducto,descripcionProducto,cantidadStock,precioVentaUnitario,precioVentaMayor,precioCompra,imagen,distribuidorId,categoriaProductoId)values
 		('Pancito','Alimento echo a base de pan ',100,10.00,6.00,300.00,null,1,1);

insert into NivelesAcceso(nivelAcceso)values
		('Administrador'),
		('Usuarios');
insert into Usuarios(usuario,contrasenia,nivelAccesoId,empleadoId)values
		('Jchepe', '$2a$10$IYgQkW2ADTG.rpZxiA9HquLd.voYWyY/7iu6a5m25RTAZygbw424W',1, 1);
