// petdoc.js
const searchInput = document.getElementById('search-input');
const searchBtn = document.getElementById('search-btn');
const postContainer = document.querySelector('.post-container');
const writeBtn = document.getElementById('write-btn');
const postModal = document.getElementById('post-modal');
const writeModal = document.getElementById('write-modal');
const closeBtn = document.querySelectorAll('.close-btn');
const postTitle = document.getElementById('post-title');
const postContent = document.getElementById('post-content');
const postTitleInput = document.getElementById('post-title-input');
const postContentInput = document.getElementById('post-content-input');
const submitBtn = document.getElementById('submit-btn');

// 임의의 게시글 생성 함수
function createPost(title, content) {
  const post = document.createElement('div');
  post.classList.add('post');
  post.textContent = title;
  post.addEventListener('click', () => {
    postTitle.textContent = title;
    postContent.textContent = content;
    postModal.style.display = 'block';
  });
  postContainer.appendChild(post);
}

// 초기 게시글 생성
createPost('반려동물 건강 상담', '반려동물 건강과 관련된 상담을 진행합니다.');
createPost('아이가 아파요', '아이가 아프다면 신속한 병원 방문을 권장드립니다.');
createPost('자유 게시판', '자유롭게 의견을 공유할 수 있는 게시판입니다.');

// 검색 기능
searchBtn.addEventListener('click', () => {
  // 검색 기능 구현
  console.log(`검색어: ${searchInput.value}`);
});

// 글쓰기 기능
writeBtn.addEventListener('click', () => {
  writeModal.style.display = 'block';
});

// 모달 닫기 기능
closeBtn.forEach((btn) => {
  btn.addEventListener('click', () => {
    postModal.style.display = 'none';
    writeModal.style.display = 'none';
  });
});

// 모달 외부 클릭 시 닫기
window.addEventListener('click', (event) => {
  if (event.target == postModal || event.target == writeModal) {
    postModal.style.display = 'none';
    writeModal.style.display = 'none';
  }
});

// 새 게시글 작성 기능
submitBtn.addEventListener('click', () => {
  const title = postTitleInput.value;
  const content = postContentInput.value;
  if (title && content) {
    createPost(title, content);
    postTitleInput.value = '';
    postContentInput.value = '';
    writeModal.style.display = 'none';
  }
});