// reservation_list.js
const dateRangeInput = $('#date-range');
const searchBtn = $('#search-btn');
const reservationTable = $('#reservation-table');
const prevPageBtn = $('#prev-page');
const nextPageBtn = $('#next-page');
const currentPageEl = $('#current-page');

let currentPage = 1;
const itemsPerPage = 10;
let reservations = [];

// 확정 버튼 클릭 이벤트 핸들러
$(document).on('click', '.confirm-btn', function() {
  const rowIndex = $(this).closest('tr').index();
  const confirmedReservation = reservations[rowIndex];
  // 여기에 확정 처리하는 로직을 추가하세요.
});

// 취소 버튼 클릭 이벤트 핸들러
$(document).on('click', '.cancel-btn', function() {
  const rowIndex = $(this).closest('tr').index();
  const canceledReservation = reservations[rowIndex];
  // 여기에 취소 처리하는 로직을 추가하세요.
});

// 데이터 조회 및 테이블 업데이트 함수
function updateReservationTable() {
  reservationTable.find('tbody').html('');

  const dateRange = dateRangeInput.val();
  const filteredReservations = reservations.filter(reservation => {
    // 여기에 필터링 로직 추가
    // 예를 들어, 날짜 범위에 맞는 예약만 필터링할 수 있습니다.
    return reservation.date === dateRange;
  });

  const startIndex = (currentPage - 1) * itemsPerPage;
  const endIndex = startIndex + itemsPerPage;
  const displayedReservations = filteredReservations.slice(startIndex, endIndex);

  displayedReservations.forEach(reservation => {
    const row = $('<tr>').html(`
      <td>${reservation.date}</td>
      <td>${reservation.phone}</td>
      <td>${reservation.service}</td>
      <td>${reservation.patientName}</td>
      <td>${reservation.petType}</td>
      <td>${reservation.doctor}</td>
      <td><button class="confirm-btn">확정</button></td>
      <td><button class="cancel-btn">취소</button></td>
    `);
    reservationTable.find('tbody').append(row);
  });

  currentPageEl.text(currentPage);
}

// 이전/다음 페이지 버튼 클릭 이벤트 핸들러
prevPageBtn.on('click', () => {
  if (currentPage > 1) {
    currentPage--;
    updateReservationTable();
  }
});

nextPageBtn.on('click', () => {
  if (currentPage * itemsPerPage < reservations.length) {
    currentPage++;
    updateReservationTable();
  }
});

// 조회 버튼 클릭 이벤트 핸들러
searchBtn.on('click', () => {
  currentPage = 1;
  updateReservationTable();
});

// 날짜 범위 선택 기능 추가
dateRangeInput.datetimepicker({
  timepicker: false,
  format: 'Y-m-d',
  scrollInput: false,
  scrollMonth: false,
  scrollTime: false,
  maxDate: new Date(), // 현재 날짜로 설정
  onChangeDateTime: (dp, $input) => {
    $input.trigger('input');
  }
});

// // 초기 데이터 로드
// fetch('/api/reservations')
//   .then(response => response.json())
//   .then(data => {
//     reservations = data;
//     updateReservationTable();
//   });

