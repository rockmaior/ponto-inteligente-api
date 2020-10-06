package com.regis.pontointeligente.api.controllers;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.regis.pontointeligente.api.dtos.CadastroPJDto;
import com.regis.pontointeligente.api.entities.Empresa;
import com.regis.pontointeligente.api.entities.Funcionario;
import com.regis.pontointeligente.api.response.Response;
import com.regis.pontointeligente.api.services.EmpresaService;
import com.regis.pontointeligente.api.services.FuncionarioService;

@RestController
@RequestMapping("/api/cadastrar-pj")
@CrossOrigin(origins = "*")
public class CadastroPJController {
	private static final Logger log = LoggerFactory.getLogger(CadastroPJController.class);

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private EmpresaService empresaService;

	public CadastroPJController() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Cadastra um pessoa juridica no sistema.
	 * 
	 * @param cadastroPJDto
	 * @param resulta
	 * @return ResponseEntity(Response<CadastroPJDto>>
	 * @throws NoSuchAlgorithmException
	 */

	@PostMapping
	public ResponseEntity<Response<CadastroPJDto>> cadastrar(@Valid @RequestBody CadastroPJDto cadastroPJDto,
			BindingResult result) throws NoSuchAlgorithmException {

		log.info("Cadastrando PJ: {}", cadastroPJDto.toString());
		Response<CadastroPJDto> response = new Response<CadastroPJDto>();

		validarDadosExistentes(cadastroPJDto, result);

		Empresa empresa = this.converterDtoParaEmpresa(cadastroPJDto);
		Funcionario funcionario = this.converterDtoParaFuncionario(cadastroPJDto, result);

		if (result.hasErrors()) {
			log.info("Erro validando dados de cadastro PJ: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		this.empresaService.persistir(empresa);
		funcionario.setEmpresa(empresa);
		this.funcionarioService.persistir(funcionario);

		response.setData(this.converterCadastroPJDto(funcionario));
		return ResponseEntity.ok(response);

	}

	private CadastroPJDto converterCadastroPJDto(Funcionario funcionario) {
		// TODO Auto-generated method stub
		return null;
	}

	private Empresa converterDtoParaEmpresa(@Valid CadastroPJDto cadastroPJDto) {
		// TODO Auto-generated method stub
		return null;
	}

	private Funcionario converterDtoParaFuncionario(@Valid CadastroPJDto cadastroPJDto, BindingResult result) {
		// TODO Auto-generated method stub
		return null;
	}

	private void validarDadosExistentes(@Valid CadastroPJDto cadastroPJDto, BindingResult result) {
		this.empresaService.buscarPorCnpj(cadastroPJDto.getCnpj()).ifPresent(emp -> 
			result.addError(new ObjectError("empresa", "empresa ja esistente")));
		this.funcionarioService.buscarPorCpf(cadastroPJDto.getCpf()).ifPresent(func ->
			result.addError(new ObjectError("funcionario", "cpf ja existente")));
		
		this.funcionarioService.buscarPorEmail(cadastroPJDto.getEmail()).ifPresent(func -> 
			result.addError(new ObjectError("funcionario", "Email ja existente")));
	}
}
