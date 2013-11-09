-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Máquina: localhost
-- Data de Criação: 09-Nov-2013 às 00:40
-- Versão do servidor: 5.6.12-log
-- versão do PHP: 5.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de Dados: `gerenciamentoequipe`
--
CREATE DATABASE IF NOT EXISTS `gerenciamentoequipe` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `gerenciamentoequipe`;

-- --------------------------------------------------------

--
-- Estrutura da tabela `arquivo`
--

CREATE TABLE IF NOT EXISTS `arquivo` (
  `ID_ARQUIVO` int(11) NOT NULL AUTO_INCREMENT,
  `TITULO` varchar(50) NOT NULL,
  `CONTEUDO` text,
  `ID_EQUIPE` int(11) NOT NULL,
  PRIMARY KEY (`ID_ARQUIVO`),
  UNIQUE KEY `TITULO` (`TITULO`),
  KEY `ID_EQUIPE` (`ID_EQUIPE`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Extraindo dados da tabela `arquivo`
--

INSERT INTO `arquivo` (`ID_ARQUIVO`, `TITULO`, `CONTEUDO`, `ID_EQUIPE`) VALUES
(2, 'file_1', 'pfkeopfkeo', 3);

-- --------------------------------------------------------

--
-- Estrutura da tabela `equipe`
--

CREATE TABLE IF NOT EXISTS `equipe` (
  `ID_EQUIPE` int(11) NOT NULL AUTO_INCREMENT,
  `NOME` varchar(50) NOT NULL,
  PRIMARY KEY (`ID_EQUIPE`),
  UNIQUE KEY `NOME` (`NOME`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Extraindo dados da tabela `equipe`
--

INSERT INTO `equipe` (`ID_EQUIPE`, `NOME`) VALUES
(3, 'nova sasoaksoaksa'),
(5, 'ola');

-- --------------------------------------------------------

--
-- Estrutura da tabela `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
  `ID_USUARIO` int(11) NOT NULL AUTO_INCREMENT,
  `LOGIN` varchar(50) NOT NULL,
  `SENHA` varchar(50) NOT NULL,
  `NOME` varchar(50) NOT NULL,
  `NIVEL` int(11) NOT NULL,
  `ID_EQUIPE` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_USUARIO`),
  UNIQUE KEY `LOGIN` (`LOGIN`),
  KEY `ID_EQUIPE` (`ID_EQUIPE`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Extraindo dados da tabela `usuario`
--

INSERT INTO `usuario` (`ID_USUARIO`, `LOGIN`, `SENHA`, `NOME`, `NIVEL`, `ID_EQUIPE`) VALUES
(1, 'admin', 'admin', 'ADMINISTRADOR', 0, NULL),
(2, 'luiz', '123', 'dsadsa', 1, 3);

-- --------------------------------------------------------

--
-- Estrutura da tabela `postit`
--

CREATE TABLE IF NOT EXISTS `postit` (
  `ID_POSTIT` int(11) NOT NULL AUTO_INCREMENT,
  `TITULO` varchar(50) NOT NULL,
  `CONTEUDO` text NOT NULL,
  `ID_USUARIO` int(11) NOT NULL,
  `ID_EQUIPE` int(11) NOT NULL,
  PRIMARY KEY (`ID_POSTIT`),
  UNIQUE KEY `TITULO` (`TITULO`),
  KEY `ID_USUARIO` (`ID_USUARIO`),
  KEY `POSTIT_ibfk_2` (`ID_EQUIPE`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Extraindo dados da tabela `postit`
--

INSERT INTO `postit` (`ID_POSTIT`, `TITULO`, `CONTEUDO`, `ID_USUARIO`, `ID_EQUIPE`) VALUES
(5, 'teste', 'efefe', 1, 5),
(7, 'testeee', 'efef', 1, 5),
(8, 'olamundo', 'ofkeokfef', 1, 3);

-- --------------------------------------------------------

--
-- Estrutura da tabela `projeto`
--

CREATE TABLE IF NOT EXISTS `projeto` (
  `ID_PROJETO` int(11) NOT NULL AUTO_INCREMENT,
  `NOME` varchar(50) NOT NULL,
  `ID_EQUIPE` int(11) NOT NULL,
  PRIMARY KEY (`ID_PROJETO`),
  UNIQUE KEY `NOME` (`NOME`),
  KEY `ID_EQUIPE` (`ID_EQUIPE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tarefa`
--

CREATE TABLE IF NOT EXISTS `tarefa` (
  `ID_TAREFA` int(11) NOT NULL AUTO_INCREMENT,
  `TITULO` varchar(50) NOT NULL,
  `DESCRICAO` varchar(200) NOT NULL,
  `DATA_INICIO` varchar(50) NOT NULL,
  `DATA_FINAL` varchar(50) NOT NULL,
  `RECURSOS` varchar(50) NOT NULL,
  `ID_PROJETO` int(11) NOT NULL,
  PRIMARY KEY (`ID_TAREFA`),
  UNIQUE KEY `TITULO` (`TITULO`),
  KEY `ID_PROJETO` (`ID_PROJETO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `arquivo`
--
ALTER TABLE `arquivo`
  ADD CONSTRAINT `arquivo_ibfk_1` FOREIGN KEY (`ID_EQUIPE`) REFERENCES `equipe` (`ID_EQUIPE`);

--
-- Limitadores para a tabela `postit`
--
ALTER TABLE `postit`
  ADD CONSTRAINT `POSTIT_ibfk_1` FOREIGN KEY (`ID_USUARIO`) REFERENCES `usuario` (`ID_USUARIO`),
  ADD CONSTRAINT `POSTIT_ibfk_2` FOREIGN KEY (`ID_EQUIPE`) REFERENCES `equipe` (`ID_EQUIPE`);

--
-- Limitadores para a tabela `projeto`
--
ALTER TABLE `projeto`
  ADD CONSTRAINT `PROJETO_ibfk_1` FOREIGN KEY (`ID_EQUIPE`) REFERENCES `equipe` (`ID_EQUIPE`);

--
-- Limitadores para a tabela `tarefa`
--
ALTER TABLE `tarefa`
  ADD CONSTRAINT `TAREFA_ibfk_1` FOREIGN KEY (`ID_PROJETO`) REFERENCES `projeto` (`ID_PROJETO`);

--
-- Limitadores para a tabela `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `USUARIO_ibfk_1` FOREIGN KEY (`ID_EQUIPE`) REFERENCES `equipe` (`ID_EQUIPE`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
