use superKinal;

delimiter $$
create procedure sp_usodeStocks(in proId int)
begin
	update Productos
		set
			cantidadStock = cantidadStock - 1
            where productoId = proId;
end$$
delimiter ;

-- call sp_usodeStocks(2);


delimiter $$
create trigger tg_restardeStock
before insert on detallesFacturas
for each row
begin
    if (select P.cantidadStock from Productos P where productoId = NEW.productoId) = 0 then
		signal sqlstate'45000'
			set message_text="no tenemos de ese producto lo sentimos";
    else
		call sp_usodeStocks(new.productoId);
	end if;
 
end $$
delimiter ;

delimiter $$
create function fn_veriClientes(nt int) returns boolean deterministic
begin
	declare flag boolean;
    declare veri int;
    set veri = (select count(*) from Clientes where nit = nt);
    if veri = 1 then
		set flag = true;
	else
		set flag = false;
	end if;
    return flag;	
end $$
delimiter ;

delimiter $$
create trigger veri_Clientes
before insert on Facturas
for each row
begin
	declare veri boolean;
    set veri = fn_veriClientes(new.clienteId);
    if !veri then
		signal sqlstate '45000'
			set message_text = "No es posible generar la factura debido a que el cliente no está registrado en nuestro sistema";
    end if;
end $$
delimiter ;

delimiter $$
create function fn_totalFacturas(facId int) returns decimal(10, 2) deterministic
	begin
		declare total int default 0;
        declare precio int default 0;
        declare cantidad int;
        declare producto int;
        declare limite int default 0;
        declare i  int default 0;
        
		set producto = (select DF.productoId from DetallesFacturas DF where DF.facturaId = facId);
        set precio = (select P.precioVentaUnitario from Productos P where P.productoId = producto);
        set total = (total + precio);
        set i = facId;
        
        total : loop
        if i = facId or i <= facId then
			leave total;
		end if;
        end loop;
        
        return total;
    end$$
delimiter ;

delimiter $$
create function fn_calcularTotalUnitario(factId int) returns decimal(10, 2) deterministic
begin
	declare total decimal(10,2) default 0.0;
    declare precio decimal(10,2);
    declare i int default 1;
    declare curFacturaId, curProductoId int;
    
    declare cursorDetalleFactura cursor for 
		select DF.facturaId, DF.productoId from detalleFactura DF;
        
	open cursorDetalleFactura;
    
    
    totalLoop : loop
    fetch cursorDetalleFactura into curFacturaId, curProductoId;
    if facId = curFacturaId then
		set precio = (select P.precioVentaUnitario from Productos P where P.productoId = curProductoId);
		set total = total + precio;
    end if;
    
    if i = (select count(*) from detalleFactura) then
		leave totalLoop;
	end if;
    
    set i = i + 1;
    
    end loop totalLoop;
    
    call sp_asignarTotal(total, facId);
    
    return total;
end$$
delimiter ;


delimiter $$
create function fn_calcularTotalMayor(factId int) returns decimal(10, 2) deterministic
begin
	declare total decimal(10,2) default 0.0;
    declare precio decimal(10,2);
    declare i int default 1;
    declare curFacturaId, curProductoId int;
    
    declare cursorDetalleFactura cursor for 
		select DF.facturaId, DF.productoId from detalleFactura DF;
        
	open cursorDetalleFactura;
    
    
    totalLoop : loop
    fetch cursorDetalleFactura into curFacturaId, curProductoId;
    if factId = curFacturaId then
		set precio = (select P.precioVentaMayor from Productos P where P.productoId = curProductoId);
		set total = total + (precio % 1.5);
    end if;
    
    if i = (select count(*) from detalleFactura) then
		leave totalLoop;
	end if;
    
    set i = i + 1;
    
    end loop totalLoop;
    
    call sp_asignarTotal(total, facId);
    
    return total;
end$$
delimiter ;



delimiter $$
create procedure sp_asignarTotal(in tot decimal(10,2), in factId int)
begin
	update Facturas
		set total = tot
			where facturaId = facId;
end $$
delimiter ;

-- call sp_asignarTotal(21.00,null);

delimiter $$
create trigger tg_totalFacturas
after insert on detallesFacturas
for each row
begin

	declare total decimal(10,2);
    set total = fn_calcularTotal(NEW.facturaId);

end$$
delimiter ;


