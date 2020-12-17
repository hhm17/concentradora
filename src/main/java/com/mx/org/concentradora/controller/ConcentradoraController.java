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
import com.mx.org.concentradora.client.LogFeignClient;
import com.mx.org.concentradora.client.TransaccionInFeignClient;
import com.mx.org.concentradora.client.TransaccionOutFeignClient;
import com.mx.org.concentradora.model.Bitacora;
import com.mx.org.concentradora.model.Log;
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

	@Autowired
	private LogFeignClient logFeignClient;

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
				bitacora.setFechaInicio(new Date());
				bitacora.setFechaFin(new Date());
				bitacora.setRespProv("E01");
				bitacora.setFolio("0");
				bitacora.setLeyendaTck("Ocurrio un error interno de aplicacion.");
				bitacora = bitacoraFeignClient.save(bitacora);
				response.setCodigo("01");
				response.setMensaje("Ocurrio un error al guardar la transaccion de entrada.");
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception ex) {
			bitacora.setEstatus(BITACORA_TRANSACCION_ERRONEA);
			bitacora.setFechaInicio(new Date());
			bitacora.setFechaFin(new Date());
			bitacora.setRespProv("E01");
			bitacora.setFolio("0");
			bitacora.setLeyendaTck("Ocurrio un error interno de aplicacion.");
			bitacora = bitacoraFeignClient.save(bitacora);
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

	@PostMapping("/logs")
	public ResponseEntity<Log> registrarLog(@RequestBody Log log) {
		try {
			log = logFeignClient.save(log);
			return new ResponseEntity<>(log, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}