document.addEventListener('DOMContentLoaded', function() {
  const calendarContainer = document.getElementById('calendar-container');
  const selectedDateElement = document.getElementById('selected-date');
  const selectedTimeElement = document.getElementById('selected-time');
  const timeSlotElements = document.querySelectorAll('#time-slots .time-slot');
  const appointmentForm = document.getElementById('appointment-form');
  const appointmentList = document.getElementById('appointment-list');

  let selectedDate = null;
  let selectedTime = null;
  const appointments = []; // 예약 정보를 저장할 배열

  // 캘린더 초기화
  function renderCalendar(date = new Date()) {
    const currentMonth = date.getMonth();
    const currentYear = date.getFullYear();
    const firstDay = new Date(currentYear, currentMonth, 1).getDay();
    const daysInMonth = new Date(currentYear, currentMonth + 1, 0).getDate();

    renderCalendarHeader(currentYear, currentMonth);
    renderCalendarBody(currentYear, currentMonth, firstDay, daysInMonth);
    addCalendarEventListeners();
  }

  // 캘린더 헤더 렌더링
  function renderCalendarHeader(year, month) {
    const calendarHeader = `
      <div class="calendar-header">
        <button class="prev-month">Prev</button>
        <div>${year} - ${("0" + (month + 1)).slice(-2)}</div>
        <button class="next-month">Next</button>
      </div>`;
    calendarContainer.innerHTML = calendarHeader;
  }

  // 캘린더 본문 렌더링
  function renderCalendarBody(year, month, firstDay, daysInMonth) {
    let calendarHTML = '<div class="calendar-body">';

    // Add empty cells for the days before the first of the month
    for (let i = 0; i < firstDay; i++) {
      calendarHTML += '<div class="calendar-day"></div>';
    }

    // Add the days of the month
    for (let i = 1; i <= daysInMonth; i++) {
      const dayClass = selectedDate && selectedDate.getDate() === i && selectedDate.getMonth() === month && selectedDate.getFullYear() === year
        ? 'calendar-day selected'
        : 'calendar-day';
      calendarHTML += `<div class="${dayClass}" data-date="${year}-${month + 1}-${i}">${i}</div>`;
    }

    calendarHTML += '</div>';
    calendarContainer.innerHTML += calendarHTML;
  }

  // 캘린더 이벤트 리스너 추가
  function addCalendarEventListeners() {
    const calendarDays = document.querySelectorAll('.calendar-day');
    calendarDays.forEach(day => {
      day.addEventListener('click', () => {
        const date = new Date(day.dataset.date);
        selectDate(date);
      });
    });

    const prevMonthButton = document.querySelector('.prev-month');
    const nextMonthButton = document.querySelector('.next-month');
    prevMonthButton.addEventListener('click', () => {
      const currentMonth = selectedDate ? selectedDate.getMonth() : new Date().getMonth();
      const currentYear = selectedDate ? selectedDate.getFullYear() : new Date().getFullYear();
      const prevMonthDate = new Date(currentYear, currentMonth - 1, 1);
      renderCalendar(prevMonthDate);
    });
    nextMonthButton.addEventListener('click', () => {
      const currentMonth = selectedDate ? selectedDate.getMonth() : new Date().getMonth();
      const currentYear = selectedDate ? selectedDate.getFullYear() : new Date().getFullYear();
      const nextMonthDate = new Date(currentYear, currentMonth + 1, 1);
      renderCalendar(nextMonthDate);
    });
  }

  // 날짜 선택
  function selectDate(date) {
    const previousSelectedDayElement = document.querySelector('.calendar-day.selected');
    if (previousSelectedDayElement) {
      previousSelectedDayElement.classList.remove('selected');
      previousSelectedDayElement.style.backgroundColor = '#f8f8f8';
      previousSelectedDayElement.style.color = '#000';
    }
  
	  selectedDate = date;
	  selectedDateElement.textContent = moment(date).format('YYYY-MM-DD');
	  updateAvailableTimeslots();
  
	  const selectedDayElement = document.querySelector(`.calendar-day[data-date="${selectedDate.getFullYear()}-${selectedDate.getMonth() + 1}-${selectedDate.getDate()}"]`);
	  selectedDayElement.classList.add('selected');
	  selectedDayElement.style.backgroundColor = '#4CAF50';
	  selectedDayElement.style.color = '#fff';
	  
    // 선택한 날짜를 appointmentDateTime 필드에 설정
  	const appointmentDateTimeField = document.getElementById('appointmentDateTime');
  	appointmentDateTimeField.value = moment(date).format('YYYY-MM-DD');
  }

  // 예약 가능한 시간 업데이트
  function updateAvailableTimeslots() {
    if (!selectedDate) return;

    timeSlotElements.forEach(slot => {
      slot.classList.remove('unavailable');
    });

    if (selectedDate.getDay() === 0 || selectedDate.getDay() === 6) {
      const fourteenSlot = document.querySelector('#time-slots .time-slot:nth-child(2)');
      fourteenSlot.classList.add('unavailable');
    }
  }

  // 시간 선택
  timeSlotElements.forEach(slot => {
    slot.addEventListener('click', () => {
      const time = slot.textContent;
      selectTime(time);
      timeSlotElements.forEach(slot => {
        slot.classList.remove('selected');
      });
      slot.classList.add('selected');
    });
  });

  // 선택한 시간 업데이트
  function selectTime(time) {
    selectedTime = time;
    selectedTimeElement.textContent = time;
    
    
  // 선택한 시간을 appointmentDateTime 필드에 추가
  const appointmentDateTimeField = document.getElementById('appointmentDateTime');
  const formattedDateTime = `${appointmentDateTimeField.value} ${time}`;
  appointmentDateTimeField.value = formattedDateTime;
  }


// 날짜와 시간을 선택하면 호출되는 함수
function updateAppointmentDateTime(date, time) {
    const appointmentDateTime = document.getElementById('appointment_date_time');
    const formattedDate = moment(date).format('YYYY-MM-DD');
    const formattedTime = time;
    const formattedDateTime = `${formattedDate} ${formattedTime}`;
    appointmentDateTime.value = formattedDateTime;
}

// 날짜 선택 시 호출
function handleDateSelect(date) {
    const selectedDate = moment(date).format('YYYY-MM-DD');
    // 선택된 날짜에 대한 시간 슬롯 업데이트 로직 (생략)
    
    // 선택된 날짜를 appointment_date_time 필드에 설정
    updateAppointmentDateTime(date, '');
}

// 시간 슬롯 선택 시 호출
function handleTimeSlotSelect(time) {
    const selectedDate = document.getElementById('appointment_date_time').value.split(' ')[0];
    
    // 선택된 날짜와 시간을 appointment_date_time 필드에 설정
    updateAppointmentDateTime(selectedDate, time);
}


  // 예약 내역 표시
  function displayAppointments() {
    appointmentList.innerHTML = ''; // 예약 리스트 초기화

    appointments.forEach(appointment => {
      const appointmentItem = document.createElement('div');
      appointmentItem.classList.add('appointment-item');
      appointmentItem.innerHTML = `
        <p><strong>날짜:</strong> ${moment(appointment.date).format('YYYY-MM-DD')}</p>
        <p><strong>시간:</strong> ${appointment.time}</p>
        <p><strong>서비스:</strong> ${appointment.service}</p>
        <p><strong>이름:</strong> ${appointment.ownerName}</p>
        <p><strong>반려동물 이름:</strong> ${appointment.petName}</p>
        <p><strong>병원 이름:</strong> ${appointment.petType}</p>
      `;
      appointmentList.appendChild(appointmentItem);
    });
  }

  renderCalendar();
});




