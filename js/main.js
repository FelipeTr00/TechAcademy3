
document.addEventListener('DOMContentLoaded', function () {
        const sidenavElems = document.querySelectorAll('.sidenav');
        const sidenavs = M.Sidenav.init(sidenavElems);
        M.Collapsible.init(document.querySelectorAll('.collapsible'));

        showSection('game');

        const commandInput = document.getElementById('command-input');
        const commandButton = document.querySelector('.command-input button');

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

async function executeCommand() {
    let command = document.getElementById('command-input').value.trim().toLowerCase();

    // ######################################
    // #### SWITCH CASE PARA OS COMANDOS ####
    // ######################################
    switch (command) {
        case 'help':

        command = 'use help';
            break;
        
        case 'start':

        command = 'use start';
            break;

        case 'check itens':

        document.querySelector('.items').style.display = 'block';
            return;

        default:
            document.querySelector('.items').style.display = 'none';
            break;
    }

    try {
            const response = await fetch('http://localhost:5150/execute-command', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
            body: JSON.stringify({ command: command })
        });

        if (!response.ok) {
            throw new Error('Erro na resposta da API');
        }

        const data = await response.json();

        if (data.error) {
            console.error('Erro:', data.error);
            return;
        }

        const scene = data.target;

        fetchData(scene);

    } catch (error) {
        console.error('Erro ao executar comando:', error);
    }
}

