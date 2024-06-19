document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');

    form.addEventListener('submit', function(event) {
        event.preventDefault();

        // Perform form validation
        if (validateForm()) {
            form.submit();
        }
    });

    function validateForm() {
        let isValid = true;

        // Validate form fields
        const userName = document.getElementById('userName'); 
        const hospitalName = document.getElementById('hospitalName'); 
        const petName = document.getElementById('petName'); 
        const appointmentDateTime = document.getElementById('appointmentDateTime');

        if (userName.value.trim() === '') { 
            isValid = false;
            userName.classList.add('invalid');
        } else {
            userName.classList.remove('invalid');
        }

        if (hospitalName.value.trim() === '') { 
            isValid = false;
            hospitalName.classList.add('invalid');
        } else {
            hospitalName.classList.remove('invalid');
        }

        if (petName.value.trim() === '') { 
            isValid = false;
            petName.classList.add('invalid');
        } else {
            petName.classList.remove('invalid');
        }

        if (appointmentDateTime.value.trim() === '') {
            isValid = false;
            appointmentDateTime.classList.add('invalid');
        } else {
            appointmentDateTime.classList.remove('invalid');
        }

        return isValid;
    }
});
