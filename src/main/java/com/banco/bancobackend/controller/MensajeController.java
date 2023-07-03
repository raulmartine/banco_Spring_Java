package com.banco.bancobackend.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banco.bancobackend.model.Mensaje;
import com.banco.bancobackend.service.MensajeService;

@RestController
@RequestMapping("/mensaje")
public class MensajeController {
	

	@Autowired
	MensajeService mensajeService;

	@GetMapping()
	public ArrayList<Mensaje> obtenerMensajes() {
		return this.mensajeService.leerMensajes();
	}
	
	@GetMapping(path = "/{id}")
	public Optional<Mensaje> obtenerMensaje(@PathVariable("id") Integer id) {
		return this.mensajeService.leerMensajePorId(id);
	}
	
	@PostMapping()
	public Mensaje guardarMensaje(@RequestBody Mensaje mensaje) {
		return this.mensajeService.guardarMensaje(mensaje);
	}
	
	@DeleteMapping(path = "/{id}")
	public void borrarMensaje(@PathVariable("id") Integer id) {
		this.mensajeService.borrarMensajePorId(id);
	}
	
}
