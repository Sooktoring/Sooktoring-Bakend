let stompClient = null;
const greetingSubApi = '/sub/hello';
const greetingPubApi = '/pub/hello';
const chatSubApi = '/sub/message';
const chatPubApi = '/pub/message';

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe(greetingSubApi, function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
        stompClient.subscribe(chatSubApi, function (chat) {
            showChat(JSON.parse(chat.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send(greetingPubApi, {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function sendChat() {
    stompClient.send(chatPubApi, {}, JSON.stringify({'name': $("#name").val(), 'message': $("#chatMessage").val()}));
}
function showChat(chat) {
    $("#greetings").append("<tr><td>" + chat.name + " : " + chat.message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    $( "#chatSend" ).click(function(){ sendChat(); });
});