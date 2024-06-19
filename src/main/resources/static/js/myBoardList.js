const boardList = document.getElementById('board-list');
const boardItems = boardList.getElementsByClassName('board-item');

// 수정 기능 구현
const editButtons = document.querySelectorAll('.edit-btn');
editButtons.forEach((editBtn, index) => {
  editBtn.addEventListener('click', () => {
    const boardItem = boardItems[index];
    const titleInput = prompt('제목을 입력하세요:', boardItem.querySelector('h2').textContent);
    const contentInput = prompt('내용을 입력하세요:', boardItem.querySelector('p').textContent);

    if (titleInput && contentInput) {
      boardItem.querySelector('h2').textContent = titleInput;
      boardItem.querySelector('p').textContent = contentInput;
    }
  });
});

// 삭제 기능 구현
const deleteButtons = document.querySelectorAll('.delete-btn');
deleteButtons.forEach((deleteBtn, index) => {
  deleteBtn.addEventListener('click', () => {
    const boardItem = boardItems[index];
    if (confirm('정말 삭제하시겠습니까?')) {
      boardList.removeChild(boardItem);
    }
  });
});