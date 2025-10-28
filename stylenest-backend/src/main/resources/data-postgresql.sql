INSERT INTO produtos (nome, descricao, preco, imagem_url, categoria)
SELECT 'Camiseta Basica Preta',
       'Camiseta 100% algodao, modelagem unissex com toque macio.',
       79.90,
       'https://images.unsplash.com/photo-1521572267360-ee0c2909d518?auto=format&fit=crop&w=600&q=80',
       'mas'
WHERE NOT EXISTS (SELECT 1 FROM produtos WHERE nome = 'Camiseta Basica Preta');

INSERT INTO produtos (nome, descricao, preco, imagem_url, categoria)
SELECT 'Calca Jeans Slim',
       'Calca jeans azul escuro com elastano e acabamento moderno.',
       189.90,
       'https://images.unsplash.com/photo-1489987707025-afc232f7ea0f?auto=format&fit=crop&w=600&q=80',
       'mas'
WHERE NOT EXISTS (SELECT 1 FROM produtos WHERE nome = 'Calca Jeans Slim');

INSERT INTO produtos (nome, descricao, preco, imagem_url, categoria)
SELECT 'Vestido Floral Midi',
       'Vestido midi com estampa floral exclusiva e tecido leve.',
       229.90,
       'https://images.unsplash.com/photo-1524504388940-b1c1722653e1?auto=format&fit=crop&w=600&q=80',
       'fem'
WHERE NOT EXISTS (SELECT 1 FROM produtos WHERE nome = 'Vestido Floral Midi');

INSERT INTO produtos (nome, descricao, preco, imagem_url, categoria)
SELECT 'Tenis Casual Branco',
       'Tenis branco em couro sintetico com sola emborrachada.',
       249.90,
       'https://images.unsplash.com/photo-1526170375885-4d8ecf77b99f?auto=format&fit=crop&w=600&q=80',
       'kids'
WHERE NOT EXISTS (SELECT 1 FROM produtos WHERE nome = 'Tenis Casual Branco');
