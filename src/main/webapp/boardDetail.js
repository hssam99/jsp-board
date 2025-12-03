console.log("boardDetail.js loaded");
console.log(bnoValue);

document.getElementById('cmtAddBtn').addEventListener('click', ()=>{
// 요소 자체 저장
    const cmtCommenterEl = document.getElementById('cmtCommenter');
    const cmtContentEl = document.getElementById('cmtContent');

// 값 가져오기
    const cmtCommenter = cmtCommenterEl.value;
    const cmtContent = cmtContentEl.value;

    if(cmtCommenter.trim() === '' || cmtContent.trim() === ''){
        alert('댓글 작성자와 내용을 모두 입력해주세요.');
        return;
    }

    // 댓글 객체 생성
    const cmtData = {
        bno: bnoValue,
        commenter: cmtCommenter,
        content: cmtContent
    };
    console.log("✅댓글 객체 생성 완료");
    console.log(cmtData);

    cmtContentEl.value=""; // 입력 필드 초기화
    cmtContentEl.focus();

    postCommentToServer(cmtData).then(result => {
        console.log(result)
        if(result==="1"){
            alert('댓글이 성공적으로 등록되었습니다.');
        }else{
            alert('댓글 등록에 실패했습니다. 다시 시도해주세요.');
        }
        // 댓글 리스트 띄우기
        printCommentList(cmtData.bno);
    });

})

// cmtData를 비동기로 컨트롤러로 보내기 (POST 방식)
async function postCommentToServer(cmtData){
    try{
        // 보낼때 -> url, headers(contentType), body(cmtData)
        const url = '/comments/post';
        const config = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json; charset=UTF-8'
            },
            body: JSON.stringify(cmtData)
        }

        const resp = await fetch(url, config);
        const result = await resp.text(); // isOk
        return result;

    } catch (error){
        console.error('댓글 등록 중 오류 발생:', error);
    }
}


// 비동기 리스트 출력
async function getCommentListFromServer(bno){
    console.log("getCommetListFromServer 함수 실행, bno:", bno);
    try{
        const resp = await fetch(`/comments/list?bno=${bno}`);
        const result = await resp.json(); // 댓글 리스트 [{...}, {}, {}]
        return result;
    } catch (error){
        console.error('댓글 목록 조회 중 오류 발생:', error);
    }
}


//댓글 리스트 출력 함수
function printCommentList(bno){
    console.log("printCommentList 함수 실행, bno:", bno);

    getCommentListFromServer(bno).then(result => {
        console.log("📌 받은 데이터:", result);

        const div = document.getElementById('commentLine');

        if(!div){
            console.error("commentLine 요소를 찾을 수 없습니다");
            return;
        }

        if(!result || result.length === 0){
            div.innerHTML='<div class="no-comments">등록된 댓글이 없습니다.</div>';
            return;
        }

        let str = '';

        for(let cmt of result){
            console.log("📌 댓글 처리 중:", cmt);

            // JavaScript로 조건 체크
            let actionButtons = '';
            if(loginUser && loginUser === cmt.commenter) {
                actionButtons = `
                    <div class="comment-actions">
                        <button type="button" class="mod" data-cno="${cmt.cno}">수정</button>
                        <button type="button" class="del" data-cno="${cmt.cno}">삭제</button>
                    </div>
                `;
            }
            str += `
                <div class="comment-item">
                    <div class="comment-header">
                        <div class="comment-info">
                            <span class="comment-number">번호: ${cmt.cno}</span>
                            <span class="comment-writer">${cmt.commenter}</span>
                            <span class="comment-date">${cmt.regdate}</span>
                        </div>
                        ${actionButtons}
                    </div>
                    <div class="comment-content">
                        <input type="text" class="cmtTextInput" value="${cmt.content}" readonly>
                    </div>
                </div>
                `;
        }

        div.innerHTML = str;
        console.log("✅ innerHTML 설정 완료");
    });
}


document.getElementById("commentLine").addEventListener("click", (e) =>{
    if(e.target.classList.contains('mod')){
        console.log("수정 버튼 클릭됨");
        let cno = e.target.dataset.cno;
        const cmtItem = e.target.closest('.comment-item');
        const cmtTextInput = cmtItem.querySelector('.cmtTextInput');
        console.log(cmtTextInput);

        if(cmtTextInput.readOnly){
            console.log("수정모드로 전환");
            cmtTextInput.readOnly = false;
            cmtTextInput.focus();
            cmtTextInput.select();
            e.target.textContent = "저장";
        }else{
            const cmtData = {
                cno,
                content: cmtTextInput.value
            };
            if(cmtData.content.trim() === ''){

                alert("댓글을 입력해주세요.");
                cmtTextInput.focus();
                return;
            }
            console.log(cmtData);

            updateCommentToServer(cmtData).then(result => {
                console.log("updateCommentToServer 실행");
                console.log(result);
                if(result==="1"){
                    console.log("댓글 수정 성공");
                    alert('댓글이 성공적으로 수정되었습니다.');
                    cmtTextInput.readOnly = true;
                    e.target.textContent = '수정';
                }else{
                    alert('댓글 수정에 실패했습니다. 다시 시도해주세요.');
                }
                // 댓글 리스트 띄우기
                printCommentList(bnoValue);
            });

        }
    }
    if(e.target.classList.contains("del")){
        console.log("삭제 버튼 클릭됨");
        let cno = e.target.dataset.cno;
        removeCommentToServer(cno).then(result =>{
            if(result === "1"){
                alert("댓글이 삭제되었습니다.");
            }else{
                alert("댓글 삭제에 실패했습니다. 다시 시도해주세요.");
            }
            printCommentList(bnoValue)
        })
    }
})

// 삭제 비동기 요청
async function removeCommentToServer(cno){
    try{
        const resp = await fetch(`/comments/remove?cno=${cno}`);
        const result = await resp.text();
        return result;
    }catch (error) {
        console.log(error);
    }
}

// 수정 비동기 요청
async function updateCommentToServer(cmtData){
    try{
        const url = '/comments/modify';
        const config = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json; charset=UTF-8'
            },
            body: JSON.stringify(cmtData)
        }

        const resp = await fetch(url, config);
        const result = await resp.text(); // isOk
        return result;

    } catch (error){
        console.error('댓글 수정 중 오류 발생:', error);
    }
}

// 페이지 로드 시 댓글 목록 출력
console.log("페이지 로드 - 댓글 목록 불러오기 시작, bnoValue:", bnoValue);
printCommentList(bnoValue);