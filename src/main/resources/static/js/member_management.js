document.addEventListener('DOMContentLoaded', function() {
  const memberList = document.getElementById('member-list');
  const searchInput = document.getElementById('search-input');
  const sortSelect = document.getElementById('sort-select');
  const editModal = document.getElementById('edit-modal');
  const closeButton = document.querySelector('.close-button');
  const editForm = document.getElementById('edit-form');

  let members = [
    {
      id: 1,
      username: 'user1',
      name: '홍길동',
      phone: '010-1234-5678',
      email: 'hong@example.com',
      address: '서울시 종로구 돈화문로'
    },
    {
      id: 2,
      username: 'user2',
      name: '김철수',
      phone: '010-9876-5432',
      email: 'kim@example.com',
      address: '경기도 수원시 장안구'
    },
    {
      id: 3,
      username: 'user3',
      name: '곽두수',
      phone: '010-1234-5432',
      email: 'kisdm@example.com',
      address: '부산시 광안리 물방구'
    },
    {
      id: 4,
      username: 'user4',
      name: '정철팔',
      phone: '010-6666-5432',
      email: 'kim123@example.com',
      address: '경기도 수원시 장안구'
    },
    {
      id: 5,
      username: 'user5',
      name: '남궁민수',
      phone: '010-1234-4322',
      email: 'ewwesk@example.com',
      address: '경기도 용인시 기흥구'
    },
    // 더 많은 회원 정보 추가 가능
  ];

  function renderMemberTable() {
    memberList.innerHTML = '';

    const filteredMembers = filterAndSortMembers();

    filteredMembers.forEach(member => {
      const row = document.createElement('tr');
      row.innerHTML = `
        <td>${member.id}</td>
        <td>${member.username}</td>
        <td>${member.name}</td>
        <td>${member.phone}</td>
        <td>${member.email}</td>
        <td>${member.address}</td>
        <td><button class="edit-button" data-member-id="${member.id}">수정</button></td>
      `;
      memberList.appendChild(row);

      const editButton = row.querySelector('.edit-button');
      editButton.addEventListener('click', () => openEditModal(member));
    });
  }

  function filterAndSortMembers() {
    let filteredMembers = [...members];

    // 검색어로 회원 정보 필터링
    const searchTerm = searchInput.value.toLowerCase();
    filteredMembers = filteredMembers.filter(member =>
      member.username.toLowerCase().includes(searchTerm) ||
      member.name.toLowerCase().includes(searchTerm) ||
      member.phone.includes(searchTerm) ||
      member.email.toLowerCase().includes(searchTerm) ||
      member.address.toLowerCase().includes(searchTerm)
    );

    // 선택한 정렬 기준으로 회원 정보 정렬
    const sortBy = sortSelect.value;
    if (sortBy) {
      filteredMembers.sort((a, b) => {
        if (a[sortBy] < b[sortBy]) return -1;
        if (a[sortBy] > b[sortBy]) return 1;
        return 0;
      });
    }

    return filteredMembers;
  }

  function openEditModal(member) {
    editModal.style.display = 'block';
    document.getElementById('edit-username').value = member.username;
    document.getElementById('edit-name').value = member.name;
    document.getElementById('edit-phone').value = member.phone;
    document.getElementById('edit-email').value = member.email;
    document.getElementById('edit-address').value = member.address;
  }

  closeButton.addEventListener('click', () => {
    editModal.style.display = 'none';
  });

  window.addEventListener('click', (event) => {
    if (event.target == editModal) {
      editModal.style.display = 'none';
    }
  });

  editForm.addEventListener('submit', (event) => {
    event.preventDefault();
    const formData = new FormData(event.target);
    const updatedMember = {
      id: parseInt(event.target.dataset.memberId),
      username: formData.get('username'),
      name: formData.get('name'),
      phone: formData.get('phone'),
      email: formData.get('email'),
      address: formData.get('address')
    };

    // 회원 정보 업데이트
    const index = members.findIndex(member => member.id === updatedMember.id);
    members[index] = updatedMember;

    editModal.style.display = 'none';
    renderMemberTable();
  });

  searchInput.addEventListener('input', renderMemberTable);
  sortSelect.addEventListener('change', renderMemberTable);

  renderMemberTable();
});