INSERT INTO `bill` (`id`, `amount`, `creation_date`, `settlement_date`, `client`, `step`, `tva`) VALUES
(1, 10000, '2021-01-08 13:01:41', '2021-04-01 08:02:01', 3, 'DONE', 20),
(2, 1500, '2021-02-12 08:00:00', NULL, 1, 'WAITING', 20),
(3, 2000, '2021-03-13 13:04:13', NULL, 2, 'WAITING', 20);

INSERT INTO `client` (`id`, `name`, `phone_number`, `address`, `active`) VALUES
(1, 'CESI Nantes', '+33244965845', '5 Avenue Lorem Impsume 44000 Nantes', 1),
(2, 'ENI Nantes', '+33259864217', '14 rue Ampères 44370 Saint-Herbalin', 1),
(3, 'TESLA France', '+33151487596', '14 Avenue des Champs Elysée 75007 Paris', 1),
(4, 'Hachette Livres', '+33125389741', '18 Rue du départ 75014 Paris', 0);

