$ (function () {
    $('#addBudget').click(function (){
        let budget = prompt("Please set planned budget");
        $.ajax({
            url: '/budgets',
            method: 'post',
            type: 'json',
            data: {"total" : budget},
            success : function () {
                console.log(budget);
                location.reload();
            },
            error : function () {
                console.log("There was an error creating a new budget");
            }
        });
    });
});
