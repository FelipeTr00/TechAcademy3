
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


// async function fetchData(scene) {
//     try {
//         const response = await fetch(`http://localhost:5150/${scene}`);
//         if (!response.ok) {
//             throw new Error('Network response was not ok');
//         }
//         const data = await response.json();

//         createList(data.scenes, 'scene-list', true);
//         createList(data.items, 'items-list', false);

//     } catch (error) {
//         console.error('There has been a problem with your fetch operation:', error);
//     }
// }

    // #### SWITCH CASE PARA OS COMANDOS ####

    // async function executeCommand() {
    //     let command = document.getElementById('command-input').value.trim().toLowerCase();
    
    //     switch (command) {
    //         case 'check itens':
    //             document.querySelector('.items').style.display = 'block';
    //             return;
    //         default:
    //             document.querySelector('.items').style.display = 'none';
    //             break;
    //     }
    
    //     try {
    //         const response = await fetch('http://localhost:5150/execute-command', {
    //             method: 'POST',
    //             headers: {
    //                 'Content-Type': 'application/json'
    //             },
    //             body: JSON.stringify({ command: command })
    //         });
    
    //         if (!response.ok) {
    //             throw new Error('Erro na resposta da API');
    //         }
    
    //         const data = await response.json();
    
    //         if (data.error) {
    //             console.error('Erro:', data.error);
    //             return;
    //         }
    
    //         const scene = data.target;
    
    //         fetchData(scene);
    
    //     } catch (error) {
    //         console.error('Erro ao executar comando:', error);
    //     }
    // }

    let currentSceneId = null; // Inicializa o currentSceneId como null para evitar problemas

async function executeCommand() {
    let command = document.getElementById('command-input').value.trim().toLowerCase();
    
    switch (command) {
        case 'check itens':
            // Exibe os itens relacionados na interface
            document.querySelector('.items').style.display = 'block';
            return;  // Finaliza o comando sem continuar a execução
        case 'inventory':
            // Verifica se currentSceneId está corretamente definido
            if (currentSceneId === null) {
                alert('Erro: Cena atual não definida.');
                return;
            }
            // Busca e exibe o inventário da cena atual
            try {
                const response = await fetch(`http://localhost:5150/${currentSceneId}`);
                if (!response.ok) {
                    throw new Error('Erro ao buscar os dados da cena');
                }

                const data = await response.json();

                // Verifica o campo inventory e exibe em um alert se não estiver vazio
                if (data.scenes[0] && data.scenes[0].inventory && data.scenes[0].inventory.trim() !== "") {
                    alert(`Inventário: ${data.scenes[0].inventory}`);
                } else {
                    alert('O inventário está vazio.');
                }

            } catch (error) {
                console.error('Erro ao buscar inventário:', error);
                alert('Erro ao carregar o inventário.');
            }
            return;  // Finaliza a execução após mostrar o inventário
        default:
            // Se não for "check itens" nem "inventory", esconde a lista de itens
            document.querySelector('.items').style.display = 'none';
            break;
    }

    // Caso o comando não seja "check itens" ou "inventory", continua o fluxo normal
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
        currentSceneId = scene; // Atualiza o currentSceneId para a nova cena
        fetchData(scene);  // Atualiza a cena e os itens

    } catch (error) {
        console.error('Erro ao executar comando:', error);
    }
}

async function fetchData(scene) {
    try {
        const response = await fetch(`http://localhost:5150/${scene}`);
        if (!response.ok) {
            throw new Error('Erro 404');
        }
        const data = await response.json();

        // Atualiza as listas de cenas e itens na interface
        createList(data.scenes, 'scene-list', true);
        createList(data.items, 'items-list', false);

        // Atualiza a variável global para armazenar a cena atual
        currentSceneId = data.scenes[0].sceneId; // Certifique-se de que currentSceneId seja atualizado com o ID correto da cena

        // Verifica o campo inventory e exibe em um alert se não estiver vazio
        if (data.scenes[0] && data.scenes[0].inventory && data.scenes[0].inventory.trim() !== "") {
            console.log(`Inventário: ${data.scenes[0].inventory}`);
        } else {
            console.log('Inventário está vazio.');
        }

    } catch (error) {
        console.error('There has been a problem with your fetch operation:', error);
    }
}
