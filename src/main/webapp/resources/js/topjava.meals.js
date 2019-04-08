// $(document).ready(function () {
$(function () {
    makeEditable({
            fncUpdateTable: updateFilteredTable,
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
                        "desc"
                    ]
                ]
            })
        }
    );
});

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: context.ajaxUrl + "filter",
        data: $('#filter').serialize(),

    }).done(function (data) {
        context.datatableApi.clear().rows.add(data).draw();
    });
}

function clearFilter() {
    $.ajax({
        type: "GET",
        url: context.ajaxUrl,
    }).done(function (data) {
        context.datatableApi.clear().rows.add(data).draw();
    });
}
