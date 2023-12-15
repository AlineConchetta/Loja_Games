package com.generation.loja_games.Repository;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


import com.generation.loja_games.model.Produto;



	public interface ProdutoRepository extends JpaRepository<Produto, Long> {
		
		List<Produto> findAllByProdutoContainingIgnoreCase(@Param("Produto") String produto);

		List<Produto> findByPrecoLessThan(BigDecimal preco);
		
		List<Produto> findByPrecoGreaterThan(BigDecimal preco);
	}
