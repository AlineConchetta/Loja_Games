package com.generation.loja_games.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.loja_games.Repository.CategoriaRepository;
import com.generation.loja_games.Repository.ProdutoRepository;
import com.generation.loja_games.model.Produto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public ResponseEntity<List<Produto>> getAll() {
		return ResponseEntity.ok(produtoRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(@PathVariable Long id) {
		return produtoRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/nome/{nome}")
	ResponseEntity<List<Produto>> getByProduto(@PathVariable String nome) {
		return ResponseEntity.ok(produtoRepository.findAllByProdutoContainingIgnoreCase(nome));
	}

	@PostMapping
	public ResponseEntity<Produto> post(@Valid @RequestBody Produto produto) {
		if (categoriaRepository.existsById(produto.getCategoria().getId()))
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(produtoRepository.save(produto));
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe.", null);
	}

	@PutMapping
	public ResponseEntity<Produto> put(@Valid @RequestBody Produto produto) {
		if(produtoRepository.existsById(produto.getId())) {
			if(produtoRepository.existsById(produto.getId())) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(produtoRepository.save(produto));
			}
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categorianão existe.", null);
	}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
	
	/*/

	
	public ResponseEntity<Produto> put(@Valid @RequestBody Produto produto) {
		return produtoRepository.findById(produto.getId())
				.map(checagem -> ResponseEntity.status(HttpStatus.OK)
						.body(produtoRepository.save(produto)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	} /*/

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		Optional<Produto> testa = produtoRepository.findById(id);
		if (testa.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		produtoRepository.deleteById(id);
	}

	@GetMapping("/menorque/{preco}")
	public List<Produto> listarProdutosMenoresQue(@PathVariable BigDecimal preco) {
		return produtoRepository.findByPrecoLessThan(preco);
	}

	@GetMapping("/maiorque/{preco}")
	public List<Produto> listarProdutosMaioresQue(@PathVariable BigDecimal preco) {
		return produtoRepository.findByPrecoGreaterThan(preco);
	}

}
