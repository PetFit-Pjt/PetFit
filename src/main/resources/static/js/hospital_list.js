function approveHospital(button) {
    var hospitalId = button.getAttribute('data-hospital-id'); // 버튼의 data-hospital-id 속성에서 hospitalId를 가져옴
    fetch('/hospital_list/' + hospitalId, {method: 'POST'})
        .then(response => {
            if (response.ok) {
                button.closest('tr').cells[6].textContent = '승인됨';
                button.remove(); // 승인 버튼 제거
                alert('병원 #' + hospitalId + '이(가) 승인되었습니다.');
            } else {
                throw new Error('HTTP status ' + response.status);
            }
        })
        .catch(error => {
            alert('병원 승인 중 오류가 발생했습니다: ' + error.message);
        });
}
