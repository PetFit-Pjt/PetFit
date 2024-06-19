// 임의의 회원 데이터
const user = {
  username: 'kimjihyun',
  password: 'password123',
  name: '김지현',
  dob: '19950505',
  email: 'kimjihyun@example.com',
  address: '서울특별시 강남구 테헤란로 123',
  phone: '01012345678',
  phoneProvider: 'kt'
};
// DOM 요소 선택
const registerForm = document.getElementById('register-form');
const usernameInput = document.getElementById('username');
const passwordInput = document.getElementById('password');
const confirmPasswordInput = document.getElementById('confirm-password');
const nameInput = document.getElementById('name');
const dobInput = document.getElementById('dob');
const emailInput = document.getElementById('email');
const addressInput = document.getElementById('address');
const phoneInput = document.getElementById('phone');
const phoneProviderInput = document.getElementById('phone-provider');

// 초기 데이터 설정
usernameInput.value = user.username;
passwordInput.value = user.password;
confirmPasswordInput.value = user.password;
nameInput.value = user.name;
dobInput.value = user.dob;
emailInput.value = user.email;
addressInput.value = user.address;
phoneInput.value = user.phone;
phoneProviderInput.value = user.phoneProvider;

// 폼 제출 처리
registerForm.addEventListener('submit', (event) => {
  event.preventDefault();

  // 입력 데이터 수집
  const formData = {
    username: usernameInput.value,
    password: passwordInput.value,
    name: nameInput.value,
    dob: dobInput.value,
    email: emailInput.value,
    address: addressInput.value,
    phone: phoneInput.value,
    phoneProvider: phoneProviderInput.value
  };

  // 서버로 데이터 전송
  sendDataToServer(formData)
    .then(() => {
      alert('회원 정보가 successfully 수정되었습니다.');
      window.location.href = 'userMyPage.html';
    })
    .catch((error) => {
      alert('회원 정보 수정에 실패했습니다: ' + error.message);
    });
});

// 서버로 데이터 전송 (예시)
function sendDataToServer(formData) {
  return new Promise((resolve, reject) => {
    // 실제 서버 API 호출 코드 작성
    console.log('Sending data to server:', formData);
    resolve(); // 성공적으로 전송된 것으로 가정
  });
}