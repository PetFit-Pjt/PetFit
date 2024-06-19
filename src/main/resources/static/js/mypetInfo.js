// myPetInfo.js

// 임의의 반려동물 데이터
const petInfo = {
  ownerName: '김지현',
  petName: '마루',
  microchipNumber: '123456789',
  petGender: 'male',
  petAge: 5,
  vaccination: 'yes',
  specialNotes: '알레르기 있음',
  petBreed: '푸들',
  petType: '개'
};

// DOM 요소 선택
const petInfoForm = document.getElementById('pet-info-form');
const ownerNameInput = document.getElementById('owner-name');
const petNameInput = document.getElementById('pet-name');
const microchipNumberInput = document.getElementById('microchip-number');
const petGenderInput = document.getElementById('pet-gender');
const petAgeInput = document.getElementById('pet-age');
const vaccinationInput = document.getElementById('vaccination');
const specialNotesInput = document.getElementById('special-notes');
const petBreedInput = document.getElementById('pet-breed');
const petTypeInput = document.getElementById('pet-type');

// 초기 데이터 설정
ownerNameInput.value = petInfo.ownerName;
petNameInput.value = petInfo.petName;
microchipNumberInput.value = petInfo.microchipNumber;
petGenderInput.value = petInfo.petGender;
petAgeInput.value = petInfo.petAge;
vaccinationInput.value = petInfo.vaccination;
specialNotesInput.value = petInfo.specialNotes;
petBreedInput.value = petInfo.petBreed;
petTypeInput.value = petInfo.petType;

// 폼 제출 처리
petInfoForm.addEventListener('submit', (event) => {
  event.preventDefault();

  // 입력 데이터 수집
  const formData = {
    ownerName: ownerNameInput.value,
    petName: petNameInput.value,
    microchipNumber: microchipNumberInput.value,
    petGender: petGenderInput.value,
    petAge: petAgeInput.value,
    vaccination: vaccinationInput.value,
    specialNotes: specialNotesInput.value,
    petBreed: petBreedInput.value,
    petType: petTypeInput.value
  };

  // 서버로 데이터 전송
  sendDataToServer(formData)
    .then(() => {
      alert('반려동물 정보가 successfully 수정되었습니다.');
      window.location.href = 'userMyPage';
    })
    .catch((error) => {
      alert('반려동물 정보 수정에 실패했습니다: ' + error.message);
    });
});

// 서버로 데이터 전송 (예시)
function sendDataToServer(formData) {
  return new Promise((resolve, reject) => {
    // 실제 서버 API 호출 코드 작성
    console.log('Sending pet data to server:', formData);
    resolve(); // 성공적으로 전송된 것으로 가정
  });
}

const addPetBtn = document.getElementById('add-pet-btn');
const additionalPetInfo = document.getElementById('additional-pet-info');

addPetBtn.addEventListener('click', () => {
  const newPetForm = document.createElement('div');
  newPetForm.classList.add('form-group');
  newPetForm.innerHTML = `
    <label for="owner-name">반려인 성명</label>
    <input type="text" id="owner-name" name="owner-name" required>
    <label for="pet-name">반려동물 이름</label>
    <input type="text" id="pet-name" name="pet-name" required>
    <label for="microchip-number">내장칩 번호</label>
    <input type="text" id="microchip-number" name="microchip-number" required>
    <label for="pet-gender">성별</label>
    <select id="pet-gender" name="pet-gender" required>
      <option value="">선택</option>
      <option value="male">수컷</option>
      <option value="female">암컷</option>
    </select>
    <label for="pet-age">나이</label>
    <input type="number" id="pet-age" name="pet-age" required>
    <label for="vaccination">예방 접종 유/무</label>
    <select id="vaccination" name="vaccination" required>
      <option value="">선택</option>
      <option value="yes">유</option>
      <option value="no">무</option>
    </select>
    <label for="special-notes">특이사항</label>
    <textarea id="special-notes" name="special-notes"></textarea>
    <label for="pet-breed">품종</label>
    <input type="text" id="pet-breed" name="pet-breed" required>
    <label for="pet-type">종류</label>
    <input type="text" id="pet-type" name="pet-type" required>
    <button type="button" id="add-pet-btn">반려동물 추가하기</button>
    <button type="submit"><a href="userMyPage.html">수정하기</a></button>
  `;
  additionalPetInfo.appendChild(newPetForm);
});