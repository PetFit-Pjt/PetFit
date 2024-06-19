// 임의의 예약 내역 데이터
const appointments = [
  {
    service: '진료',
    ownerName: '김지현',
    petName: '마루',
    petType: '개',
    date: '2023-05-01',
    time: '10:00'
  },
  {
    service: '예방 접종',
    ownerName: '이민수',
    petName: '나비',
    petType: '고양이',
    date: '2023-05-02',
    time: '14:00'
  },
  {
    service: '미용',
    ownerName: '박영희',
    petName: '두리',
    petType: '강아지',
    date: '2023-05-03',
    time: '16:30'
  }
];

const appointmentList = document.getElementById('appointment-list');

// 예약 내역 HTML 생성 및 추가
appointments.forEach((appointment) => {
  const appointmentItem = document.createElement('div');
  appointmentItem.classList.add('appointment-item');

  const appointmentInfo = document.createElement('div');
  appointmentInfo.classList.add('appointment-info');
  appointmentInfo.innerHTML = `
    <p>서비스: ${appointment.service}</p>
    <p>사용자: ${appointment.ownerName}</p>
    <p>반려동물: ${appointment.petName} (${appointment.petType})</p>
    <p>예약 날짜: ${appointment.date}</p>
    <p>예약 시간: ${appointment.time}</p>
  `;

  const cancelButton = document.createElement('button');
  cancelButton.classList.add('appointment-cancel');
  cancelButton.textContent = '예약 취소';
  cancelButton.addEventListener('click', () => {
    cancelAppointment(appointment);
  });

  appointmentItem.appendChild(appointmentInfo);
  appointmentItem.appendChild(cancelButton);
  appointmentList.appendChild(appointmentItem);
});

// 예약 취소 함수
function cancelAppointment(appointment) {
  // 실제 서버 API를 통해 예약 취소 처리
  console.log(`${appointment.ownerName}님의 ${appointment.petName} 예약이 취소되었습니다.`);

  // 예약 내역 목록에서 해당 예약 항목 제거
  const appointmentItem = Array.from(appointmentList.children).find((item) => {
    const info = item.querySelector('.appointment-info');
    return (
      info.textContent.includes(appointment.ownerName) &&
      info.textContent.includes(appointment.petName) &&
      info.textContent.includes(appointment.date) &&
      info.textContent.includes(appointment.time)
    );
  });
  appointmentList.removeChild(appointmentItem);
}