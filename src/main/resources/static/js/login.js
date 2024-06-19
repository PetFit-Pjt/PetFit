const addPetButton = document.getElementById('add-pet');
const petContainer = document.querySelector('.pet-container');

addPetButton.addEventListener('click', () => {
  const petDiv = document.createElement('div');
  petDiv.classList.add('form-group');

  const petTypeLabel = document.createElement('label');
  petTypeLabel.textContent = '반려동물 종류';
  const petTypeInput = document.createElement('input');
  petTypeInput.type = 'text';
  petTypeInput.name = 'pet-type';
  petTypeInput.required = true;

  const petNameLabel = document.createElement('label');
  petNameLabel.textContent = '반려동물 이름';
  const petNameInput = document.createElement('input');
  petNameInput.type = 'text';
  petNameInput.name = 'pet-name';
  petNameInput.required = true;

  const petGenderLabel = document.createElement('label');
  petGenderLabel.textContent = '반려동물 성별';
  const petGenderInput = document.createElement('input');
  petGenderInput.type = 'text';
  petGenderInput.name = 'pet-gender';
  petGenderInput.required = true;

  const petDOBLabel = document.createElement('label');
  petDOBLabel.textContent = '반려동물 생년월일';
  const petDOBInput = document.createElement('input');
  petDOBInput.type = 'text';
  petDOBInput.name = 'pet-dob';
  petDOBInput.placeholder = 'YYYYMMDD';
  petDOBInput.required = true;

  const petSpeciesLabel = document.createElement('label');
  petSpeciesLabel.textContent = '반려동물 종';
  const petSpeciesInput = document.createElement('input');
  petSpeciesInput.type = 'text';
  petSpeciesInput.name = 'pet-species';
  petSpeciesInput.required = true;

  const petVaccinationLabel = document.createElement('label');
  petVaccinationLabel.textContent = '반려동물 예방접종 상태';
  const petVaccinationInput = document.createElement('input');
  petVaccinationInput.type = 'text';
  petVaccinationInput.name = 'pet-vaccination';
  petVaccinationInput.required = true;

  const petSurgeryLabel = document.createElement('label');
  petSurgeryLabel.textContent = '반려동물 수술 이력';
  const petSurgeryInput = document.createElement('input');
  petSurgeryInput.type = 'text';
  petSurgeryInput.name = 'pet-surgery';
  petSurgeryInput.required = true;

  petDiv.appendChild(petTypeLabel);
  petDiv.appendChild(petTypeInput);
  petDiv.appendChild(petNameLabel);
  petDiv.appendChild(petNameInput);
  petDiv.appendChild(petGenderLabel);
  petDiv.appendChild(petGenderInput);
  petDiv.appendChild(petDOBLabel);
  petDiv.appendChild(petDOBInput);
  petDiv.appendChild(petSpeciesLabel);
  petDiv.appendChild(petSpeciesInput);
  petDiv.appendChild(petVaccinationLabel);
  petDiv.appendChild(petVaccinationInput);
  petDiv.appendChild(petSurgeryLabel);
  petDiv.appendChild(petSurgeryInput);

  petContainer.appendChild(petDiv);
});
