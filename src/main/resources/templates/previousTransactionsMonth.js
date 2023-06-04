$(document).ready(function () {
    $('#previousMonth').on('click', function () {

        if (document.getElementById('income') === null && document.getElementById('expense') === null) {
            let min = new Date();
            min.setMonth(new Date(min).getMonth() - 1, 1);
            min = min.toLocaleDateString();
            let new_min = min.split(".").reverse().join("-");
            console.log(new_min);

            let max = new Date();
            max.setMonth(new Date(max).getMonth(), 0);
            max = max.toLocaleDateString();
            let new_max = max.split(".").reverse().join("-");
            console.log(new_max);

            $.ajax({
                type: 'GET',
                success: function () {
                    window.location.href = 'http://localhost:8081/previousTransactions?' + 'min_date=' + new_min + '&' + 'max_date=' + new_max;
                }
            });
        }

        if (document.getElementById('income') === null) {

            let min = document.getElementById('expense').textContent;
            min = new Date(min);
            min.setMonth(new Date(min).getMonth() - 1, 1);
            min = min.toLocaleDateString();
            let new_min = min.split(".").reverse().join("-");

            let max = document.getElementById('expense').textContent;
            max = new Date(max);
            max.setMonth(new Date(max).getMonth(), 0);
            max = max.toLocaleDateString();
            let new_max = max.split(".").reverse().join("-");

            $.ajax({
                type: 'GET',
                success: function () {
                    window.location.href = 'http://localhost:8081/previousTransactions?' + 'min_date=' + new_min + '&' + 'max_date=' + new_max;
                }
            });
        }

        else {
            let min = document.getElementById('income').textContent;
            min = new Date(min);
            min.setMonth(new Date(min).getMonth() - 1, 1);
            min = min.toLocaleDateString();
            let new_min = min.split(".").reverse().join("-");

            let max = document.getElementById('income').textContent;
            max = new Date(max);
            max.setMonth(new Date(max).getMonth(), 0);
            max = max.toLocaleDateString();
            let new_max = max.split(".").reverse().join("-");

            $.ajax({
                type: 'GET',
                success: function () {
                    window.location.href = 'http://localhost:8081/previousTransactions?' + 'min_date=' + new_min + '&' + 'max_date=' + new_max;
                }
            });
        }
    });
});