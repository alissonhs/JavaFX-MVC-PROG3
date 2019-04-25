-- --------------------------------------------------------
-- Servidor:                     127.0.0.1
-- Versão do servidor:           10.1.38-MariaDB - mariadb.org binary distribution
-- OS do Servidor:               Win32
-- HeidiSQL Versão:              10.1.0.5464
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Copiando estrutura do banco de dados para prog3bancoteste
CREATE DATABASE IF NOT EXISTS `prog3bancoteste` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `prog3bancoteste`;

-- Copiando estrutura para tabela prog3bancoteste.cliente
CREATE TABLE IF NOT EXISTS `cliente` (
  `idcliente` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(90) DEFAULT NULL,
  `cnpj` varchar(20) DEFAULT NULL,
  `situacao` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idcliente`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- Copiando dados para a tabela prog3bancoteste.cliente: ~20 rows (aproximadamente)
DELETE FROM `cliente`;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` (`idcliente`, `nome`, `cnpj`, `situacao`) VALUES
	(1, 'Predo', '44191973070', 'Ativo'),
	(2, 'Ariso', '44191973072', 'Ativo'),
	(3, 'Lusca', '123123123123', 'Ativo'),
	(4, 'Nicolal', '44191973073', 'Ativo'),
	(5, 'Luiz', '44191973074', 'Ativo'),
	(6, 'Josefa', '44191973075', 'Ativo'),
	(7, 'Markos', '44191973076', 'Ativo'),
	(9, 'Saitama', '44191973078', 'Ativo'),
	(10, 'Deu Branco', '44191973079', 'Ativo'),
	(11, 'Cebolinha', '12321312', 'Ativo'),
	(12, 'Test', '123', 'Ativo'),
	(13, 'Ariosvaldo', '', 'Ativo'),
	(14, 'asd', '', 'Ativo'),
	(15, 'asd', '90.359.312/0001-89', 'Inativo'),
	(16, '123', '123.123.123-12', 'Inativo');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;

-- Copiando estrutura para tabela prog3bancoteste.teste
CREATE TABLE IF NOT EXISTS `teste` (
  `Coluna 1` datetime DEFAULT NULL,
  `Coluna 2` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela prog3bancoteste.teste: ~0 rows (aproximadamente)
DELETE FROM `teste`;
/*!40000 ALTER TABLE `teste` DISABLE KEYS */;
INSERT INTO `teste` (`Coluna 1`, `Coluna 2`) VALUES
	('2019-03-15 19:55:55', '2019-03-15 19:55:57');
/*!40000 ALTER TABLE `teste` ENABLE KEYS */;

-- Copiando estrutura para tabela prog3bancoteste.usuario
CREATE TABLE IF NOT EXISTS `usuario` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `nomeUsuario` varchar(150) NOT NULL,
  `loginUsuario` varchar(45) NOT NULL,
  `senhaUsuario` varchar(64) NOT NULL,
  `statusUsuario` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`idUsuario`),
  UNIQUE KEY `nomeUsuario_UNIQUE` (`nomeUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela prog3bancoteste.usuario: ~4 rows (aproximadamente)
DELETE FROM `usuario`;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` (`idUsuario`, `nomeUsuario`, `loginUsuario`, `senhaUsuario`, `statusUsuario`) VALUES
	(1, 'admin', 'admin', '1c142b2d01aa34e9a36bde480645a57fd69e14155dacfab5a3f9257b77fdc8d8', 'Ativo'),
	(2, '651651', '516516', '9ef0f6fd9d7b625302f64a4244e9c0bad5fbece025232509d99e896fb3f63600', 'Ativo'),
	(5, 'a', 'a', 'ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb', 'Ativo'),
	(6, 'b', 'b', '3e23e8160039594a33894f6564e1b1348bbd7a0088d42c4acb73eeaed59c009d', 'Ativo'),
	(7, 'c', 'c', '2e7d2c03a9507ae265ecf5b5356885a53393a2029d241394997265a1a25aefc6', 'Inativo');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
