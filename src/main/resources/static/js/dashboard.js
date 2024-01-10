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