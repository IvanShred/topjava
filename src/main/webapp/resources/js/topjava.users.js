// $(document).ready(function () {
$(function () {
    makeEditable({
            fncUpdateTable: updateTable,
            ajaxUrl: "ajax/admin/users/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "email"
                    },
                    {
                        "data": "roles"
                    },
                    {
                        "data": "enabled"
                    },
                    {
                        "data": "registered"
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
    $(".checkbox").click(function () {
        let checkbox = this;
        $.ajax({
            type: "POST",
            url: context.ajaxUrl + $(this).parent().parent().attr("id"),
            data: {"enabled": checkbox.checked}
        }).done(function () {
            $(checkbox).parent().parent().attr("data-userEnabled", checkbox.checked);
        }).fail(function () {
            $(checkbox).prop("checked", !checkbox.checked);
        });
    });
});


