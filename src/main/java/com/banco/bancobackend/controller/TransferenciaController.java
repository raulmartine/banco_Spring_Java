package com.banco.bancobackend.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banco.bancobackend.model.Cliente;
import com.banco.bancobackend.model.Transferencia;
import com.banco.bancobackend.service.TransferenciaService;

@RestController
@RequestMapping("/transferencia")
@CrossOrigin(origins = "http://localhost:4200")
public class TransferenciaController {

	@Autowired
	TransferenciaService transferenciaService;

	@GetMapping()
	public ArrayList<Transferencia> obtenerTransferencia() {
		return this.transferenciaService.leerTransferencia();
	}

	@GetMapping(path = "/{id}")
	public Optional<Transferencia> obtenerTransferencia(@PathVariable("id") Integer id) {
		return this.transferenciaService.leerTransferenciaPorId(id);
	}

	@GetMapping(path = "/ordenante/{ordenanteId}")
	public ArrayList<Transferencia> obtenerTransferenciasPorIdOrdenante(@PathVariable("ordenanteId") Integer id) {
		return this.transferenciaService.leerTransferenciasPorIdOrdenante(id);
	}
	
	@GetMapping(path = "/beneficiario/{beneficiarioId}")
	public ArrayList<Transferencia> obtenerTransferenciasPorIdBeneficiario(@PathVariable("beneficiarioId") Integer id) {
		return this.transferenciaService.leerTransferenciasPorIdBeneficiario(id);
	}
	
	@PostMapping()
	public Transferencia guardarTransferencia(@RequestBody Transferencia transferencia) {
		return this.transferenciaService.guardarTransferencia(transferencia);
	}

	@DeleteMapping(path = "/{id}")
	public void borrarTransferencia(@PathVariable("id") Integer id) {
		this.transferenciaService.borrarTransferenciaPorId(id);
	}

}
