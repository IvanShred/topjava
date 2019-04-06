// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: "ajax/profile/meals/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "dateTime"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "asc"
                    ]
                ]
            })
        }
    );
});

$('#dateTime').datetimepicker({
    format:'Y-m-d H:i'
});

$('#startTime, #endTime').datetimepicker({
    datepicker:false,
    format:'H:i',
});

$('#startDate, #endDate').datetimepicker({
    timepicker:false,
    format:'d.m.Y',
});