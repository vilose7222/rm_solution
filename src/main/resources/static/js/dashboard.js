//대시보드로 이동을 하려고 할 때 (checkSubscriptionBtn)구독 여부 확인
document.querySelector('#checkSubscriptionBtn').addEventListener('click', async function(event){
	try{
		const response = await fetch('/subscription/check', {
			method: 'GET',
			headers: {
				'Content-Type': 'application/json',
			},
		});
		
		if(response.ok){
			// HTTP 응답 성공했을 경우
			let data = await response.json();
			
			if(data.dashboardInfo){
				//대시보드 정보 있을때 (구독여부가 true)
				alert('구독여부 있으니 링크 넘어감'); //대시보드로 이동
				window.location.href = "/dashboard";
			} else {
				//대시보드 정보 없을때 (구독여부가 false)
				alert('구독을 하시지 않았군요! 구독 페이지로 이동합니다')
				window.location.href = "/subscription"
			}
		} else {
			// HTTP 응답 실패했을 경우
			alert('HTTP응답 실패',response.status); //이때의 처리는 뭘 해야하지?
		}
	}catch (error) {
		// 여러가지 예외의 발생 시
		alert('여러가지 예외 발생, catch에 걸림',error);
	}
});

 

/**구독 기간 연장 신청 */
//빈 p태그(버튼클릭 시 메시지 출력)
let comentTag = document.querySelector('.durationDays');
//연장 신청 버튼
let extensionBtn = document.querySelector('#extensionBtn');
//input type hidden으로 된 구독id와 회원 id 
let subscriptionId = document.querySelector('#subsId').value;
let userId = document.querySelector('#userId').value;

document.addEventListener('DOMContentLoaded', () => {
    // 2024.01.13 extensionDays를 클로저로 선언 (기존에는 이벤트리스너 바깥에 존재)
    let extensionDays = 0;

    document.querySelectorAll('.duration-btns button').forEach(function (button) {
        button.addEventListener('click', function () {
            let isSelected = button.classList.contains('selected');

            document.querySelectorAll('.duration-btns button').forEach(function (btn) {
                btn.classList.remove('selected');
            });

            if (isSelected) {
                extensionDays = 0;
                comentTag.innerHTML='';
            } else {
                button.classList.add('selected');
                extensionDays = parseInt(button.getAttribute('data-days'));
                let addComent = `선택하신 기간은 ${extensionDays}일 입니다.`;
                comentTag.innerHTML = addComent;
            }
        });
    });
    // durationBtn에 클릭 이벤트 리스너 추가
    document.querySelector('#durationBtn').addEventListener('click', function () {
        submitExtensionRequest(subscriptionId, extensionDays);
    });
});
	

//연장 요청 버튼 클릭 시 요청 함수
async function submitExtensionRequest(subscriptionId, extensionDays) {
    try {
        const response = await fetch('/subscription/extension', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                subscriptionId: subscriptionId,
                extensionDays: extensionDays,
                userId: userId,
            }),
        });

        if (response.ok) {
            // 연장 성공 시
            alert('구독이 성공적으로 연장되었습니다.');
            window.location.href = '/dashboard';  // 대시보드로 리다이렉트
        } else {
            // 연장 실패 시
            alert('구독 연장에 실패했습니다.');
        }
    } catch (error) {
        // 예외 발생 시
        console.error('구독 연장 요청 중 에러 발생:', error);
        alert('구독 연장 요청 중 에러가 발생했습니다.');
    }
}