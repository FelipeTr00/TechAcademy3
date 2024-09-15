// #### REGISTER ####

document.getElementById('register-form').addEventListener('submit', function (event) {
    event.preventDefault();

    const nickname = document.getElementById('register-nickname').value;
    const password = document.getElementById('register-passwd').value;
    const confirmPassword = document.getElementById('register-passwd2').value;

    if (password !== confirmPassword) {
        alert("As senhas não coincidem!");
        return;
    }

    const user = {
        name: nickname,
        password: password
    };

    // REQUISIÇÃO POST NO SERVIDOR
    
    fetch('http://127.0.0.1:5150/insert-user', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    })

        .then(response => response.json())
        .then(data => {
            if (data.message) {
                alert(data.message);  // Mostra a mensagem de sucesso ou erro
            } else {
                alert("Erro ao registrar usuário");
            }
        })
        .catch(error => {
            console.error('Erro ao registrar usuário:', error);
        });
});

// #### LOGIN ####

document.addEventListener('DOMContentLoaded', function() {
    // Inicializa o componente Collapsible do Materialize
    var elems = document.querySelectorAll('.collapsible');
    var instances = M.Collapsible.init(elems, {});

    // Adiciona evento de submit ao formulário
    document.querySelector('form').addEventListener('submit', function(event) {
        event.preventDefault(); // Evita o envio padrão do formulário

        // Captura o nickname e a senha do formulário
        const nickname = document.getElementById('login-nickname').value;
        const password = document.getElementById('login-passwd').value;

        // Envia uma solicitação POST para o servidor
        fetch('http://localhost:5150/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                nickname: nickname,
                password: password
            })
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                // Redireciona para a cena atual ou página do jogo
                window.location.href = `/game?scene=${data.currentSceneId}`;
            } else {
                // Exibe uma mensagem de erro
                M.toast({html: 'Nickname ou senha inválidos!'});
            }
        })
        .catch(error => {
            console.error('Error:', error);
            M.toast({html: 'Erro ao conectar com o servidor.'});
        });
    });
});

