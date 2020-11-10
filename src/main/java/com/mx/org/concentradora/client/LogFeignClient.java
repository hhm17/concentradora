package com.mx.org.concentradora.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mx.org.concentradora.model.Log;

@FeignClient(name = "servicio-logs")
public interface LogFeignClient {

	@PostMapping("/logs/")
	public Log save(@RequestBody Log log);
}
