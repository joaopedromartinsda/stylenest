document.addEventListener('DOMContentLoaded', function() {
  // Oculta o carrinho inicialmente
  const divCarrinho = document.getElementById('carrinho');
  if (divCarrinho) {
    divCarrinho.style.display = 'none';
    divCarrinho.style.opacity = '0';
    divCarrinho.style.transform = 'translateY(-20px)';
  }

  atualizarContadorCarrinho();
  atualizarListaCarrinho();

  // Mostrar/ocultar carrinho ao clicar no ícone
  const cartIcon = document.querySelector('.cart-icon');
  if (cartIcon && divCarrinho) {
    cartIcon.style.cursor = 'pointer';
    cartIcon.addEventListener('click', function(e) {
      e.stopPropagation();
      if (divCarrinho.style.display === 'none' || divCarrinho.style.opacity === '0') {
        divCarrinho.style.display = 'block';
        setTimeout(() => {
          divCarrinho.style.opacity = '1';
          divCarrinho.style.transform = 'translateY(0)';
        }, 10);
      } else {
        divCarrinho.style.opacity = '0';
        divCarrinho.style.transform = 'translateY(-20px)';
        setTimeout(() => {
          divCarrinho.style.display = 'none';
        }, 200);
      }
    });
    document.addEventListener('click', function(event) {
      if (divCarrinho.style.display === 'block' && !divCarrinho.contains(event.target) && event.target !== cartIcon) {
        divCarrinho.style.opacity = '0';
        divCarrinho.style.transform = 'translateY(-20px)';
        setTimeout(() => {
          divCarrinho.style.display = 'none';
        }, 200);
      }
    });
  }

  // Adiciona eventos aos botões "Adicionar ao Carrinho"
  const botoesAdicionar = document.querySelectorAll('.botao-adicionar');
  botoesAdicionar.forEach(function(botao) {
    botao.addEventListener('click', function() {
      const produto = botao.closest('.produto');
      const nome = produto.querySelector('h2')?.innerText || '';
      const preco = produto.querySelector('.preco')?.innerText || '';
      adicionarAoCarrinho(nome, preco);
      atualizarContadorCarrinho();
      atualizarListaCarrinho();
    });
  });

  // Adiciona eventos aos botões "Comprar"
  const botoesComprar = document.querySelectorAll('.botao-comprar');
  botoesComprar.forEach(function(botao) {
    botao.addEventListener('click', function() {
      const produto = botao.closest('.produto');
      const nome = produto.querySelector('h2')?.textContent || '';
      const preco = produto.querySelector('.preco')?.textContent || '';
      const imagemSrc = produto.querySelector('img')?.src || '';
      localStorage.setItem('produtoNome', nome);
      localStorage.setItem('produtoPreco', preco);
      localStorage.setItem('produtoImagem', imagemSrc);
      window.location.href = 'pagamento/pagamento.html';
    });
  });

  // Torna o card do produto clicável e envia dados para o checkout
  document.querySelectorAll('.produto').forEach(function(card) {
    card.style.cursor = 'pointer';
    card.addEventListener('click', function() {
      const nome = card.querySelector('h2')?.textContent;
      const preco = card.querySelector('.preco')?.textContent;
      const imagemSrc = card.querySelector('img')?.src;
      if (nome && preco && imagemSrc) {
        localStorage.setItem('produtoNome', nome);
        localStorage.setItem('produtoPreco', preco);
        localStorage.setItem('produtoImagem', imagemSrc);
        window.location.href = 'checkout.html';
      }
    });
  });

  if (sessionStorage.getItem('compra-em-andamento')) {
    sessionStorage.removeItem('compra-em-andamento');
    return;
  }
});

function adicionarAoCarrinho(nome, preco) {
  let carrinho = JSON.parse(localStorage.getItem('carrinho')) || [];
  carrinho.push({ nome, preco });
  localStorage.setItem('carrinho', JSON.stringify(carrinho));
}

function atualizarContadorCarrinho() {
  let carrinho = JSON.parse(localStorage.getItem('carrinho')) || [];
  const contador = document.querySelector('.cart-count');
  if (contador) {
    contador.textContent = carrinho.length;
  }
}

function atualizarListaCarrinho() {
  const lista = document.getElementById('lista-carrinho');
  if (!lista) return;
  let carrinho = JSON.parse(localStorage.getItem('carrinho')) || [];
  lista.innerHTML = '';
  carrinho.forEach(function(item, index) {
    const li = document.createElement('li');
    const info = document.createElement('div');
    const nomeSpan = document.createElement('span');
    nomeSpan.textContent = item.nome;
    nomeSpan.style.fontWeight = "bold";
    info.appendChild(nomeSpan);
    info.appendChild(document.createTextNode(' - ' + item.preco));
    const btnDiv = document.createElement('div');
    btnDiv.className = 'remover-container';
    const btnRemover = document.createElement('button');
    btnRemover.textContent = 'Remover';
    btnRemover.onclick = function(e) {
      e.stopPropagation();
      removerDoCarrinho(index);
    };
    btnDiv.appendChild(btnRemover);
    li.appendChild(info);
    li.appendChild(btnDiv);
    lista.appendChild(li);
  });

  // Botão de comprar dentro do carrinho
  if (carrinho.length > 0) {
    const btnComprar = document.createElement('button');
    btnComprar.textContent = 'Comprar Agora';
    btnComprar.className = 'botao-comprar-carrinho';
    // Adiciona o evento de clique para redirecionar ao checkout
    btnComprar.onclick = function() {
      window.location.href = 'pagamento/pagamento.html';
    };
    lista.appendChild(btnComprar);
  }
}

function removerDoCarrinho(index) {
  let carrinho = JSON.parse(localStorage.getItem('carrinho')) || [];
  carrinho.splice(index, 1);
  localStorage.setItem('carrinho', JSON.stringify(carrinho));
  atualizarContadorCarrinho();
  atualizarListaCarrinho();
}