const addPetButton = document.getElementById('add-pet');
const petContainer = document.querySelector('.pet-container');

addPetButton.addEventListener('click', () => {
  const petDiv = document.createElement('div');
  petDiv.classList.add('form-group');

  const fields = [
    { label: '반려동물 종류', name: 'pet-type' },
    { label: '반려동물 이름', name: 'pet-name' },
    { label: '반려동물 성별', name: 'pet-gender' },
    { label: '반려동물 생년월일', name: 'pet-dob', placeholder: 'YYYYMMDD' },
    { label: '반려동물 종', name: 'pet-species' },
    { label: '반려동물 예방접종 상태', name: 'pet-vaccination' },
    { label: '반려동물 수술 이력', name: 'pet-surgery' }
  ];

  fields.forEach(field => {
    const fieldLabel = document.createElement('label');
    fieldLabel.textContent = field.label;

    const fieldInput = document.createElement('input');
    fieldInput.type = 'text';
    fieldInput.name = field.name;
    fieldInput.required = true;
    if (field.placeholder) {
      fieldInput.placeholder = field.placeholder;
    }

    petDiv.appendChild(fieldLabel);
    petDiv.appendChild(fieldInput);
  });

  petContainer.appendChild(petDiv);
});

// 유효성 검사 및 조건 확인 함수
function validateForm() {
  const requiredFields = ['username', 'password', 'confirm-password', 'name', 'dob', 'email', 'address', 'phone'];
  let isValid = true;

  requiredFields.forEach(fieldName => {
    const fieldValue = document.getElementById(fieldName).value.trim();
    if (fieldValue === '') {
      alert(`${fieldName === 'dob' ? '생년월일' : fieldName}을(를) 입력해주세요.`);
      isValid = false;
    }
  });

 // 비밀번호 확인
 const password = document.getElementById('password').value;
 const confirmPassword = document.getElementById('confirm-password').value;
 if (password !== confirmPassword) {
   alert('비밀번호와 비밀번호 확인이 일치하지 않습니다.');
   isValid = false;
 }

  // 생년월일 형식 확인
  const dob = document.getElementById('dob').value;
  if (!isValidDate(dob)) {
    alert('올바른 생년월일 형식이 아닙니다. (YYYYMMDD)');
    isValid = false;
  }

  return isValid;
}

// 생년월일 형식 확인 함수
function isValidDate(dateString) {
  const regex = /^\d{8}$/; // YYYYMMDD 형식
  return regex.test(dateString);
}

// 회원가입 폼 submit 이벤트 처리
const registerForm = document.getElementById('register-form');
registerForm.addEventListener('submit', (event) => {
  if (!validateForm()) {
    event.preventDefault(); // 폼 제출 방지
  }
});
