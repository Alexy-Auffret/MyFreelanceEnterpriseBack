SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `my_freelance_enterprise`
--

-- --------------------------------------------------------

--
-- Structure de la table `bill`
--

DROP TABLE IF EXISTS `bill`;
CREATE TABLE IF NOT EXISTS `bill` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` double NOT NULL,
  `creation_date` datetime NOT NULL,
  `settlement_date` datetime DEFAULT NULL,
  `client` int(11) DEFAULT NULL,
  `step` varchar(15) CHARACTER SET utf8 DEFAULT NULL,
  `tva` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `bill_client__fk` (`client`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `bill`
--

INSERT INTO `bill` (`id`, `amount`, `creation_date`, `settlement_date`, `id_client`, `step`, `vat`) VALUES
(1, 10000, '2021-01-08 13:01:41', '2021-04-01 08:02:01', 3, 'DONE', 20),
(2, 1500, '2021-02-12 08:00:00', NULL, 1, 'WAITING', 20),
(3, 2000, '2021-03-13 13:04:13', NULL, 2, 'WAITING', 20);

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) CHARACTER SET utf8 NOT NULL,
  `phone_number` varchar(12) CHARACTER SET utf8 NOT NULL,
  `address` varchar(250) CHARACTER SET utf8 NOT NULL,
  `active` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`id`, `name`, `phone_number`, `address`, `active`) VALUES
(1, 'CESI Nantes', '+33244965845', '5 Avenue Lorem Impsume 44000 Nantes', 1),
(2, 'ENI Nantes', '+33259864217', '14 rue Ampères 44370 Saint-Herbalin', 1),
(3, 'TESLA France', '+33151487596', '14 Avenue des Champs Elysée 75007 Paris', 1),
(4, 'Hachette Livres', '+33125389741', '18 Rue du départ 75014 Paris', 0);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
