//아이디 중복체크
let regForm = document.querySelector('.regForm');
let userIdInput = document.getElementById('userIdInput');
let idCheckMessage = document.querySelector('.idCheckMessage');

userIdInput.addEventListener('input', async () => {
	let userId = userIdInput.value;

	//정규표현식으로 아이디 확인
	const isValidId = /^[a-zA-Z0-9]{6,12}$/.test(userId);

	if (!isValidId) {
		idCheckMessage.style.color = 'red';
		idCheckMessage.innerHTML = '영문, 숫자로 이루어진 6~12자를 입력하세요.';
	} else {

		// 서버로 아이디 중복 체크 요청
		const response = await fetch('/register/checkExistId', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify({
				id: userId,
			}),
		});
		if (response.status === 409) {
			// 아이디가 이미 존재할 경우
			idCheckMessage.style.color = 'red';
			idCheckMessage.innerHTML = 'ID가 중복됩니다.';
		} else if (response.ok) {
			// 아이디가 존재하지 않을 경우
			idCheckMessage.style.color = 'green';
			idCheckMessage.innerHTML = '사용 가능한 ID입니다.';
		} else {
			// 서버 요청 실패
			console.error('아이디 중복 체크 실패');
			idCheckMessage.innerHTML = '';
		}
	}
});

/**패스워드 일치 여부 메시지 출력*/
let password = document.querySelector('.password');
let confirmPassword = document.querySelector('.confirmPassword');
let confirmMsg = document.querySelector('.confirmMsg');

password.addEventListener('input', () => {
	// 비밀번호 입력 여부 체크
	if (password.value !== '') {
		if (password.value === confirmPassword.value) {
			confirmMsg.style.color = 'rgb(47, 192, 78)';
			confirmMsg.innerHTML = '비밀번호가 일치합니다.';
		} else {
			confirmMsg.style.color = '#ff0000';
			confirmMsg.innerHTML = '비밀번호가 불일치합니다.';
		}
	} else {
		// 비밀번호가 비어있을 때 초기화
		confirmMsg.innerHTML = '';
	}
});

confirmPassword.addEventListener('input', () => {
	// 비밀번호와 비밀번호 확인이 일치하는지 확인
	if (password.value !== '') {
		if (password.value === confirmPassword.value) {
			confirmMsg.style.color = 'rgb(47, 192, 78)';
			confirmMsg.innerHTML = '비밀번호가 일치합니다.';
		} else {
			confirmMsg.style.color = '#ff0000';
			confirmMsg.innerHTML = '비밀번호가 불일치합니다.';
		}
	} else {
		// 비밀번호가 비어있을 때 초기화
		confirmMsg.innerHTML = '';
	}
});

function openPopup(event) {
	// 팝업을 열 때 사용할 URL (컨트롤러로)
	const popupURL = "/login/find";
	// 팝업 창의 속성 설정
	const popupOptions = "width=600,height=400,location=no,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,left=600,top=200";
	// 팝업 창 열기
	window.open(popupURL, "FindIdPopup", popupOptions);

	event.preventDefault();
}