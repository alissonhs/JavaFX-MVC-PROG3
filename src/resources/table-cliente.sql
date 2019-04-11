/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  drink
 * Created: 28/02/2019
 */

CREATE TABLE `cliente` (
  `idcliente` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(90) DEFAULT NULL,
  `cnpj` varchar(20) DEFAULT NULL,
  `situacao` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idcliente`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

insert into `cliente` (`nome`, `cnpj`, `situacao`) 
 values 
('fulano de tal',  '111.111.111-11','Ativo'),
('ciclano','222.222.222-22','Inativo');