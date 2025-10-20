// Recupera dados do localStorage
const nome = localStorage.getItem('produtoNome');
const preco = localStorage.getItem('produtoPreco');
const imagemSrc = localStorage.getItem('produtoImagem');

// Atualiza o conteúdo da página
document.getElementById('nome-produto').textContent = nome;
document.getElementById('preco-produto').textContent = preco;

// Seleciona elementos
const imgPrincipal = document.getElementById("imagem-principal");
const thumbnailsContainer = document.querySelector('.thumbnails');

// Função para criar miniatura e adicionar evento
function criarMiniatura(src) {
  const thumb = document.createElement('img');
  thumb.src = src;
  thumb.addEventListener("click", () => {
    imgPrincipal.src = src;
  });
  thumbnailsContainer.appendChild(thumb);
}

// Adiciona imagem principal como miniatura
if (imagemSrc) {
  imgPrincipal.src = imagemSrc;
  criarMiniatura(imagemSrc);
}

// Seleção de tamanho
const sizeButtons = document.querySelectorAll(".size-btn:not(.disabled)");
let tamanhoSelecionado = null;

sizeButtons.forEach(btn => {
  btn.addEventListener("click", () => {
    sizeButtons.forEach(b => b.classList.remove("selected"));
    btn.classList.add("selected");
    tamanhoSelecionado = btn.dataset.size;
  });
});

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

// Função para atualizar o contador do carrinho
function atualizarContadorCarrinho() {
  const carrinho = JSON.parse(localStorage.getItem("carrinho")) || [];
  document.querySelector(".cart-count").textContent = carrinho.length;
}

// Atualiza contador ao carregar a página
atualizarContadorCarrinho();

// Adicionar ao carrinho
document.getElementById("btn-add-cart").addEventListener("click", () => {
  if (!tamanhoSelecionado) {
    mostrarToast("Escolha um tamanho primeiro!");
    return;
  }

  const produto = {
    nome: document.querySelector(".detalhes h1")?.textContent || "Produto",
    preco: document.querySelector(".detalhes .preco")?.textContent || "0,00",
    tamanho: tamanhoSelecionado,
    imagem: imgPrincipal.src
  };

  let carrinho = JSON.parse(localStorage.getItem("carrinho")) || [];
  carrinho.push(produto);
  localStorage.setItem("carrinho", JSON.stringify(carrinho));
  atualizarContadorCarrinho();

  // --- Cálculo do valor total dos produtos ---
  const totalProdutos = carrinho.reduce((soma, item) => {
    const precoNum = parseFloat(item.preco.replace('R$', '').replace(',', '.'));
    return soma + (isNaN(precoNum) ? 0 : precoNum);
  }, 0);

  localStorage.setItem('resumoCompra', JSON.stringify({
    totalProdutos: totalProdutos,
    frete: 15.00,
    cupomDesconto: 0
  }));

  mostrarToast(`Produto adicionado ao carrinho - Tamanho ${tamanhoSelecionado}`);
});

// Seleciona o botão "Comprar Agora" dentro do modal
document.getElementById('modal-carrinho').addEventListener('click', function(e) {
  if (e.target && e.target.id === 'btn-comprar-agora') {
    window.location.href = 'pagamento/pagamento.html'; // troque pelo link da sua aba de checkout
  }
});

// Abrir modal do carrinho
document.getElementById('btn-ver-carrinho').addEventListener('click', () => {
  const lista = document.getElementById('lista-carrinho');
  lista.innerHTML = '';
  let carrinho = JSON.parse(localStorage.getItem('carrinho')) || [];
  if (carrinho.length === 0) {
    lista.innerHTML = '<p>O carrinho está vazio.</p>';
  } else {
    carrinho.forEach((item, index) => {
      const div = document.createElement('div');
      div.style.marginBottom = '12px';
      div.innerHTML = `
        <img src="${item.imagem}" style="width:40px;vertical-align:middle;border-radius:4px;margin-right:8px;">
        <strong>${item.nome}</strong> - ${item.tamanho} - <span>${item.preco}</span>
        <button class="remover-item" data-index="${index}" style="margin-left:12px;background:#ff4d4d;color:#fff;border:none;padding:4px 10px;border-radius:6px;cursor:pointer;">Remover</button>
      `;
      lista.appendChild(div);
    });

    // Adiciona evento para remover
    lista.querySelectorAll('.remover-item').forEach(btn => {
      btn.addEventListener('click', function() {
        const idx = parseInt(this.getAttribute('data-index'));
        carrinho.splice(idx, 1);
        localStorage.setItem('carrinho', JSON.stringify(carrinho));
        atualizarContadorCarrinho();
        // Reabre o modal atualizado
        document.getElementById('btn-ver-carrinho').click();
      });
    });
  }
  document.getElementById('modal-carrinho').style.display = 'flex';
});

// Fechar modal
document.getElementById('close-modal').onclick = function() {
  document.getElementById('modal-carrinho').style.display = 'none';
};

// Fecha modal ao clicar fora do conteúdo
document.getElementById('modal-carrinho').onclick = function(e) {
  if (e.target === this) this.style.display = 'none';
};
