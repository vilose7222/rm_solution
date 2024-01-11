async function findUser() {
    try {
        let findForm = document.querySelector('#findForm');
        let name = document.querySelector('input[name="name"]').value;
        let email = document.querySelector('input[name="email"]').value;
        let encodedName = encodeURIComponent(name);
        let alertText = document.querySelector('.alertText');

        if (!name || !email) {
            alertText.innerHTML = '정보를 입력해 주세요';
        } else {
            let response = await fetch(`/login/result/${encodedName}/${email}`);
            if (response.status === 404) {
                alertText.innerHTML = '해당하는 회원 정보가 없습니다.';
            } else {
                let data = await response.json();
                findForm.innerHTML = `
                    <p>
                        <span id="foundId">
                            회원님의 아이디는 ${data.id}입니다.
                        </span>
                    </p>
                    <input type="button" onclick="closePopup()" value="창 닫기">
                `;
            }
        }
    } catch (error) {
        console.error('데이터 불러오지 못함', error);
    }
}

function closePopup() {
    window.close();
}
