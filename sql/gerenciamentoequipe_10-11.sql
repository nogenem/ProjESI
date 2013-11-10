-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Máquina: localhost
-- Data de Criação: 10-Nov-2013 às 23:12
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
  KEY `USUARIO_ibfk_1` (`ID_EQUIPE`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Extraindo dados da tabela `usuario`
--

INSERT INTO `usuario` (`ID_USUARIO`, `LOGIN`, `SENHA`, `NOME`, `NIVEL`, `ID_EQUIPE`) VALUES
(3, 'admin', 'admin', 'ADMINISTRADOR', 0, NULL);

-- -------------------------------------------------------------

--
-- Estrutura da tabela `arquivo`
--

CREATE TABLE IF NOT EXISTS `arquivo` (
  `ID_ARQUIVO` int(11) NOT NULL AUTO_INCREMENT,
  `TITULO` varchar(50) NOT NULL,
  `CONTEUDO` text,
  `ID_EQUIPE` int(11) NOT NULL,
  PRIMARY KEY (`ID_ARQUIVO`),
  UNIQUE KEY `ID_EQUIPE` (`ID_EQUIPE`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `equipe`
--

CREATE TABLE IF NOT EXISTS `equipe` (
  `ID_EQUIPE` int(11) NOT NULL AUTO_INCREMENT,
  `NOME` varchar(50) NOT NULL,
  PRIMARY KEY (`ID_EQUIPE`),
  UNIQUE KEY `NOME` (`NOME`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `postit`
--

CREATE TABLE IF NOT EXISTS `postit` (
  `ID_POSTIT` int(11) NOT NULL AUTO_INCREMENT,
  `TITULO` varchar(50) NOT NULL,
  `CONTEUDO` text,
  `ID_USUARIO` int(11) DEFAULT NULL,
  `ID_EQUIPE` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_POSTIT`),
  KEY `ID_USUARIO` (`ID_USUARIO`),
  KEY `ID_EQUIPE` (`ID_EQUIPE`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

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
  KEY `PROJETO_ibfk_1` (`ID_EQUIPE`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

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
  KEY `TAREFA_ibfk_1` (`ID_PROJETO`)
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
  ADD CONSTRAINT `postit_ibfk_1` FOREIGN KEY (`ID_USUARIO`) REFERENCES `usuario` (`ID_USUARIO`),
  ADD CONSTRAINT `postit_ibfk_2` FOREIGN KEY (`ID_EQUIPE`) REFERENCES `equipe` (`ID_EQUIPE`);

--
-- Limitadores para a tabela `projeto`
--
ALTER TABLE `projeto`
  ADD CONSTRAINT `projeto_ibfk_1` FOREIGN KEY (`ID_EQUIPE`) REFERENCES `equipe` (`ID_EQUIPE`);

--
-- Limitadores para a tabela `tarefa`
--
ALTER TABLE `tarefa`
  ADD CONSTRAINT `tarefa_ibfk_1` FOREIGN KEY (`ID_PROJETO`) REFERENCES `projeto` (`ID_PROJETO`);

--
-- Limitadores para a tabela `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`ID_EQUIPE`) REFERENCES `equipe` (`ID_EQUIPE`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;