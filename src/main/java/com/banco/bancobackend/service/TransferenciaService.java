package com.banco.bancobackend.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banco.bancobackend.model.Cliente;
import com.banco.bancobackend.model.Transferencia;
import com.banco.bancobackend.repository.TransferenciaRepository;

@Service
public class TransferenciaService {

	@Autowired
	TransferenciaRepository transferenciaRepository;
	
	@Autowired
	ClienteService clienteService;
	
	public ArrayList<Transferencia> leerTransferencia() {
		return (ArrayList<Transferencia>) this.transferenciaRepository.findAll();
	}

	public Optional<Transferencia> leerTransferenciaPorId(Integer id) {
		return this.transferenciaRepository.findById(id);
	}

	public Transferencia guardarTransferencia(Transferencia transferencia) {
		
		this.transferenciaRepository.save(transferencia);
		
		Double importe = transferencia.getImporte();
		
		Cliente ordenante = transferencia.getOrdenante();
		ordenante = clienteService.leerClientePorId(ordenante.getId()).orElse(null);
		ordenante.setPassword(null);
		Double saldoOrdenante = ordenante.getSaldo();
		
		ordenante.setSaldo(saldoOrdenante - importe);
		
		Cliente beneficiario = transferencia.getBeneficiario();
		beneficiario = clienteService.leerClientePorId(beneficiario.getId()).orElse(null);
		beneficiario.setPassword(null);
		Double saldoBenenficiario = beneficiario.getSaldo();
		
		beneficiario.setSaldo(saldoBenenficiario + importe);
		
		clienteService.guardarCliente(ordenante);
		clienteService.guardarCliente(beneficiario);
		
		return transferencia;
	}

	public void borrarTransferenciaPorId(Integer id) {
		this.transferenciaRepository.deleteById(id);
	}

}
