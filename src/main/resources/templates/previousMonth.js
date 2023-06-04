$(document).ready(function () {
    $('#previousMonth').on('click', function () {

        if (document.getElementById('test_income') === null && document.getElementById('test') === null) {
            let params = (new URL(document.location)).searchParams;
            let min = params.get("min_date");
            let max = params.get("max_date");

            min = new Date(min);
            min.setMonth(new Date(min).getMonth() - 1, 1);
            min = min.toLocaleDateString();
            let new_min = min.split(".").reverse().join("-");

            max = new Date(max);
            max.setMonth(new Date(max).getMonth(), 0);
            max = max.toLocaleDateString();
            let new_max = max.split(".").reverse().join("-");

            $.ajax({
                type: 'GET',
                success: function () {
                    window.location.search = 'min_date=' + new_min + '&' + 'max_date=' + new_max;
                }
            });
        }

        if (document.getElementById('test_income') === null) {
            let min_date = document.getElementById('test').textContent;
            min_date = new Date(min_date);
            min_date.setMonth(new Date(min_date).getMonth() - 1, 1);
            min_date = min_date.toLocaleDateString();
            let new_min_date = min_date.split(".").reverse().join("-");
            console.log(new_min_date);

            let max_date = document.getElementById('test').textContent;
            max_date = new Date(max_date);
            max_date.setMonth(new Date(max_date).getMonth(), 0);
            max_date = max_date.toLocaleDateString();
            let new_max_date = max_date.split(".").reverse().join("-");
            console.log(new_max_date);

            $.ajax({
                type: 'GET',
                success: function () {
                    window.location.search = 'min_date=' + new_min_date + '&' + 'max_date=' + new_max_date;
                }
            });
        }

        else {
            let min_date = document.getElementById('test_income').textContent;
            min_date = new Date(min_date);
            min_date.setMonth(new Date(min_date).getMonth() - 1, 1);
            min_date = min_date.toLocaleDateString();
            let new_min_date = min_date.split(".").reverse().join("-");
            console.log(new_min_date);

            let max_date = document.getElementById('test_income').textContent;
            max_date = new Date(max_date);
            max_date.setMonth(new Date(max_date).getMonth(), 0);
            max_date = max_date.toLocaleDateString();
            let new_max_date = max_date.split(".").reverse().join("-");
            console.log(new_max_date);

            $.ajax({
                type: 'GET',
                success: function () {
                    window.location.search = 'min_date=' + new_min_date + '&' + 'max_date=' + new_max_date;
                }
            });
        }
    });
});