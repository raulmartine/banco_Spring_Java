package com.banco.bancobackend.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.banco.bancobackend.model.Cliente;
import com.banco.bancobackend.model.Gestor;
import com.banco.bancobackend.repository.ClienteRepository;
import com.banco.bancobackend.repository.GestorRepository;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	GestorRepository gestorRepository;

	public ArrayList<Cliente> leerClientes() {
		return (ArrayList<Cliente>) this.clienteRepository.findAll();
	}

	public Optional<Cliente> leerClientePorId(Integer id) {
		return this.clienteRepository.findById(id);
	}
	
	public ArrayList<Cliente> leerClientesPorIdGestor(Integer id) {
		return clienteRepository.findByGestorId(id);
	}
	
	public Optional<Cliente> leerClientePorIdGestor(Integer id) {
		return this.clienteRepository.findById(id);
	}
	

	public Cliente guardarCliente(Cliente cliente) {
		String pass = cliente.getPassword();
		if (pass != null) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			pass = encoder.encode(pass);
			cliente.setPassword(pass);
		}
		else {
			Cliente clienteExistente = leerClientePorId(cliente.getId()).orElse(null);
			if (clienteExistente != null) {
				cliente.setPassword(clienteExistente.getPassword());
			}

		}
		return this.clienteRepository.save(cliente);
	}
	
	public String obtenerPasswordActual(Cliente cliente) {
		Cliente clienteGuardado = leerClientePorId(cliente.getId()).orElse(null);
		if (clienteGuardado != null) {
			return clienteGuardado.getPassword();
		} 
		return null;
	}
	
	public Cliente guardarClienteSinActualizarPassword(Cliente cliente) {
		String passGuardada= obtenerPasswordActual(cliente);
		cliente.setPassword(passGuardada);
		return this.clienteRepository.save(cliente);
	}
	

	public void borrarClientePorId(Integer id) {
		this.clienteRepository.deleteById(id);
	}

	public Optional<Cliente> buscarClientePorCorreo(String correo) {
		return this.clienteRepository.findFirstByCorreo(correo);
	}

	public Optional<Cliente> buscarClientePorCorreoYPass(String correo, String password) {
		Optional<Cliente> cliente = buscarClientePorCorreo(correo);
		if (cliente.isPresent()) {
			Cliente clienteEncontrado = cliente.get();
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			if (encoder.matches(password, clienteEncontrado.getPassword())) {
				return cliente;
			}
		}
		return null;
	}
}
