package com.regis.pontointeligente.api.services;

import java.util.Optional;

import com.regis.pontointeligente.api.entities.Empresa;

public interface EmpresaService {

	/**
	 * Retorna uma empresa dado um CNPJ
	 * 
	 * @param cnpj
	 * @return Optional<Empresa>
	 */

	Optional<Empresa> buscarPorCnpj(String cnpj);

	/**
	 * Cadastra empresa no banco de dados
	 * 
	 * @param empresa
	 * @return Empresa
	 */

	Empresa persistir(Empresa empresa);

}
