document.addEventListener('DOMContentLoaded', () => {
  const botoesAdicionar = document.querySelectorAll('.botao-adicionar');
  const botoesComprar = document.querySelectorAll('.botao-comprar');
  const listaCarrinho = document.getElementById('lista-carrinho');
  const totalCarrinho = document.getElementById('total-carrinho');

  let carrinho = JSON.parse(localStorage.getItem('carrinho')) || [];
  let frete = 9.99;
  let descontoPix = 0;
  let cupomDesconto = 0;
  let cupomAplicado = false;

  function atualizarCarrinho() {
    listaCarrinho.innerHTML = '';
    let total = 0;

    carrinho.forEach((item, index) => {
      const li = document.createElement('li');
      li.textContent = `${item.nome} - ${item.preco}`;

      const botaoRemover = document.createElement('button');
      botaoRemover.textContent = 'Remover';
      botaoRemover.style.marginLeft = '10px';
      botaoRemover.addEventListener('click', () => {
        carrinho.splice(index, 1);
        salvarCarrinho();
        atualizarCarrinho();
      });

      li.appendChild(botaoRemover);
      listaCarrinho.appendChild(li);

      total += parseFloat(item.preco.replace('R$', '').replace(',', '.'));
    });

    totalCarrinho.textContent = `Total: R$ ${total.toFixed(2).replace('.', ',')}`;
  }

  function salvarCarrinho() {
    localStorage.setItem('carrinho', JSON.stringify(carrinho));
  }

  botoesAdicionar.forEach(botao => {
    botao.addEventListener('click', () => {
      const produto = botao.parentElement;
      const nome = produto.querySelector('h2').textContent;
      const preco = produto.querySelector('.preco').textContent;

      carrinho.push({ nome, preco });
      salvarCarrinho();
      atualizarCarrinho();
    });
  });

  botoesComprar.forEach(botao => {
    botao.addEventListener('click', () => {
      const produto = botao.parentElement;
      const nome = produto.querySelector('h2').textContent;
      const preco = produto.querySelector('.preco').textContent;

      // Simula compra imediata: salva no carrinho e redireciona
      carrinho.push({ nome, preco });
      salvarCarrinho();
      window.location.href = 'checkout.html'; // substitua com sua página real
    });
  });

  function renderCarrinho() {
    const lista = document.getElementById('lista-carrinho');
    lista.innerHTML = '';
    if (carrinho.length === 0) {
      lista.innerHTML = '<li style="color:#aaa;">Seu carrinho está vazio.</li>';
      document.getElementById('valor-produtos').textContent = 'R$ 0,00';
      document.getElementById('valor-frete').textContent = 'R$ 0,00';
      document.getElementById('desconto-pix').textContent = '- R$ 0,00';
      document.getElementById('total-compra').textContent = 'R$ 0,00';
      return;
    }
    let totalProdutos = 0;
    carrinho.forEach((item, idx) => {
      totalProdutos += parseFloat(item.preco.replace('R$', '').replace(',', '.'));
      const li = document.createElement('li');
      li.className = 'produto-carrinho';
      li.innerHTML = `
        <img src="${item.imagem}" alt="${item.nome}">
        <div class="info">
          <div>${item.nome}</div>
          <div style="font-size:0.95em;color:#aaa;">Tamanho: ${item.tamanho || 'M'}</div>
        </div>
        <div class="preco">${item.preco}</div>
        <button style="background:#222;color:#fff;padding:4px 10px;cursor:pointer;" onclick="removerDoCarrinho(${idx})">🗑️</button>
      `;
      lista.appendChild(li);
    });
    document.getElementById('valor-produtos').textContent = `R$ ${totalProdutos.toFixed(2).replace('.', ',')}`;
    document.getElementById('valor-frete').textContent = `R$ ${frete.toFixed(2).replace('.', ',')}`;
    document.getElementById('desconto-pix').textContent = `- R$ ${descontoPix.toFixed(2).replace('.', ',')}`;
    let total = totalProdutos + frete - descontoPix - cupomDesconto;
    document.getElementById('total-compra').textContent = `R$ ${total.toFixed(2).replace('.', ',')}`;
    // Salva para a próxima etapa
    localStorage.setItem('resumoCompra', JSON.stringify({
      totalProdutos, frete, descontoPix, cupomDesconto, total
    }));
  }

  window.removerDoCarrinho = function(idx) {
    carrinho.splice(idx, 1);
    localStorage.setItem('carrinho', JSON.stringify(carrinho));
    renderCarrinho();
  };

  document.getElementById('calcular-frete').onclick = function() {
    const cep = document.getElementById('cep').value;
    if (cep.match(/^\d{5}-?\d{3}$/)) {
      document.getElementById('frete-info').textContent = 'Frete calculado: R$ 9,99';
      frete = 9.99;
    } else {
      document.getElementById('frete-info').textContent = 'CEP inválido';
      frete = 0;
    }
    renderCarrinho();
  };

  document.getElementById('aplicar-cupom').onclick = function() {
    const cupom = document.getElementById('cupom').value.trim().toUpperCase();
    if (cupom === 'STYLE10' && !cupomAplicado) {
      cupomDesconto = 10;
      cupomAplicado = true;
      document.getElementById('cupom-info').textContent = 'Cupom aplicado: -R$ 10,00';
    } else if (cupomAplicado) {
      document.getElementById('cupom-info').textContent = 'Cupom já aplicado.';
    } else {
      document.getElementById('cupom-info').textContent = 'Cupom inválido.';
    }
    renderCarrinho();
  };

  // Desconto Pix só será aplicado na tela de pagamento
  descontoPix = 0;

  document.getElementById('continuar-pagamento').onclick = function() {
    window.location.href = "pagamento/pagamento.html";
  };

  atualizarCarrinho();
  renderCarrinho();
});