	function showWriteModal() {
	    document.getElementById("writeModal").style.display = "block";
	}
	
	function hideWriteModal() {
	    document.getElementById("writeModal").style.display = "none";
	}
	
	window.onclick = function(event) {
	    if (event.target == document.getElementById("writeModal")) {
	        hideWriteModal();
	    }
	}

document.addEventListener('DOMContentLoaded', function() {
    const editBtns = document.querySelectorAll('.edit-btn');
    const deleteBtns = document.querySelectorAll('.delete-btn');
    const replyBtns = document.querySelectorAll('.reply-btn');

    editBtns.forEach(function(btn) {
        btn.addEventListener('click', function(event) {
            event.preventDefault();
            const petdocId = this.getAttribute('data-petdoc-id');
            showEditModal(petdocId);
        });
    });

    deleteBtns.forEach(function(btn) {
        btn.addEventListener('click', function(event) {
            event.preventDefault();
            const petdocId = this.getAttribute('data-petdoc-id');
            deletePetdoc(petdocId);
        });
    });
    
    replyBtns.forEach(function(btn) { // 각 답글 버튼에 대한 클릭 이벤트 처리
	    btn.addEventListener('click', function(event) {
	        event.preventDefault();
	        const petdocId = this.getAttribute('data-petdoc-id');
	        showReplyModal(petdocId); // 답글 모달 표시
        });
    });
});

// 수정 모달 열기 및 게시글 정보 불러오기
function showEditModal(postId, title, details, userName) {
    var editForm = document.getElementById('editForm');
    var titleInput = editForm.querySelector('input[name="title"]');
    var detailsInput = editForm.querySelector('textarea[name="details"]');
    var userNameInput = editForm.querySelector('input[name="userName"]');

    titleInput.value = title;
    detailsInput.value = details;
    userNameInput.value = userName;

    // 수정할 게시글의 ID를 form의 action에 추가하여 서버에 전송
    editForm.action = '/petdoc/edit/' + postId;

    // 수정 모달을 보이도록 설정
    document.getElementById('editModal').style.display = 'block';
}

// 수정 모달 닫기
function hideEditModal() {
    document.getElementById('editModal').style.display = 'none';
}

// 답글 모달 열기
function showReplyModal(postId) {
    var replyForm = document.getElementById('replyForm');
    var parentPostIdInput = replyForm.querySelector('input[name="parentPostId"]');
    parentPostIdInput.value = postId; // 부모 게시글의 ID를 hidden 필드에 설정
    document.getElementById('replyModal').style.display = 'block'; // 답글 모달 표시
}

// 답글 모달 닫기
function hideReplyModal() {
    document.getElementById('replyModal').style.display = 'none';
}

const replyForm = document.getElementById('replyForm');
replyForm.addEventListener('submit', function(event) {
    event.preventDefault(); // 기본 폼 제출 동작 막기

    const formData = new FormData(event.target);
    const replyData = {
        parentPostId: formData.get('parentPostId'),
        content: formData.get('content'),
        userName: formData.get('userName'),
    };

        fetch('/replies', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(replyData)
        })
        .then(response => {
            if (response.ok) {
                alert('답글이 작성되었습니다.');
                hideReplyModal();
                window.location.reload(); // 페이지 새로고침
            } else {
                alert('답글 작성에 실패했습니다.');
            }
        })
        .catch(error => console.error(error));
    });

// 게시글 삭제
function deletePetdoc(petdocId) {
    if (confirm('정말로 삭제하시겠습니까?')) {
        fetch('/petdoc/delete/' + petdocId, {
            method: 'GET'
        })
        .then(() => {
            window.location.reload();
        })
        .catch(error => console.error(error));
    }
}