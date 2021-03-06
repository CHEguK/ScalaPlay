
const csrfToken = $("#csrfToken").val()
const loginRoute = $("#loginRoute").val();
const validateRoute = $("#validateRoute").val();

$("#contents").load("/login2")

function login() {
    const username = $('#loginName').val();
    const passwd = $('#loginPass').val();
    $.post(validateRoute, { username, passwd, csrfToken }, data => {
        $("#contents").html(data)
    });
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