// Configuração da API
const API_URL = 'https://stylenest-mi9i.onrender.com/api/auth';

// Classe para gerenciar autenticação
class AuthService {
    // Cadastrar novo usuário
    static async cadastrar(dados) {
        try {
            const response = await fetch(`${API_URL}/cadastro`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(dados)
            });
            
            const result = await response.json();
            
            if (response.ok) {
                // Salvar token e dados do usuário
                this.salvarSessao(result);
                return { sucesso: true, dados: result };
            } else {
                return { sucesso: false, mensagem: result.mensagem || 'Erro ao cadastrar' };
            }
        } catch (error) {
            console.error('Erro ao cadastrar:', error);
            return { sucesso: false, mensagem: 'Erro de conexão com o servidor' };
        }
    }
    
    // Fazer login
    static async login(email, senha) {
        try {
            const response = await fetch(`${API_URL}/login`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ email, senha })
            });
            
            const result = await response.json();
            
            if (response.ok) {
                this.salvarSessao(result);
                return { sucesso: true, dados: result };
            } else {
                return { sucesso: false, mensagem: result.mensagem || 'Email ou senha inválidos' };
            }
        } catch (error) {
            console.error('Erro ao fazer login:', error);
            return { sucesso: false, mensagem: 'Erro de conexão com o servidor' };
        }
    }
    
    // Salvar sessão no navegador
    static salvarSessao(dados) {
        const usuario = {
            token: dados.token,
            id: dados.id,
            nome: dados.nome,
            email: dados.email
        };
        // Armazena os dados em variável global ao invés de localStorage
        window.usuarioLogado = usuario;
    }
    
    // Fazer logout
    static logout() {
        window.usuarioLogado = null;
        window.location.href = 'index.html';
    }
    
    // Verificar se está logado
    static estaLogado() {
        return window.usuarioLogado && window.usuarioLogado.token;
    }
    
    // Obter usuário logado
    static getUsuario() {
        return window.usuarioLogado || null;
    }
    
    // Obter token
    static getToken() {
        const usuario = this.getUsuario();
        return usuario ? usuario.token : null;
    }
    
    // Verificar token com servidor
    static async verificarToken() {
        const token = this.getToken();
        if (!token) return false;
        
        try {
            const response = await fetch(`${API_URL}/verificar`, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            return response.ok;
        } catch (error) {
            return false;
        }
    }
}

// ==================== EXEMPLO DE USO ====================

// Formulário de Cadastro
document.getElementById('formCadastro')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const dados = {
        nome: document.getElementById('nome').value,
        email: document.getElementById('email').value,
        senha: document.getElementById('senha').value,
        telefone: document.getElementById('telefone')?.value,
        cpf: document.getElementById('cpf')?.value,
        dataNascimento: document.getElementById('dataNascimento')?.value || null
    };
    
    const resultado = await AuthService.cadastrar(dados);
    
    if (resultado.sucesso) {
        alert('Cadastro realizado com sucesso!');
        window.location.href = 'index.html';
    } else {
        alert(resultado.mensagem);
    }
});

// Formulário de Login
document.getElementById('formLogin')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;
    
    const resultado = await AuthService.login(email, senha);
    
    if (resultado.sucesso) {
        alert(`Bem-vindo(a), ${resultado.dados.nome}!`);
        window.location.href = 'index.html';
    } else {
        alert(resultado.mensagem);
    }
});

// Botão de Logout
document.getElementById('btnLogout')?.addEventListener('click', () => {
    if (confirm('Deseja realmente sair?')) {
        AuthService.logout();
    }
});

// Verificar se está logado ao carregar a página
window.addEventListener('DOMContentLoaded', () => {
    const usuario = AuthService.getUsuario();
    
    if (usuario) {
        // Atualizar interface para usuário logado
        const nomeUsuario = document.getElementById('nomeUsuario');
        if (nomeUsuario) {
            nomeUsuario.textContent = usuario.nome;
        }
        
        // Mostrar/esconder elementos baseado no login
        document.querySelectorAll('.only-logged-in').forEach(el => {
            el.style.display = 'block';
        });
        document.querySelectorAll('.only-logged-out').forEach(el => {
            el.style.display = 'none';
        });
    } else {
        // Usuário não logado
        document.querySelectorAll('.only-logged-in').forEach(el => {
            el.style.display = 'none';
        });
        document.querySelectorAll('.only-logged-out').forEach(el => {
            el.style.display = 'block';
        });
    }
});
