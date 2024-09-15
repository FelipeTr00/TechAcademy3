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

// #################### LOGIN ####################

document.getElementById('login-form').addEventListener('submit', async function(event) {
    event.preventDefault(); // Impede o envio padrão do formulário

    // Captura os valores dos campos de entrada
    const username = document.getElementById('login-nickname').value;
    const password = document.getElementById('login-passwd').value;

    // Cria o objeto JSON com "username" como chave
    const loginData = {
        username: username, // Certifique-se de que isso é "username"
        password: password
    };

    console.log('Dados de login enviados:', loginData); // Verifique o log no console

    try {
        // Envia a requisição POST para o servidor (rota de login)
        const response = await fetch('http://127.0.0.1:5150/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(loginData)
        });

        // Verifica se a resposta foi bem-sucedida
        const responseText = await response.text(); // Pega a resposta como texto
        console.log('Resposta do servidor (login):', responseText); // Log adicional

        if (response.ok) {
            alert('Login bem-sucedido!');
        } else {
            console.log('Erro (login):', responseText);
            alert('Falha no login: ' + responseText);
        }

    } catch (error) {
        console.error('Erro na requisição (login):', error);
        alert('Ocorreu um erro ao processar sua solicitação de login.');
    }
});