document.addEventListener('DOMContentLoaded', function () {
    const sidenavElems = document.querySelectorAll('.sidenav');
    const sidenavs = M.Sidenav.init(sidenavElems);
    M.Collapsible.init(document.querySelectorAll('.collapsible'));

    showSection('game');

    const commandInput = document.getElementById('command-input');
    const commandButton = document.querySelector('.command-input button');

    // Adiciona o evento para pressionar 'Enter' no input
    commandInput.addEventListener('keydown', function (event) {
        if (event.key === 'Enter') {
            event.preventDefault(); 
            executeCommand(); 
        }
    });

    document.querySelectorAll('.nav-link').forEach(link => {
        link.addEventListener('click', function (event) {
            event.preventDefault();
            const page = this.getAttribute('data-page');
            showSection(page);

            sidenavs.forEach(sidenav => sidenav.close());
        });
    });
});

// Função para mostrar a seção ativa
function showSection(id) {
    document.querySelectorAll('.content-section').forEach(section => {
        section.classList.remove('active-section');
    });
    document.getElementById(id).classList.add('active-section');

    const terminal = document.querySelector('.terminal');
    if (id === 'game') {
        terminal.classList.remove('hidden');
    } else {
        terminal.classList.add('hidden');
    }
}

// Função para criar listas de cenas e itens
function createList(items, parentId, updateTitle) {
    const ul = document.getElementById(parentId);
    ul.innerHTML = '';

    items.forEach(item => {
        const li = document.createElement('li');
        li.classList.add('collection-item');

        if (item.title) {
            li.innerHTML = `<strong>${item.title}</strong><br><p>${item.description}</p>`;
            if (updateTitle) {
                document.getElementById('scene-title').innerHTML = `Cena: ${item.sceneId}`;
            }
        } else if (item.item) {
            li.innerHTML = `<strong>Item:</strong> ${item.item}<br><p>${item.descrItem}</p>`;
        }

        ul.appendChild(li);
    });
}

// Função para buscar dados de uma cena específica
async function fetchData(scene) {
    try {
        const response = await fetch(`http://localhost:5150/${scene}`);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const data = await response.json();

        createList(data.scenes, 'scene-list', true);
        createList(data.items, 'items-list', false);

    } catch (error) {
        console.error('There has been a problem with your fetch operation:', error);
    }
}

// Função para executar o comando
async function executeCommand() {
    let command = document.getElementById('command-input').value.trim().toLowerCase();

    // Usar switch para lidar com diferentes comandos
    switch (command) {
        case 'start':
            // Modifica o comando para "use start"
            command = 'use start';
            break;

        case 'check itens':
            // Mostra a seção de itens
            document.querySelector('.items').style.display = 'block';
            return; // Sai da função sem fazer mais nada

        default:
            // Para outros comandos, continue o fluxo normal
            // Oculta os itens ao avançar para a próxima cena
            document.querySelector('.items').style.display = 'none';
            break;
    }

    // Executa a chamada à API
    try {
        // Envia o comando para o backend na porta correta
        const response = await fetch('http://localhost:5150/execute-command', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ command: command }) // Usa o comando modificado se necessário
        });

        if (!response.ok) {
            throw new Error('Erro na resposta da API');
        }

        const data = await response.json();

        // Verifica se há algum erro no retorno da API
        if (data.error) {
            console.error('Erro:', data.error);
            return;
        }

        // Obtém o valor da cena (target) do retorno
        const scene = data.target;

        // Faz a requisição para obter os dados da nova cena
        fetchData(scene);

    } catch (error) {
        console.error('Erro ao executar comando:', error);
    }
}

