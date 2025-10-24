// ---------------------------
// 🛒 CARRINHO
// ---------------------------
let carrinho = JSON.parse(localStorage.getItem("carrinho")) || [];
const API_BASE_URL = "https://stylenest-mi9i.onrender.com/api";

// Atualiza contador no ícone do carrinho
function atualizarCarrinho() {
  const cartCount = document.querySelector(".cart-count");
  if (cartCount) {
    cartCount.textContent = carrinho.length;
  }
  localStorage.setItem("carrinho", JSON.stringify(carrinho));
}

// Adiciona produto ao carrinho
function adicionarAoCarrinho(produto) {
  carrinho.push(produto);
  atualizarCarrinho();
  alert(`${produto.nome} adicionado ao carrinho!`);
}

// Remove produto do carrinho (pelo índice)
function removerDoCarrinho(index) {
  carrinho.splice(index, 1);
  atualizarCarrinho();
}

// Adiciona evento de clique nos botões de comprar/adicionar
document.querySelectorAll(".botao-comprar, .botao-adicionar").forEach((botao) => {
  botao.addEventListener("click", function () {
    const produto = {
      nome: this.dataset.nome || "Produto sem nome",
      preco: this.dataset.preco || "0.00",
      imagem: this.dataset.imagem || "",
    };
    adicionarAoCarrinho(produto);
  });
});

// Atualiza contador do carrinho ao carregar
document.addEventListener("DOMContentLoaded", atualizarCarrinho);

document.addEventListener("DOMContentLoaded", () => {
  const usuarioSalvo = JSON.parse(localStorage.getItem("usuario"));
if (usuarioSalvo && usuarioSalvo.nome) {
  document.getElementById("btn-login").style.display = "none";
  document.getElementById("btn-register").style.display = "none";
  document.getElementById("perfil-container").style.display = "block";

  const perfilNome = document.getElementById("perfil-nome");
  if (perfilNome) {
    const hora = new Date().getHours();
    let saudacao = "Olá";

    if (hora >= 5 && hora < 12) {
      saudacao = "Bom dia";
    } else if (hora >= 12 && hora < 18) {
      saudacao = "Boa tarde";
    } else {
      saudacao = "Boa noite";
    }

    perfilNome.textContent = `${saudacao}, ${usuarioSalvo.nome}`;
  } 
  }
});

// ---------------------------
// 🔐 LOGIN & CADASTRO (Modais)
// ---------------------------

// ---------------------------
// VISUALIZAR SENHA NO CADASTRO
// ---------------------------
function toggleSenhaCadastro() {
  const campo = document.getElementById("register-senha");
  const icone = document.getElementById("icone-senha");

  if (campo.type === "password") {
    campo.type = "text";
    icone.innerHTML = `
      <path d="M12 5c-7 0-10 7-10 7s3 7 10 7c2.5 0 4.7-1 6.5-2.6l1.4 1.4 1.4-1.4-18-18-1.4 1.4 3.3 3.3C3.6 8.5 2 12 2 12s3 7 10 7c2.5 0 4.7-1 6.5-2.6l3.1 3.1 1.4-1.4-18-18-1.4 1.4 3.3 3.3z"/>
    `;
  } else {
    campo.type = "password";
    icone.innerHTML = `
      <path d="M12 5c-7 0-10 7-10 7s3 7 10 7 10-7 10-7-3-7-10-7zm0 12c-2.761 0-5-2.239-5-5s2.239-5 5-5 5 2.239 5 5-2.239 5-5 5zm0-8c-1.654 0-3 1.346-3 3s1.346 3 3 3 3-1.346 3-3-1.346-3-3-3z"/>
    `;
  }
}

const modalLogin = document.getElementById("modal-login");
const modalRegister = document.getElementById("modal-register");
const btnLogin = document.getElementById("btn-login");
const btnRegister = document.getElementById("btn-register");
const btnsFechar = document.querySelectorAll(".btn-fechar");

// Abrir login
if (btnLogin) {
  btnLogin.addEventListener("click", (e) => {
    e.preventDefault();
    modalLogin.style.display = "flex";
  });
}

// Abrir cadastro
if (btnRegister) {
  btnRegister.addEventListener("click", (e) => {
    e.preventDefault();
    modalRegister.style.display = "flex";
  });
}

// Fechar modais
btnsFechar.forEach((btn) => {
  btn.addEventListener("click", () => {
    modalLogin.style.display = "none";
    modalRegister.style.display = "none";
  });
});

// Fechar clicando fora do modal
window.addEventListener("click", (e) => {
  if (e.target === modalLogin) modalLogin.style.display = "none";
  if (e.target === modalRegister) modalRegister.style.display = "none";
});

// ---------------------------
// 📤 SUBMISSÃO DE FORMULÁRIOS (com backend)
// ---------------------------

// LOGIN
const formLogin = document.getElementById("form-login");
if (formLogin) {
  formLogin.addEventListener("submit", async (e) => {
    e.preventDefault();
    const email = document.getElementById("login-email").value;
    const senha = document.getElementById("login-senha").value;

    try {
      const resposta = await fetch(`${API_BASE_URL}/usuarios/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, senha }),
      });

      let dados = null;
      try {
        dados = await resposta.json();
      } catch (err) {
        dados = null;
      }

      if (resposta.ok && dados) {
        alert("Login concluido com sucesso!");
        if (modalLogin) {
          modalLogin.style.display = "none";
        }
        localStorage.setItem("token", dados.token);
        localStorage.setItem("usuario", JSON.stringify(dados.usuario));
        formLogin.reset();

       // Oculta botões de login/cadastro e mostra perfil com nome
document.getElementById("btn-login").style.display = "none";
document.getElementById("btn-register").style.display = "none";
document.getElementById("perfil-container").style.display = "block";

// Exibe nome do usuário no botão de perfil
const perfilNome = document.getElementById("perfil-nome");
if (perfilNome && dados.usuario && dados.usuario.nome) {
  perfilNome.textContent = `Olá, ${dados.usuario.nome}`;
}

      } else {
        const mensagem =
          (dados && (dados.message || dados.mensagem || dados.erro)) ||
          "Erro ao fazer login";
        alert("Erro: " + mensagem);
      }
    } catch (erro) {
      alert("Erro de conexao com o servidor");
    }
  });
}

// CADASTRO
const formRegister = document.getElementById("form-register");
if (formRegister) {
  formRegister.addEventListener("submit", async (e) => {
    e.preventDefault();
    const nome = document.getElementById("register-nome").value;
    const email = document.getElementById("register-email").value;
    const senha = document.getElementById("register-senha").value;
    const confirmar = document.getElementById("register-confirmar").value;

    if (senha !== confirmar) {
      alert("As senhas nao conferem!");
      return;
    }

    try {
      const resposta = await fetch(`${API_BASE_URL}/usuarios`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ nome, email, senha }),
      });

      let dados = null;
      try {
        dados = await resposta.json();
      } catch (err) {
        dados = null;
      }

      if (resposta.ok) {
        alert("Cadastro concluido com sucesso!");
        if (modalRegister) {
          modalRegister.style.display = "none";
        }
        formRegister.reset();
      } else {
        const mensagem =
          (dados && (dados.message || dados.mensagem || dados.erro)) ||
          "Erro ao cadastrar";
        alert("Erro: " + mensagem);
      }
    } catch (erro) {
      alert("Erro de conexao com o servidor");
    }
  });
}

// ---------------------------
// 🛒 CARRINHO (CONTINUAÇÃO)
// ---------------------------
document.addEventListener("DOMContentLoaded", function() {
  const cartIcon = document.getElementById("cart-icon");
  const carrinhoDiv = document.getElementById("carrinho");
  const listaCarrinho = document.getElementById("lista-carrinho");
  const btnFechar = document.getElementById("fechar-carrinho");

  function atualizarListaCarrinho() {
    const carrinho = JSON.parse(localStorage.getItem("carrinho")) || [];
    listaCarrinho.innerHTML = "";
    if (carrinho.length === 0) {
      listaCarrinho.innerHTML = "<li>Carrinho vazio.</li>";
      return;
    }
    carrinho.forEach((item, index) => {
      const li = document.createElement("li");
      li.textContent = `${item.nome} - ${item.preco} ${item.tamanho ? "- Tam: " + item.tamanho : ""}`;

      // Botão remover
      const botaoRemover = document.createElement("button");
      botaoRemover.textContent = "Remover";
      botaoRemover.style.marginLeft = "10px";
      botaoRemover.onclick = function() {
        removerDoCarrinho(index);
        atualizarListaCarrinho();
      };

      li.appendChild(botaoRemover);
      listaCarrinho.appendChild(li);
    });
  }

  cartIcon.addEventListener("click", () => {
    atualizarListaCarrinho();
    carrinhoDiv.style.display = "block";
  });

  btnFechar.addEventListener("click", () => {
    carrinhoDiv.style.display = "none";
  });
});

// ---------------------------
// 👤 PERFIL E SAIR
// ---------------------------
const btnPerfil = document.getElementById("btn-perfil");
const menuPerfil = document.getElementById("menu-perfil");
const btnSair = document.getElementById("btn-sair");

if (btnPerfil) {
  btnPerfil.addEventListener("click", () => {
    menuPerfil.style.display = menuPerfil.style.display === "block" ? "none" : "block";
  });
}

if (btnSair) {
  btnSair.addEventListener("click", () => {
    // Limpa dados do usuário
    localStorage.removeItem("token");
    localStorage.removeItem("usuario");

    // Oculta perfil e mostra login/cadastro
    document.getElementById("perfil-container").style.display = "none";
    document.getElementById("btn-login").style.display = "inline-block";
    document.getElementById("btn-register").style.display = "inline-block";
  });
}
