document.addEventListener('DOMContentLoaded', function() {
  const form = document.getElementById('PetRegistrationForm');
  
  form.addEventListener('submit', function(event) {
    event.preventDefault();

    const formData = new FormData(form);

    fetch('/myPetRegister', {
      method: 'POST',
      body: formData
    })
    .then(response => {
      if (response.ok) {
        alert('등록이 성공적으로 완료되었습니다.');
        form.reset(); // 폼 리셋
      } else {
        alert('등록에 실패했습니다.');
      }
    })
    .catch(error => {
      console.error('Error:', error);
      alert('등록에 실패했습니다.');
    });
  });
});