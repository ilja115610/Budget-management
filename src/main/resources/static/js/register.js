$(function () {
    $("#registerBtn").click(function () {
        window.location.href = '/register';
    });
});

$(function () {
    const error = $("#errorMsg");
    if (error.text() !== "")
    {
        $(error).removeClass("d-none");
    }
});
