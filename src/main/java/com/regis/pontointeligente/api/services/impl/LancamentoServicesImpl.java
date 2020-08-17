package com.regis.pontointeligente.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.regis.pontointeligente.api.entities.Lancamento;
import com.regis.pontointeligente.api.repositories.LancamentoRepository;
import com.regis.pontointeligente.api.services.LancamentoService;

@Service
public class LancamentoServicesImpl implements LancamentoService {

	private static final Logger log = LoggerFactory.getLogger(LancamentoServicesImpl.class);

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Override
	public Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest) {
		log.info("Buscando lancamentos para funcionario ID {}", funcionarioId);
		return this.lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest);
	}

	@Override
	public Optional<Lancamento> buscarPorId(Long id) {
		log.info("Buscando um lancamento pelo ID {} ", id);
		return this.lancamentoRepository.findById(id);
	}

	@Override
	public Lancamento persistir(Lancamento lancamento) {
		log.info("Persistindo lancamento: {}", lancamento);
		return this.lancamentoRepository.save(lancamento);
	}

	@Override
	public void remover(Long id) {
		log.info("Removendo o lancamento ID: ", id);
		this.lancamentoRepository.deleteById(id);
	}

}
