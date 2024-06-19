document.addEventListener('DOMContentLoaded', function() {
    const searchForm = document.querySelector('form[action="/search"]');
    const searchInput = searchForm.querySelector('input[name="query"]');
    const locationSelect = searchForm.querySelector('select[name="location"]');
    const typeSelect = searchForm.querySelector('select[name="type"]');
    const hospitalCards = document.querySelectorAll('.hospital-card');

    searchForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const query = searchInput.value.toLowerCase();
        const location = locationSelect.value.toLowerCase();
        const type = typeSelect.value.toLowerCase();

        hospitalCards.forEach(function(card) {
            const name = card.querySelector('h3').textContent.toLowerCase();
            const address = card.querySelectorAll('p')[2].textContent.toLowerCase();

            const matchQuery = name.includes(query) || address.includes(query);
            const matchLocation = location === '' || address.includes(location);
            const matchType = type === '' || card.classList.contains(type);

            if (matchQuery && matchLocation && matchType) {
                card.style.display = 'flex';
            } else {
                card.style.display = 'none';
            }
        });
    });
});
