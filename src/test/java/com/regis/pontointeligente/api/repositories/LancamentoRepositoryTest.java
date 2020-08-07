package com.regis.pontointeligente.api.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import com.regis.pontointeligente.api.entities.Empresa;
import com.regis.pontointeligente.api.entities.Funcionario;
import com.regis.pontointeligente.api.entities.Lancamento;
import com.regis.pontointeligente.api.enums.PerfilEnum;
import com.regis.pontointeligente.api.enums.TipoEnum;
import com.regis.pontointeligente.api.utils.PasswordUtils;

@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	private Long funcionarioId;

	@BeforeEach
	public void setUp() throws Exception {
		Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());

		Funcionario funcionario = this.funcionarioRepository.save(obterDadosFuncionario(empresa));

		this.funcionarioId = funcionario.getId();

		this.lancamentoRepository.save(obterDadosLancamentos(funcionario));

	}

	private Empresa obterDadosEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setCnpj("1212121222344");
		empresa.setRazaoSocial("Empresa de Exemplo");
		return empresa;
	}

	private Funcionario obterDadosFuncionario(Empresa empresa) throws NoSuchAlgorithmException {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome("Fulando de Tal");
		funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
		funcionario.setSenha(PasswordUtils.gerarBCrypt("123456"));
		funcionario.setCpf("01384948198");
		funcionario.setEmail("email@email.com");
		funcionario.setEmpresa(empresa);
		return funcionario;
	}

	private Lancamento obterDadosLancamentos(Funcionario funcionario) {
		Lancamento lancamento = new Lancamento();
		lancamento.setData(new Date());
		lancamento.setTipo(TipoEnum.INICIO_ALMOCO);
		lancamento.setFuncionario(funcionario);
		return lancamento;
	}

	@AfterEach
	public void tearUp() throws Exception {
		this.empresaRepository.deleteAll();
	}

	@Test
	public void testBuscarLancamentosPorFuncionarioId() {
		List<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId);
		assertEquals(1, lancamentos.size());
	}

	@Test
	public void testBuscarLancamentosPorFuncionarioIdPaginado() {
		PageRequest page = PageRequest.of(0, 10);

		Page<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId, page);
		assertEquals(1, lancamentos.getTotalElements());
	}

}
