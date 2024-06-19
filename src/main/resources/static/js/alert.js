    // 멤버십 링크 클릭 시 실행되는 함수
    document.getElementById("membershipLink").addEventListener("click", function(event) {
		// 세션이 유효한지 확인하는 함수
		function checkSession() {
		    // 서버에 세션을 확인하는 요청을 보냅니다.
		    fetch('/check-session')
		        .then(response => {
		            if (response.ok) {
		                // 세션이 유효하면 로그인 상태를 true로 설정합니다.
		                isLoggedIn = true;
		            } else {
		                // 세션이 만료되었거나 유효하지 않으면 로그인 상태를 false로 설정합니다.
		                isLoggedIn = false;
		            }
		        })
		        .catch(error => {
		            // 오류가 발생하면 로그인 상태를 false로 설정합니다.
		            console.error('Error checking session:', error);
		            isLoggedIn = false;
		        });
		}
		
		// 페이지 로드 시 세션을 확인합니다.
		checkSession();
        // 만약 로그인되어 있지 않다면
        if (!isLoggedIn) {
            // 얼럿창 띄우기
            alert("로그인이 필요합니다.");
            // 링크 이벤트 취소하여 로그인 페이지로 이동을 막음
            event.preventDefault();
            // 로그인 페이지로 이동
            window.location.href = "/login"; // 로그인 페이지의 URL로 이동
        }
    });