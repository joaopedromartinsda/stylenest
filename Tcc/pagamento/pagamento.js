// Recupera carrinho do localStorage
let carrinho = JSON.parse(localStorage.getItem("carrinho")) || [];
const container = document.getElementById("pedido-produtos");

// Função para exibir produtos
function mostrarCarrinho() {
  container.innerHTML = "";
  if (carrinho.length === 0) {
    container.innerHTML = "<p>Seu carrinho está vazio.</p>";
    return;
  }

  carrinho.forEach((produto, index) => {
    const item = document.createElement("div");
    item.className = "carrinho-item";
    item.innerHTML = `
      <p><strong>${produto.nome}</strong> - Tamanho: ${produto.tamanho} - Preço: ${produto.preco}</p>
      <button class="remover" data-index="${index}">Remover</button>
    `;
    container.appendChild(item);
  });

  document.querySelectorAll(".remover").forEach(btn => {
    btn.addEventListener("click", () => {
      const i = btn.dataset.index;
      carrinho.splice(i, 1);
      localStorage.setItem("carrinho", JSON.stringify(carrinho));
      atualizarResumoCompra(); // <-- Atualiza o resumo ao remover
      mostrarCarrinho();
      renderResumoPedido();    // <-- Atualiza o resumo do pedido
      atualizarResumo('cartao'); // <-- Atualiza valores finais
    });
  });
}

// Função de toast
function mostrarToast(mensagem) {
  let toast = document.querySelector('.toast-msg');
  if (!toast) {
    toast = document.createElement('div');
    toast.className = 'toast-msg';
    document.body.appendChild(toast);
  }
  toast.textContent = mensagem;
  toast.classList.add('show');
  setTimeout(() => toast.classList.remove('show'), 2200);
}

function mostrarMensagemSucesso() {
  const msg = document.getElementById("mensagem-sucesso");
  msg.classList.add("show");   // mostra mensagem

  setTimeout(() => {
    msg.classList.remove("show");  // esconde após 3s
    window.location.href = "../index.html"; // redireciona
  }, 3000);
}

function atualizarResumoCompra() {
  const totalProdutos = carrinho.reduce((soma, item) => {
    const precoNum = parseFloat(item.preco.replace('R$', '').replace(',', '.'));
    return soma + (isNaN(precoNum) ? 0 : precoNum);
  }, 0);

  localStorage.setItem('resumoCompra', JSON.stringify({
    totalProdutos: totalProdutos,
    frete: 15.00,
    cupomDesconto: 0
  }));
}

// Inicial
document.addEventListener('DOMContentLoaded', function() {

  // Adiciona o resumo do pedido
  function renderResumoPedido() {
    const pedidoDiv = document.getElementById('pedido-produtos');
    const carrinho = JSON.parse(localStorage.getItem('carrinho')) || [];
    if (carrinho.length === 0) {
      pedidoDiv.innerHTML = '<p style="color:#aaa;">Seu carrinho está vazio.</p>';
      return;
    }
    pedidoDiv.innerHTML = '';
    carrinho.forEach(item => {
      const div = document.createElement('div');
      div.className = 'pedido-produto';
      div.innerHTML = `
        <img src="${item.imagem}" alt="${item.nome}">
        <div class="pedido-produto-info">
          <div class="nome">${item.nome}</div>
          <div class="detalhes">
            Quantidade: ${item.quantidade || 1}<br>
            Cor: ${item.cor || 'Preto'}<br>
            Tamanho: ${item.tamanho || 'M'}<br>
            ${item.estilo ? `Estilo: ${item.estilo}<br>` : ''}
          </div>
          <div class="preco">${item.preco}</div>
        </div>
      `;
      pedidoDiv.appendChild(div);
    });
  }

  renderResumoPedido();

  // Resumo compra
  const resumo = JSON.parse(localStorage.getItem('resumoCompra')) || {};
  let descontoPix = 0;
  let totalPix = 0;
  let totalCartao = 0;

  function atualizarResumo(tipo) {
    let valorProdutos = resumo.totalProdutos || 0;
    let frete = resumo.frete || 0;
    let cupomDesconto = resumo.cupomDesconto || 0;
    descontoPix = tipo === 'pix' ? valorProdutos * 0.05 : 0;
    totalPix = valorProdutos + frete - descontoPix - cupomDesconto;
    totalCartao = valorProdutos + frete - cupomDesconto;

    document.getElementById('valor-produtos').textContent = `R$ ${valorProdutos.toFixed(2).replace('.', ',')}`;
    document.getElementById('valor-frete').textContent = `R$ ${frete.toFixed(2).replace('.', ',')}`;
    document.getElementById('desconto-pix').textContent = descontoPix > 0 ? `- R$ ${descontoPix.toFixed(2).replace('.', ',')}` : '- R$ 0,00';
    document.getElementById('total-compra-pix').textContent = tipo === 'pix'
      ? `R$ ${totalPix.toFixed(2).replace('.', ',')} no Pix`
      : `R$ ${totalCartao.toFixed(2).replace('.', ',')} no cartão`;
    document.getElementById('total-compra-cartao').textContent = tipo === 'pix'
      ? `R$ ${totalCartao.toFixed(2).replace('.', ',')} no cartão`
      : '';
  }

  atualizarResumo('cartao'); // Coloque esta linha aqui para garantir que o valor aparece ao abrir a página

  // Controle Cartão/Pix
  const radios = document.querySelectorAll('input[name="pagamento"]');
  const cartaoDiv = document.getElementById('cartao-dados');
  const pixDiv = document.getElementById('pix-dados');
  const cpfPix = document.getElementById('cpf-pix');

  radios.forEach(radio => {
    radio.addEventListener('change', () => {
      atualizarResumo(radio.value);
      if (radio.value === 'cartao') {
        cartaoDiv.style.display = 'block';
        pixDiv.style.display = 'none';
      } else {
        cartaoDiv.style.display = 'none';
        pixDiv.style.display = 'block';
      }
    });
  });

  // Máscaras
  const numeroCartao = document.getElementById("numero-cartao");
  const validadeCartao = document.getElementById("validade-cartao");

  if (numeroCartao) numeroCartao.addEventListener("input", function() {
    this.value = this.value.replace(/\D/g, '').replace(/(\d{4})(?=\d)/g, '$1 ').trim();
  });

  if (validadeCartao) validadeCartao.addEventListener("input", function() {
    this.value = this.value.replace(/\D/g, '').replace(/(\d{2})(\d{0,2})/, '$1/$2').substr(0,5);
  });

  if (cpfPix) cpfPix.addEventListener("input", function() {
    this.value = this.value.replace(/\D/g, '').replace(/(\d{3})(\d)/, '$1.$2').replace(/(\d{3})(\d)/, '$1.$2').replace(/(\d{3})(\d{1,2})$/, '$1-$2').substr(0,14);
  });

  // Finalizar compra
  document.getElementById('form-pagamento').onsubmit = function(e) {
    e.preventDefault();
    const selected = document.querySelector('input[name="pagamento"]:checked');
    if (!selected) {
      mostrarToast("Escolha uma forma de pagamento!");
      return;
    }

    document.getElementById('btn-finalizar').addEventListener('click', function(e){
  finalizarCompra(e);
});

    // Validação Cartão
    if (selected.value === 'cartao') {
      const numero = numeroCartao.value.replace(/\s/g, '');
      const nome = document.getElementById('nome-cartao').value.trim();
      const validade = validadeCartao.value;
      const cvv = document.getElementById('cvv-cartao').value;
      if (numero.length < 16 || !nome || validade.length < 5 || cvv.length < 3) {
        mostrarToast("Preencha todos os dados do cartão corretamente.");
        return;
      }
    }

    // Validação Pix
    if (selected.value === 'pix') {
      const cpf = cpfPix.value.replace(/\D/g, '');
      if (cpf.length !== 11) {
        mostrarToast("Digite um CPF válido.");
        return;
      }
    }

    // Limpa carrinho
    localStorage.removeItem('carrinho');
    carrinho = [];
    mostrarCarrinho();

    // Mostra mensagem de sucesso
    mostrarMensagemSucesso();
  };
});

// Inicializa
mostrarCarrinho();