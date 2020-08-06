create table `empresa` (
	`id` bigint(20) not null,
	`cnpj` varchar(255) not null,
	`data_atualizacao` datetime not null,
	`data_criacao` datetime not null,
	`razao_social` varchar(255) not null
) engine=innodb DEFAULT charset=utf8;

create table `funcionario`(
	`id` bigint(20) not null,
	`cpf` varchar(255) not null,
	`data_atualizacao` datetime not null,
	`data_criacao` datetime not null,
	`email` varchar(255) not null,
	`nome` varchar(255) not null,
	`perfil` varchar(255) not null,
	`qtd_horas_almoco` float default null,
	`qtd_horas_trabalho_dia` float default null,
	`senha` varchar(255) not null,
	`valor_hora` decimal(19,2) default null,
	`empresa_id` bigint(20) default null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table `lancamento`(
	`id` bigint(20) not null,
	`data` datetime not null,
	`data_atualizacao` datetime not null,
	`data_criacao` datetime not null,
	`descricao` varchar(255) default null,
	`localizacao` varchar(255) default null,
	`tipo` varchar(255) not null,
	`funcionario_id` bigint(20) default null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- indexes for table `empresa`
--

alter table `empresa`
 add primary key(`id`);
 
--
-- indexes for table `funcionario`
--

alter table `funcionario`
 add primary key(`id`),
 add key `funcionario_key` (`empresa_id`); 
 
--
-- indexes for table `lancamento`
--

alter table `lancamento`
 add primary key(`id`),
 add key `lancamento_key` (`funcionario_id`);  
 
--
-- auto_increment for table `empresa`
--

alter table `empresa`
	modify `id` bigint(20) not null AUTO_INCREMENT;
	
--
-- auto_increment for table `funcionario`
--

alter table `funcionario`
	modify `id` bigint(20) not null AUTO_INCREMENT; 
	
--
-- auto_increment for table `lancamento`
--

alter table `lancamento`
	modify `id` bigint(20) not null AUTO_INCREMENT;
	
--
-- constraints for table funcionario
--  
alter table `funcionario`
	add constraint `funcionario_empresa_fk` foreign key (`empresa_id`) references empresa (`id`);
	
--
-- constraints for table lancamento
--

alter table `lancamento`
	add constraint `lancamento_funcionario_fk` foreign key (`funcionario_id`) references funcionario (`id`);