/**
 * @author Brian Pérez Ramos
 *
 */
document.addEventListener('DOMContentLoaded', creaPagina)

var listaFacturas = [];
let divInfo = document.getElementById('infoRecogida');
let divFactura = document.getElementById('divFactura');
let yaMostrado = false;

function creaPagina(){
	document.getElementById('btnFacturaInsertar').disabled = true;
	let btnBuscaFactura = document.getElementById('btnBuscaFactura');
	btnBuscaFactura.addEventListener('click', buscaFactura);
	cargaSelect();
	cargaDatos();
}

function cargaSelect(){
	axios.get('http://localhost:8080/EmprInfRs_PerezB-0.0.1-SNAPSHOT/resourcesPerezB/facturas/getTiendas').then(result => {
		let listaTiendas = result.data;
		let selectTiendas = document.getElementById('selectTiendaFacturaInsertar')
		console.log(listaTiendas);
		listaTiendas.forEach(tienda => {
			let optionTienda = document.createElement('option');
			optionTienda.value = tienda.idTienda;
			optionTienda.text = tienda.nombreTienda;
			selectTiendas.appendChild(optionTienda);
		})
		document.getElementById('btnFacturaInsertar').disabled = false;
	}).catch(error => {
		mensajeError()
	})
	document.getElementById('formInsertar').addEventListener('submit', event =>{
		event.preventDefault();
		
		const nCliente = document.getElementById('txtClienteFacturaInsertar').value.trim();
		
		const idTienda = document.getElementById('selectTiendaFacturaInsertar').value.trim();
		
		if(nCliente.length > 0){
			const factura = {
					cliente: nCliente,
					idTienda: idTienda
			}
			
			console.log('Valor factura convertida: ')
			console.log(factura)
			insertaFactura(factura);
		}else{
			alert('Debe introducir un nombre de Cliente');
		}
		
		console.log('Valores del formulario: ' + nCliente + " " + idTienda)
	})
}

function cargaDatos(){
	borraHijos(divInfo);
	axios.get('http://localhost:8080/EmprInfRs_PerezB-0.0.1-SNAPSHOT/resourcesPerezB/facturas/getAll').then(result => {

		listaFacturas = result.data
		console.log(listaFacturas)
		let tituloDiv = document.createElement('h3');
		tituloDiv.innerText = 'Todas las facturas: '
		divInfo.appendChild(tituloDiv);
		listaFacturas.forEach(factura => {
			let ulInfo = document.createElement('ul');
			divInfo.appendChild(ulInfo)
			
			let liIdFactura = document.createElement('li');
			liIdFactura.innerText = 'Id Factura: ' + factura.idFactura;
			ulInfo.appendChild(liIdFactura);
			
			let liCliente = document.createElement('li');
			liCliente.innerText = 'Nombre cliente: ' + factura.cliente;
			ulInfo.appendChild(liCliente);
			
			let liFecha = document.createElement('li');
			liFecha.innerText = 'Fecha factura: ' + factura.fechaFormateada;
			ulInfo.appendChild(liFecha);
			
			let liIdTienda = document.createElement('li');
			liIdTienda.innerText = 'Tienda e Id: ' + factura.nombreTienda + ' (' + factura.idTienda + ')';
			ulInfo.appendChild(liIdTienda);
		});
	}).catch(error => {
		mensajeError()
	})	
}

function buscaFactura(){
	borraHijos(divFactura);
	let txtBuscaFactura = document.getElementById('txtBuscaFactura');
	let nBuscar = txtBuscaFactura.value.trim();
	let msg = document.getElementById('msg')
	if(parseInt(nBuscar)){
		msg.innerText = '';
		axios.get(`http://localhost:8080/EmprInfRs_PerezB-0.0.1-SNAPSHOT/resourcesPerezB/facturas/getFactura?nFactura=${nBuscar}`).then(result => {
			console.log('Largo array: ' + Object.entries(result.data).length)
			if(Object.entries(result.data).length > 0){
				let factura = result.data;
				msg.innerText = 'Se ha encontrado una factura: '
					let ulFactura = document.createElement('ul');
				divFactura.appendChild(ulFactura)
				
				let liIdFactura = document.createElement('li');
				liIdFactura.innerText = 'Id Factura: ' + factura.idFactura;
				ulFactura.appendChild(liIdFactura);
				
				let liCliente = document.createElement('li');
				liCliente.innerText = 'Nombre cliente: ' + factura.cliente;
				ulFactura.appendChild(liCliente);
				
				let liFecha = document.createElement('li');
				liFecha.innerText = 'Fecha factura: ' + factura.fechaFormateada;
				ulFactura.appendChild(liFecha);
				
				let liIdTienda = document.createElement('li');
				liIdTienda.innerText = 'Tienda e Id: ' + factura.nombreTienda + ' (' + factura.idTienda + ')';
				ulFactura.appendChild(liIdTienda);
			}else{
				msg.innerText = 'No se ha encontrado la factura.'
			}
		})
	}else{
		msg.innerText = 'Campo requerido, y debe contener un número.'
	}
}

function insertaFactura(factura){
	axios.post('http://localhost:8080/EmprInfRs_PerezB-0.0.1-SNAPSHOT/resourcesPerezB/facturas/addFactura', factura).then(response => {
		console.log('Se ha agregado la factura ' + factura);
		cargaDatos();
	}).catch(error => {
		mensajeError()
	})
}

function mensajeError(){
	if(yaMostrado === false){
		alert('Se ha producido un error al conectar al servidor.\nAvise a un administrador en admin@javarest.edu')
		yaMostrado = true;
	}
	document.getElementById('btnBuscaFactura').disabled = true;
}

function borraHijos(elemento){
	while(elemento.firstChild){
		elemento.removeChild(elemento.lastChild);
	}
	console.log('Contenedor limpiado correctamente.')
}