// MediFlow JSP Main JS
document.addEventListener('DOMContentLoaded', function() {
    // Auto-dismiss alerts
    document.querySelectorAll('.alert').forEach(function(alert) {
        setTimeout(function() { alert.style.display = 'none'; }, 5000);
    });

    // User dropdown toggle
    var wrapper = document.querySelector('.user-menu-wrapper');
    if (wrapper) {
        var dropdown = wrapper.querySelector('.user-dropdown');
        if (dropdown) dropdown.style.display = 'none';
        wrapper.querySelector('.user-btn').addEventListener('click', function(e) {
            e.stopPropagation();
            dropdown.style.display = dropdown.style.display === 'none' ? 'block' : 'none';
        });
        document.addEventListener('click', function() {
            if (dropdown) dropdown.style.display = 'none';
        });
    }
});
