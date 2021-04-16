INSERT INTO client (active, address, name, phone_number) VALUES
( 1, '5 Avenue Lorem Impsume 44000 Nantes','CESI Nantes', '+33244965845'),
( 1, '14 rue Ampères 44370 Saint-Herbalin','ENI Nantes', '+33244965845'),
( 1, '14 Avenue des Champs Elysée 75007 Paris','TESLA France', '+33151487596'),
( 1, '+33151487596', '14 Avenue des Champs Elysée 75007 Paris','+33151487596');
INSERT INTO bill ( amount, creation_date, settlement_date, step, vat, client_id) VALUES
( 10000, '2021-01-08 13:01:41', '2021-04-01 08:02:01', 'DONE', 20, 1),
( 1500, '2021-02-12 08:00:00', NULL, 'WAITING', 20, 1),
( 2000, '2021-03-13 13:04:13', NULL, 'WAITING', 20, 1);
