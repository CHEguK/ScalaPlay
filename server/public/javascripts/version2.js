

$("#contents").load("/login2")

function login() {
    const username = $('#loginName').val();
    const passwd = $('#loginPass').val();
    $("#contents").load("/validate2?username=" + username + "&password=" + passwd);
}

function createUser() {
    const username = $('#createName').val()
    const passwd = $('#createPass').val()
    $("#contents").load("/create2?username=" + username + "&password=" + passwd);
}

function addTask() {
    const task = $('#newTask').val();
    $("#contents").load("/addTask2?task=" + task);
}

function deleteTask(index) {
    $("#contents").load("/deleteTask2?index=" + index);
}