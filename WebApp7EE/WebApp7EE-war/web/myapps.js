var websocketSession;

function f_onmessage(evt) {
    websocketMessages = document.getElementById('websocketMessages');
    websocketMessages.innerHTML = websocketMessages.innerHTML + evt.data + '<br/>';
}

function open() {
    if (!websocketSession) {
        websocketSession = new WebSocket('ws://localhost:8080/WebApp7EE-war/mywebsocket');
        websocketSession.onmessage = f_onmessage;
    }
}

function close() {
    if (websocketSession) {
        websocketSession.close();
    }
}

function sendMessage(msg) {
    websocketSession.send(msg);
}

