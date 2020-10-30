package com.mx.org.concentradora.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.org.concentradora.client.BitacoraFeignClient;
import com.mx.org.concentradora.client.TransaccionInFeignClient;
import com.mx.org.concentradora.client.TransaccionOutFeignClient;
import com.mx.org.concentradora.model.Bitacora;
import com.mx.org.concentradora.model.ResponseModel;
import com.mx.org.concentradora.model.TransaccionIn;
import com.mx.org.concentradora.model.TransaccionOut;
import com.mx.org.concentradora.util.TransaccionUtil;

@RestController
@RequestMapping(value = "/v1")
public class ConcentradoraController {

	private Integer TRANSACCION_NUEVA = 1;

	private Integer BITACORA_TRANSACCION_NUEVA = 1;
	private Integer BITACORA_TRANSACCION_ERRONEA = 0;

	@Autowired
	private TransaccionInFeignClient transaccionInFeignClient;

	@Autowired
	private TransaccionOutFeignClient transaccionOutFeignClient;

	@Autowired
	private BitacoraFeignClient bitacoraFeignClient;

	@PostMapping("/transacciones")
	public ResponseEntity<ResponseModel> solicitudSaldo(@RequestBody TransaccionIn transaccionIn) {
		transaccionIn.setEstatus(TRANSACCION_NUEVA);
		transaccionIn.setFecha(new Date());

		ResponseModel response = new ResponseModel();

		Bitacora bitacora = TransaccionUtil.crearBitacora(transaccionIn);
		try {
			TransaccionIn transaccion = transaccionInFeignClient.save(transaccionIn);
			if (transaccion != null) {
				String folio = TransaccionUtil.rellenarCeros(10, transaccion.getId());

				response.setCodigo("00");
				response.setMensaje(folio);

				TransaccionOut transaccionOut = TransaccionUtil.convertirTransaccionOut(transaccion);
				transaccionOut.setFolio(folio);
				transaccionOutFeignClient.save(transaccionOut);

				/** hhm: se guarda bitacora con transaccion de entrada guardada **/
				bitacora.setFolio(folio);
				bitacora.setEstatus(BITACORA_TRANSACCION_NUEVA);
				bitacora.setFechaInicio(new Date());
				bitacora = bitacoraFeignClient.save(bitacora);
				return new ResponseEntity<ResponseModel>(response, HttpStatus.OK);
			} else {
				/** hhm: se guarda bitacora con transaccion de entrada erronea **/
				bitacora.setEstatus(BITACORA_TRANSACCION_ERRONEA);
				bitacora.setFechaActualizacion(new Date());
				bitacora = bitacoraFeignClient.update(bitacora, bitacora.getId());
				response.setCodigo("01");
				response.setMensaje("Ocurrio un error al guardar la transaccion de entrada.");
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception ex) {
			response.setCodigo("01");
			response.setMensaje("Ocurrio un error al guardar la transaccion de entrada.");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/transacciones/{folio}")
	public ResponseEntity<TransaccionOut> consultaTransaccionSalida(@PathVariable String folio) {
		CollectionModel<TransaccionOut> response = transaccionOutFeignClient.findByFolio(folio);
		if (response != null && !response.getContent().isEmpty()) {
			TransaccionOut transaccion = response.getContent().iterator().next();
			return new ResponseEntity<>(transaccion, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

//	@GetMapping("/transacciones/{folio}")
//	public ResponseEntity<TransaccionOut> consultaTransaccionSalida(@PathVariable String folio) {
//		HttpStatus codigo = null;
//		Optional<TransaccionOut> response = null;
//		try {
//			response = transaccionOutFeignClient.findByFolio(folio);
//			if (response.isPresent()) {
//				codigo = HttpStatus.OK;
//			} else {
//				codigo = HttpStatus.NOT_FOUND;
//			}
//		} catch (Exception ex) {
//			codigo = HttpStatus.INTERNAL_SERVER_ERROR;
//			return new ResponseEntity<>(null, codigo);
//		}
//		return new ResponseEntity<>(response.get(), codigo);
//	}

//	@GetMapping("/transacciones/{id}")
//	public ResponseEntity<TransaccionIn> consultaTransaccion(@PathVariable Long id) {
//		HttpStatus codigo = null;
//		Optional<TransaccionIn> response = null;
//		try {
//			response = transaccionInFeignClient.findById(id);
//			if (response.isPresent()) {
//				codigo = HttpStatus.OK;
//			} else {
//				codigo = HttpStatus.NOT_FOUND;
//			}
//		} catch (Exception ex) {
//			codigo = HttpStatus.INTERNAL_SERVER_ERROR;
//			return new ResponseEntity<>(null, codigo);
//		}
//		return new ResponseEntity<>(response.get(), codigo);
//	}

//	@GetMapping("/bitacoras/{folio}")
//	public ResponseEntity<Bitacora> consultaBitacora(@PathVariable String folio) {
//		HttpStatus codigo = null;
//		Bitacora response = null;
//		try {
//			response = bitacoraFeignClient.findByFolio(folio);
//			if (response != null) {
//				codigo = HttpStatus.OK;
//			} else {
//				codigo = HttpStatus.NOT_FOUND;
//			}
//		} catch (Exception ex) {
//			codigo = HttpStatus.INTERNAL_SERVER_ERROR;
//		}
//		return new ResponseEntity<>(response, codigo);
//	}
}