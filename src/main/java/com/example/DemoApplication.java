package com.example;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DemoApplication {

		
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner runner(ClienteRepository clienteRepository){
		
		return new CommandLineRunner() {
			
			@Override
			public void run(String... arg0) throws Exception {
				
			
				Arrays.asList(new Cliente("Joao cardoso"), new Cliente("Juma"))
					.forEach(new Consumer<Cliente>() {

						@Override
						public void accept(Cliente cliente) {
							clienteRepository.save(cliente);
							
						}
					});
				
				clienteRepository.findAll()
				.forEach(new Consumer<Cliente>() {

					@Override
					public void accept(Cliente cliente) {
						System.out.println(cliente.getNome());
						
					}
				});
				
			}
		};
		
	}
		

		
		
		

}




@Entity
class Cliente{
	
	@Id
	@GeneratedValue 
	private Integer id;
	
	private String nome;
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	public Cliente(String nome) {
		this.nome = nome;
	}
	
	public Cliente() {
	}
	
}

interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	
}

@RestController
class ClienteController{
	
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	
	@PostMapping("clientes")
	private Cliente novo(@RequestBody Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	@GetMapping("clientes")
	private List<Cliente> listar() {
		return clienteRepository.findAll();
	}
	
}



