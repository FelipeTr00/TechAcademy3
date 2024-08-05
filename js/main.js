document.addEventListener('DOMContentLoaded', function() {
    const links = document.querySelectorAll('a[data-page]');
    const contentDiv = document.getElementById('content');

    links.forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            const page = this.getAttribute('data-page');

            // Carregar o conteúdo do arquivo correspondente
            fetch(`content/${page}.html`)
                .then(response => response.text())
                .then(html => {
                    contentDiv.innerHTML = html;
                    
                    // Reinitialize Materialize components
                    M.AutoInit();
                })
                .catch(error => console.error('Erro ao carregar o conteúdo:', error));
        });
    });

    links[1].click();
});
