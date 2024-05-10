use superKinal;

----- Clientes -------
DELIMITER $$ 
CREATE PROCEDURE sp_AgregarCliente(IN nom VARCHAR (30), IN ape VARCHAR (30), IN tel VARCHAR (15), IN dir VARCHAR (150), IN ni varchar(15))
BEGIN 	
	INSERT INTO Clientes (nombre, apellido,telefono,direccion,nit)VALUES 
		(nom, ape,tel,dir,ni);
END$$
DELIMITER ;

call sp_AgregarCliente('Leonel','Messi','3243-4325','Buenos Aires','423432523');

DELIMITER $$ 
CREATE PROCEDURE sp_ListarClientes()
BEGIN 
	SELECT
		Clientes.clienteId,
		Clientes.nombre,
		Clientes.apellido,
		Clientes.telefono,
		Clientes.direccion,
		Clientes.nit
			FROM Clientes;
END$$
DELIMITER ;

DELIMITER $$ 
CREATE PROCEDURE sp_EliminarCliente(IN cliId INT)
BEGIN
	DELETE
	FROM Clientes 
		WHERE clienteId =  cliId;
END$$
DELIMITER ;

	
-- CALL sp_EliminarCliente(3);

DELIMITER $$ 
CREATE PROCEDURE sp_BuscarClientes(IN cliId INT)
BEGIN
	SELECT 	
		Clientes.clienteId,
		Clientes.nombre,
		Clientes.apellido,
		Clientes.telefono,
		Clientes.direccion,
		Clientes.nit
			FROM Clientes
			WHERE clienteId = cliId;
END$$
DELIMITER ;

-- CALL sp_BuscarClientes(2);

DELIMITER $$ 
CREATE PROCEDURE sp_EditarCliente(IN cliId INT,IN nom VARCHAR (30), IN ape VARCHAR (30), IN tel VARCHAR(15), IN dir VARCHAR(150), IN ni varchar(15))
BEGIN
	UPDATE Clientes
		SET
			nombre = nom,
			apellido = ape,
			telefono = tel,
			direccion = dir,
            nit = ni
			WHERE clienteId = cliId;
END$$
DELIMITER ;
CALL sp_EditarCliente(3, 'Daniela' , 'Flores', '3273-9102' , 'Mixco','242323523');

call sp_ListarClientes();

-- Cargos 

DELIMITER $$ 
CREATE PROCEDURE sp_AgregarCargos(IN nom VARCHAR (30), IN des VARCHAR (100))
BEGIN 	
	INSERT INTO Cargos (nombreCargo , descripcionCargo)VALUES 
		(nom, des);
END$$
DELIMITER ;

call sp_AgregarCargos('Jefe','dueño de la empresa');

DELIMITER $$ 
CREATE PROCEDURE sp_ListarCargos()
BEGIN 
	SELECT
		Cargos.cargoId,
		Cargos.nombreCargo,
		Cargos.descripcionCargo  
			FROM Cargos;
END$$
DELIMITER ;

call sp_ListarCargos();

DELIMITER $$ 
CREATE PROCEDURE sp_EliminarCargos(IN carId INT)
BEGIN
	DELETE
	FROM Cargos 
		WHERE cargoId =  carId;
END$$
DELIMITER ;

-- call sp_EliminarCargos(6);

DELIMITER $$ 
CREATE PROCEDURE sp_BuscarCargos(IN carId INT)
BEGIN
	SELECT 	
		Cargos.cargoId ,
		Cargos.nombreCargo  ,
		Cargos.descripcionCargo  
			FROM Cargos
			WHERE cargoId = carId;
END$$
DELIMITER ;

-- call sp_BuscarCargos(1);
DELIMITER $$ 
CREATE PROCEDURE sp_EditarCargos(IN carId INT,IN nom VARCHAR (30), IN des VARCHAR (100))
BEGIN
	UPDATE Cargos
		SET
			nombreCargo  = nom,
			descripcionCargo  = des
			WHERE cargoId  = carId;
END$$
DELIMITER 

call sp_EditarCargos(2,'Oficinista','trabaja en las oficinas ');

-- Compras 
DELIMITER $$ 
CREATE PROCEDURE sp_AgregarCompras(IN fec date, IN tot  decimal (10.2))
BEGIN 	
	INSERT INTO Compras (fechaCompra,totalCompra )VALUES 
		(fec, tot);
END$$
DELIMITER ;

call sp_AgregarCompras('2024-05-09',35.24);

DELIMITER $$ 
CREATE PROCEDURE sp_ListarCompras()
BEGIN 
	SELECT
		Compras.compraId ,
		Compras.fechaCompra, 
		Compras.totalCompra  
			FROM Compras;
END$$
DELIMITER ;

call sp_ListarCompras();

DELIMITER $$ 
CREATE PROCEDURE sp_EliminarCompras(IN compId  INT)
BEGIN
	DELETE
	FROM Compras 
		WHERE compraId  =  compId;
END$$
DELIMITER ;

DELIMITER $$ 
CREATE PROCEDURE sp_BuscarCompras(IN compId INT)
BEGIN
	SELECT 	
		Compras.compraId,
		Compras.fechaCompra, 
		Compras.totalCompra 
			FROM Compras
			WHERE compraId = compId;
END$$
DELIMITER ;

DELIMITER $$ 
CREATE PROCEDURE sp_EditarCompras(IN compId INT,IN fec date, IN tot  decimal (10.2))
BEGIN
	UPDATE Compras
		SET
			fechaCompra   = fec,
			totalCompra   = tot
			WHERE compraId   = compId;
END$$
DELIMITER ;

-- CategoriaProductos
DELIMITER $$ 
CREATE PROCEDURE sp_AgregarCategoriaProductos(IN nom VARCHAR (30), IN des VARCHAR (100))
BEGIN 	
	INSERT INTO CategoriaProductos (nombreCategoria , descripcionCategoria )VALUES 
		(nom, des);
END$$
DELIMITER ;

call sp_AgregarCategoriaProductos('Comida','solo comida de Guatemala');

DELIMITER $$ 
CREATE PROCEDURE sp_ListarCategoriaProductos()
BEGIN 
	SELECT
		CategoriaProductos.categoriaProductoId, 
		CategoriaProductos.nombreCategoria,  
		CategoriaProductos.descripcionCategoria  
			FROM CategoriaProductos;
END$$
DELIMITER ;

call sp_ListarCategoriaProductos();

DELIMITER $$ 
CREATE PROCEDURE sp_EliminarCategoriaProductos(IN catId  INT)
BEGIN
	DELETE
	FROM CategoriaProductos 
		WHERE categoriaProductoId  =  catId;
END$$
DELIMITER ;

-- call sp_EliminarCategoriaProductos(4);

DELIMITER $$ 
CREATE PROCEDURE sp_BuscarCategoriaProductos(IN catId INT)
BEGIN
	SELECT 	
		CategoriaProductos.categoriaProductoId, 
		CategoriaProductos.nombreCategoria,  
		CategoriaProductos.descripcionCategoria  
			FROM CategoriaProductos
			WHERE categoriaProductoId  = catId;	
END$$
DELIMITER ;
-- call sp_BuscarCategoriaProductos(1);

DELIMITER $$ 
CREATE PROCEDURE sp_EditarCategoriaProductos(IN catId INT,IN nom VARCHAR (30), IN des VARCHAR (100))
BEGIN
	UPDATE CategoriaProductos
		SET
			nombreCategoria  = nom,
			descripcionCategoria = des
			WHERE categoriaProductoId = catId;
END$$
DELIMITER ;

-- call sp_EditarCategoriaProductos(1,'ahgwd','sadas');

-- Distribuidores 
DELIMITER $$ 
CREATE PROCEDURE sp_AgregarDistribuidores(IN nom VARCHAR (30),IN dir VARCHAR (200), IN nit varchar(15), IN tel varchar(15),  IN web varchar(50))
BEGIN 	
	INSERT INTO Distribuidores (nombreDistribuidor , direccionDistribuidor ,nitDistribuidor ,telefonoDistribuidor ,web )VALUES 
		(nom, dir,nit,tel,web);
END$$
DELIMITER ; 

-- call sp_AgregarDistribuidores('as','as','as','as','as');
DELIMITER $$ 
CREATE PROCEDURE sp_ListarDistribuidores()
BEGIN 
	SELECT
		Distribuidores.distribuidorId,
		Distribuidores.nombreDistribuidor,
		Distribuidores.direccionDistribuidor,
		Distribuidores.nitDistribuidor,
		Distribuidores.telefonoDistribuidor,
		Distribuidores.web 
			FROM Distribuidores;
END$$
DELIMITER ;

-- call sp_ListarDistribuidores();

DELIMITER $$ 
CREATE PROCEDURE sp_EliminarDistribuidores(IN disId INT)
BEGIN
	DELETE
	FROM Distribuidores 
		WHERE distribuidorId =  disId;
END$$
DELIMITER ;

DELIMITER $$ 
CREATE PROCEDURE sp_BuscarDistribuidores(IN disId INT)
BEGIN
	SELECT 	
		Distribuidores.distribuidorId,
		Distribuidores.nombreDistribuidor,
		Distribuidores.direccionDistribuidor,
		Distribuidores.nitDistribuidor,
		Distribuidores.telefonoDistribuidor,
		Distribuidores.web 
			FROM Distribuidores
			WHERE distribuidorId = disId;
END$$
DELIMITER ;

DELIMITER $$ 
CREATE PROCEDURE sp_EditarDistribuidores(IN disId INT,IN nom VARCHAR (30),IN dir VARCHAR (200), IN nit varchar(15), IN tel varchar(15),  IN web varchar(50))
BEGIN
	UPDATE Distribuidores
		SET
			nombreDistribuidor = nom,
			direccionDistribuidor = dir,
			nitDistribuidor = nit,
			telefonoDistribuidor = tel ,
            web = web
			WHERE clienteId = cliId;
END$$
DELIMITER ;

-- Productos
DELIMITER $$
create procedure sp_AgregarProducto(in nom varchar(50),in des varchar(100),in can int, in preU decimal(10,2),in preM decimal(10,2),in preC decimal(10,2), in ima blob, in disId int, in catId int)
	BEGIN
		insert into Productos(nombreProducto, descripcionProducto, cantidadStock, precioUnitario, precioVentaMayor, precioCompra, imagenProducto, distribuidorId, categoriaProductosId ) values
			(nom, des, can, preU, preM, preC, ima, disId, catId);
	END $$
DELIMITER ;

DELIMITER $$
create procedure sp_ListarProducto()
	BEGIN 
		select * from Productos;
    END $$
DELIMITER ;

DELIMITER $$
create procedure sp_BuscarProducto(in proId int)
	BEGIN 
		select * from Productos
        where productoId = proId;
    END $$
DELIMITER ;

DELIMITER $$
create procedure sp_EliminarProducto(in proId int)
	BEGIN
		delete from Productos
			where productoId = proId;
    END $$
DELIMITER ;

DELIMITER $$
create procedure sp_EditarProducto(in proId int, in nom varchar(50),in des varchar(100),in can int, in preU decimal(10,2),in preM decimal(10,2),in preC decimal(10,2), in ima blob, in disId int, in catId int )
	BEGIN
		update Productos	
			set 
            nombreProducto = nom,
            descripcionProduto = des,
            cantidadStock = can,
            precioVentaUnitario = preU,
            precioVentaMayor = preM,
            precioCompra = preC,
            imagenProducto = ima,
            distribuidorId = disId,
            categoriaProductosId = catId
            where productoId = proId;
    END $$
DELIMITER ;
-- DetalleCompra
DELIMITER $$
create procedure sp_AgregarDetallesCompras(in canC int, in proId int, in comId int)
	BEGIN
		insert into DetallesCompras(cantidadCompra, productoId, compraId) values
			(canC, proId, comId);
    END $$
DELIMITER ;

-- call sp_AgregarDetallesCompras(5,null,1);

DELIMITER $$
create procedure sp_ListarDetallesCompras()
BEGIN
	select
		DC.detalleCompraId,
		DC.cantidadCompra,
		DC.productoId,
		DC.compraId
			from DetallesCompras DC;
END $$
DELIMITER ;

call sp_ListarDetallesCompras();

DELIMITER $$
create procedure sp_BuscarDetalleCompra(in detCId int)
	BEGIN
		select * from DetalleCompra
				where detalleCompraId = detCId;
    END $$
DELIMITER ;

DELIMITER $$
create procedure sp_EliminarDetalleCompra(in detCId int)
	BEGIN
		delete
			from DetalleCompra
				where detalleCompraId = detCId;
    END $$
DELIMITER ;

DELIMITER $$
create procedure sp_EditarDetalleCompra(in detCId int, in canC int, in proId int, in comId int)
	BEGIN
		update DetalleCompra
			set
				cantidadCompra = canC,
                productoId = proId,
                compraId = comId
					where detalleCompraId = detCId;
    END $$
DELIMITER ;
-- Promociones
DELIMITER $$
create procedure sp_AgregarPromociones(prePro decimal (10,2), desPro varchar (100), fecI date, fecF date, in proId int)
BEGIN
	insert into Promociones(precioPromocion, descripcionPromocion, fechaInicio, fechaFinalizacion, productoId )values
    (prePro,desPro, fecI, fecF, proId);
    
END $$
DELIMITER ;

DELIMITER $$
create procedure sp_ListarPromociones()
BEGIN
	select
    Promociones.promocionId,
    Promociones.precioPromocion,
    Promociones.descripcionPromocion,
    Promociones.fechaInicio,
    Promociones.fechaFinalizacion,
    Promociones.productoId
		FROM Promociones;

END $$
DELIMITER ;

DELIMITER $$
create procedure sp_EliminarPromociones(in proId int)
BEGIN
	delete
		from Promociones
        where promocionId = proId;
END $$
DELIMITER ;

DELIMITER $$
create procedure sp_BuscarPromociones(in proId int)
BEGIN
	select
		Promociones.promocionId,
        Promociones.precioPromocion,
        Promociones.descripcionPromocion,
        Promociones.fechaInicio,
        Promociones.fechaFinalizacion,
        Promociones.productoId
			from Promociones
            where promocionId = proId;
END $$
DELIMITER  ;

DELIMITER $$
create procedure sp_EditarPromociones(in promId int, prePro decimal (10,2), desPro varchar (100), fecI date, fecF date, in proId int )
BEGIn
	update Promociones
		set
			precioPromocion = prePro,
            descripcionPromocion = desPro,
            fechaInicio = fecI,
            fechFinalizacion = fecF,
            profuctoId = proId
            where promocionId = promId;
END $$
DELIMITER ;

-- Empleados
delimiter $$
	create procedure sp_AgregarEmpleados (in nomEmp varchar (30), in apeEmp  varchar (30), in sud decimal (10, 2), in horEntr time, in horSld time, in cargId int, in encaId int)
		begin 
			insert into Empleados (nombreEmpleado , apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargoId, encargadoId)
				values (nomEmp, apeEmp, sud, horEntr, horSld, cargId, encaId);
		end$$
delimiter ;


delimiter $$
	create procedure sp_ListarEmpleados ()
		begin 
			select 
				Empleados.nombreEmpleado,
                Empleados.apellidoEmpleado,
                Empleados.sueldo,
                Empleados.horaEntrada,
                Empleados.horaSalida,
                Empleados.cargoId,
                Empleados.encargadoId
					FROM Empleados;
		end $$
delimiter ;

delimiter $$
	create procedure sp_EliminarEmpleados (in empId int)
		begin
			delete
				from Empleados
					where empleadoId = empId;
		end $$
delimiter ;

delimiter $$
	create procedure sp_BuscarEmpleados (in empId int)
		begin 
			select
				Empleados.nombreEmpleado,
                Empleados.apellidoEmpleado,
                Empleados.sueldo,
                Empleados.horaEntrada,
                Empleados.horaSalida,
                Empleados.cargoId,
                Empleados.encargadoId
					from Empleados 
						where empleadoId = empId;
		end $$
delimiter ;

delimiter $$
	create procedure sp_EditarEmpleados (in empId int, in nomEmp varchar (30), in apeEmp  varchar (30), in sud decimal (10, 2), in horEntr time, in horSld time, in cargId int, in encaId int)
		begin
			update Empleados
				set	
					nombreEmpleado = nomEmp,
					apellidoEmpleado = apeEmp,
					sueldo = sud,
					horaEntrada = horEntr, 
					horaSalida = horSld,
					cargoId = cargId,
					encargadoId = encaId
					where empleadoId = empId;
		end $$
delimiter ;

-- FACTURAS --

delimiter $$
	create procedure sp_AgregarFacturas (in fech date, in hor time, in tot decimal (10, 2), in cliId int, in empId int)
		begin 
			insert into Facturas (fecha, hora, total, clienteId, empleadoId)
				values (fech, hor, tot, cliId, empId);
		end$$
delimiter ;

delimiter $$
	create procedure sp_ListarFacturas ()
		begin 
			select 
				Facturas.facturaId,
				Facturas.fecha,
                Facturas.hora,
                Facturas.total,
                Facturas.clienteId,
                Facturas.empleadoId
					FROM Facturas;
		end $$
delimiter ;

delimiter $$
	create procedure sp_EliminarFacturas (in facId int)
		begin
			delete
				from Facturas
					where facturaId = facId;
		end $$
delimiter ;

delimiter $$
	create procedure sp_BuscarFacturas (in facId int)
		begin 
			select
				Facturas.fecha,
                Facturas.hora,
                Facturas.total,
                Facturas.clienteId,
                Facturas.empleadoId
					from Facturas
						where facturaId = facId;
		end $$
delimiter ;

delimiter $$
	create procedure sp_EditarFacturas (in facId int, in fech date, in hor time, in tot decimal (10, 2), in cliId int, in empId int)
		begin
			update Facturas
				set	
					fecha = fech,
					hora = hor,
					total = tot,
					clienteId = cliId,
					empleadoId = empId
					where facturaId = facId;
		end $$
delimiter ;

-- DETALLE FACTURA --
delimiter $$
	create procedure sp_AgregarDetalleFactura  (in factId int, in proId int)
		begin 
			insert into DetalleFactura  (facturaId, productoId)
				values (factId, proId);
		end$$
delimiter ;

delimiter $$
	create procedure sp_ListarDetalleFactura  ()
		begin 
			select 
				DetalleFactura.detalleFacturaId,
				DetalleFactura.facturaId,
                DetalleFactura.productoId
					FROM DetalleFactura;
		end $$
delimiter ;

delimiter $$
	create procedure sp_EliminarDetalleFactura   (in detaFacId int)
		begin
			delete
				from DetalleFactura  
					where detalleFacturaId  = detaFacId;
		end $$
delimiter ;

delimiter $$
	create procedure sp_BuscarDetalleFactura  (in detaFacId int)
		begin 
			select
				DetalleFactura.facturaId,
                DetalleFactura.productoId
					from DetalleFactura 
						where detalleFacturaId  = detaFacId;
		end $$
delimiter ;

delimiter $$
	create procedure sp_EditarDetalleFactura  (in detaFacId int, in factId int, in proId int)
		begin
			update DetalleFactura 
				set	
					facturaId = factId,
					productoId = proId
					where detalleFacturaId  = detaFacId;
		end $$
delimiter ;

-- TICKET SOPORTE --
delimiter $$
create procedure sp_AgregarTicketSoportes(in des varchar (250),in cliId int, in facId int)
begin 
	insert into TicketsSoportes (descripcionTicket,estatus,clienteId , facturaId)
	values (des,'Recien Creado', cliId, facId);
end$$
delimiter ;
 
call sp_AgregarTicketSoportes('chepe tiene error en la base de datos',2,1);
 
 
DELIMITER $$
CREATE PROCEDURE sp_listarTicketsSoportes()
BEGIN
    select TS.ticketSoporteId, TS.descripcionTicket, TS.estatus,
			CONCAT('Id: ', C.clienteId, ' | ', C.nombre, ' ', C.apellido) AS 'cliente',
            TS.facturaId from TicketsSoportes TS
    join Clientes C on TS.clienteId = C.clienteId;
END $$
DELIMITER ;
 
call sp_listarTicketsSoportes();

delimiter $$
	create procedure sp_EliminarTicketSoporte  (in tickSopId int)
		begin
			delete
				from TicketSoporte 
					where ticketSoporteId = tickSopId;
		end $$
delimiter ;

delimiter $$
	create procedure sp_BuscarTicketSoporte (in tickSopId int)
		begin 
			select
				TicketSoporte.descripcionTicket,
                TicketSoporte.estatuts,
                TicketSoporte.clienteId,
                TicketSoporte.facturaId
					from TicketSoporte
						where ticketSoporteId = tickSopId;
		end $$
delimiter ;

delimiter $$
create procedure sp_EditarTicketsSoportes(in tickSopId int, in des varchar (250), in est varchar (30), in cliId int, in facId int)
begin
	update TicketsSoportes
	set	
		descripcionTicket = des,
		estatus = est,
		clienteId = cliId,
		facturaId = facId
	where ticketSoporteId = tickSopId;
end $$
delimiter ;

call sp_EditarTicketsSoportes(1,'error al iniciar','Finalizado',1,1);

set global time_zone = '-6:00';
