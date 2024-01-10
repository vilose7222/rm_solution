function selectPlan(plan, price) {
  let selectedPlanElement = document.getElementById('selectedPlan');
  let selectedPriceElement = document.getElementById('selectedPrice');
  selectedPlanElement.textContent = plan;
  selectedPriceElement.textContent = price;
}

/** 구독신청 버튼 누를 시 팝업 띄우기 */
document.addEventListener('DOMContentLoaded', function () {
	closePopup();
  document.getElementById('openPopupBtn').addEventListener('click', function () {
    document.getElementById('overlay').style.display = 'block';
    document.getElementById('popup').style.display = 'block';
    
  });

  document.getElementById('closeBtn').addEventListener('click', closePopup);
  document.getElementById('overlay').addEventListener('click', closePopup);
  document.getElementById('cancelBtn').addEventListener('click', closePopup);

  function closePopup() {
    document.getElementById('overlay').style.display = 'none';
    document.getElementById('popup').style.display = 'none';
  }
});

/** ========fetch 사용해서 컨트롤러로 전송======= */
function handleSubmit(event) {
  event.preventDefault();

  // FormData를 사용하여 폼 데이터를 가져옴
  const formData = new FormData(document.getElementById("subsForm"));
	let idd = formData.get("userId");
  // 추가적인 값 매핑
  const solutionSelect = document.getElementById("solutionSelect");
  const selectedValue = solutionSelect.value;
  switch (selectedValue) {
    case "1":
      formData.append("serviceType", "Basic");
      break;
    case "2":
      formData.append("serviceType", "Standard");
      break;
    case "3":
      formData.append("serviceType", "Premium");
      break;
    default:
      break;
  }

  // Fetch API를 사용하여 데이터 전송
  fetch("/subscription", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(Object.fromEntries(formData)),
  })
   .then(response => {
    if (response.ok) {
	   //서버 응답이 정상인 경우
    return response.json();
    } 
     else {
      	//서버에서 에러 응답이 오면 처리
		throw new Error(response.status);
	}
  })
  .then(data => {
        // 정상 서버 응답 처리
        alert('구독 처리 완료 되었습니다!');
        window.location.href = "/";
        // 원하는 작업 수행
    })
    .catch(error => {
      console.error("오류 발생", error);
      console.log(error.message);
      if(error.message === '400'){
		  alert('잘못된 요청입니다. 다시 시도해 주세요.');
	  } else if(error.message === '409'){
		  alert('이미 구독중이시군요! 대시보드에서 구독 연장을 해주세요.');
            window.location.href = "/dashboard";
	  }else{
      	alert('로그인이 되지 않았습니다.');
      window.location.href = "/login";
      }
    });
}

// 폼에 submit 이벤트 리스너 추가
document.getElementById("subsForm").addEventListener("submit", handleSubmit);

/** 다음 주소 api 관련 함수 */
function openDaumAddressPopup() {
  new daum.Postcode({
    oncomplete: function (data) {
      // 선택한 주소 정보를 받아와서 해당 필드에 설정
      document.getElementById('address').value = data.address;
    }
  }).open();
}
