function toggleAnswer(num) {
  var answer = document.getElementById('answer' + num);
  if (answer.style.display === 'none') {
    answer.style.display = 'block';
  } else {
    answer.style.display = 'none';
  }
}

function openForm() {
  document.getElementById("myForm").style.display = "block";
}

function closeForm() {
  document.getElementById("myForm").style.display = "none";
}

function submitQuestion() {
  var questionInput = document.getElementById("questionInput").value;
  var answerInput = document.getElementById("answerInput").value;

  if (questionInput && answerInput) {
    var newQuestion = document.createElement("div");
    newQuestion.className = "question";
    newQuestion.onclick = function() { toggleAnswer(document.querySelectorAll(".question").length + 1); };

    var newQuestionTitle = document.createElement("h2");
    newQuestionTitle.innerText = (document.querySelectorAll(".question").length + 1) + ". " + questionInput;

    var newAnswer = document.createElement("div");
    newAnswer.className = "answer";
    newAnswer.style.display = "block";
    var newAnswerText = document.createElement("p");
    newAnswerText.innerText = answerInput;
    newAnswer.appendChild(newAnswerText);

    newQuestion.appendChild(newQuestionTitle);
    newQuestion.appendChild(newAnswer);

    document.querySelector(".question-list").appendChild(newQuestion);

    closeForm();
  } else {
    alert("질문과 답변을 모두 입력해주세요.");
  }
}
