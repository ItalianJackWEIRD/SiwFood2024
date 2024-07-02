/*INSERIMENTI NELLA TABELLA CUOCO*/
INSERT INTO Cuoco (nome, cognome, data_nascita) VALUES ('Mario', 'Rossi', '01-01-1980');
INSERT INTO Cuoco (nome, cognome, data_nascita) VALUES ('Luigi', 'Bianchi', '15-05-1975');
INSERT INTO Cuoco (nome, cognome, data_nascita) VALUES ('Giovanni', 'Verdi', '22-09-1985');
/*INSERIMENTI NELLA TABELLA RICETTA*/
INSERT INTO Ricetta (nome, descrizione, cuoco_id) VALUES ('Spaghetti Carbonara', 'Una deliziosa carbonara.', (SELECT id FROM Cuoco WHERE nome = 'Luigi' AND cognome = 'Bianchi'));
INSERT INTO Ricetta (nome, descrizione, cuoco_id) VALUES ('Pizza Margherita', 'Una classica pizza margherita.', (SELECT id FROM Cuoco WHERE nome = 'Mario' AND cognome = 'Rossi'));
INSERT INTO Ricetta (nome, descrizione, cuoco_id) VALUES ('Tiramisu', 'Un dolce tiramisu tradizionale.', (SELECT id FROM Cuoco WHERE nome = 'Giovanni' AND cognome = 'Verdi'));
/*INSERIMENTI NELLA TABELLA RICETTA*/
INSERT INTO Ingrediente (nome, quantita, ricetta_id) VALUES ('Spaghetti', '200g', (SELECT id FROM Ricetta WHERE nome = 'Spaghetti Carbonara'));
INSERT INTO Ingrediente (nome, quantita, ricetta_id) VALUES ('Uova', '4', (SELECT id FROM Ricetta WHERE nome = 'Spaghetti Carbonara'));
INSERT INTO Ingrediente (nome, quantita, ricetta_id) VALUES ('Pancetta', '100g', (SELECT id FROM Ricetta WHERE nome = 'Spaghetti Carbonara'));
INSERT INTO Ingrediente (nome, quantita, ricetta_id) VALUES ('Farina', '300g', (SELECT id FROM Ricetta WHERE nome = 'Pizza Margherita'));
INSERT INTO Ingrediente (nome, quantita, ricetta_id) VALUES ('Pomodoro', '200g', (SELECT id FROM Ricetta WHERE nome = 'Pizza Margherita'));
INSERT INTO Ingrediente (nome, quantita, ricetta_id) VALUES ('Mozzarella', '150g', (SELECT id FROM Ricetta WHERE nome = 'Pizza Margherita'));
INSERT INTO Ingrediente (nome, quantita, ricetta_id) VALUES ('Mascarpone', '250g', (SELECT id FROM Ricetta WHERE nome = 'Tiramisu'));
INSERT INTO Ingrediente (nome, quantita, ricetta_id) VALUES ('Caffe', '100ml', (SELECT id FROM Ricetta WHERE nome = 'Tiramisu'));
INSERT INTO Ingrediente (nome, quantita, ricetta_id) VALUES ('Cacao', 'q.b.', (SELECT id FROM Ricetta WHERE nome = 'Tiramisu'));