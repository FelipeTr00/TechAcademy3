/* 
document.addEventListener('DOMContentLoaded', function() {
    const links = document.querySelectorAll('a[data-page]');
    const contentDiv = document.getElementById('content');

    links.forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            const page = this.getAttribute('data-page');

            fetch(`content/${page}.html`)
                .then(response => response.text())
                .then(html => {
                    contentDiv.innerHTML = html;
                    
                    M.AutoInit();
                })
                .catch(error => console.error('Erro ao carregar o conteúdo:', error));
        });
    });

    links[0].click();
});
*/

document.addEventListener('DOMContentLoaded', function() {
    const apiContent = document.getElementById('api-content');
    const terminalOutput = document.getElementById('terminal-output');
    const terminalInput = document.getElementById('terminal-input');
    const sendButton = document.getElementById('send-button');

    async function fetchContent() {
        try {
            const response = await fetch('http://localhost:5150/user'); // Altere conforme necessário
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            const data = await response.json();
            
            // Supondo que `data.scenes` contenha o conteúdo da API
            if (data.scenes && Array.isArray(data.scenes)) {
                const scene = data.scenes[0]; // Pegue a primeira cena ou adapte conforme necessário
                apiContent.innerHTML = `<strong>${scene.title}</strong><p>${scene.description}</p>`;
            } else {
                apiContent.innerHTML = 'Nenhum conteúdo encontrado.';
            }
        } catch (error) {
            console.error('Erro ao carregar o conteúdo:', error);
            apiContent.innerHTML = 'Erro ao carregar o conteúdo.';
        }
    }

    function handleTerminalInput() {
        const command = terminalInput.value.trim();
        if (command) {
            terminalOutput.innerHTML += `<p>> ${command}</p>`; // Exibe o comando
            terminalInput.value = ''; // Limpa o campo de entrada
            
            // Enviar o comando para o backend ou processar localmente
            // Exemplo de envio para o backend
            fetch('http://localhost:5150/command', { // Substitua pela sua URL de comando
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ command })
            })
            .then(response => response.text())
            .then(result => {
                terminalOutput.innerHTML += `<p>${result}</p>`; // Exibe a resposta do backend
            })
            .catch(error => {
                console.error('Erro ao enviar comando:', error);
                terminalOutput.innerHTML += `<p>Erro ao enviar comando.</p>`;
            });
        }
    }

    // Inicializar o conteúdo da API ao carregar a página
    fetchContent();

    // Adicionar evento ao botão de enviar
    sendButton.addEventListener('click', handleTerminalInput);

    // Adicionar evento ao pressionar Enter no campo de entrada
    terminalInput.addEventListener('keydown', function(e) {
        if (e.key === 'Enter') {
            e.preventDefault(); // Previne o comportamento padrão do Enter
            handleTerminalInput();
        }
    });
});
