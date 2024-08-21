document.addEventListener("DOMContentLoaded", () => {
  const socket = new SockJS("http://localhost:8080/ws");
  const stompClient = Stomp.over(socket);
  const username = "username"; // 실제 로그인한 사용자의 이름으로 설정

  stompClient.connect({}, (frame) => {
    console.log("Connected: " + frame);

    stompClient.subscribe("/topic/public", (message) => {
      const msg = JSON.parse(message.body);
      showMessage(msg);
    });

    stompClient.send("/app/chat.addUser", {}, JSON.stringify({ sender: username, type: "JOIN" }));
  });

  document.getElementById("send-button").addEventListener("click", () => {
    const messageContent = document.getElementById("message-input").value;
    if (messageContent && stompClient) {
      const chatMessage = {
        sender: username,
        content: messageContent,
        type: "CHAT",
      };
      stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
    }
  });
});

function showMessage(message) {
  const messageElement = document.createElement("li");
  messageElement.textContent = `${message.sender}: ${message.content}`;
  if (message.sender === "username") {
    // 실제 로그인한 사용자의 이름으로 변경
    messageElement.classList.add("my-message");
  } else {
    messageElement.classList.add("other-message");
  }
  document.getElementById("messages").appendChild(messageElement);
}
