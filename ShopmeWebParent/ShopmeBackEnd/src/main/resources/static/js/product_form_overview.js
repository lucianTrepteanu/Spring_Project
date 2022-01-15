function checkUnique(form) {
    productId = $("#id").val();
    productName = $("#name").val();

    csrfValue = $("input[name='_csrf']").val();

    params = {id: productId, name: productName, _csrf: csrfValue};

    $.post(checkUniqueUrl, params, function(response) {
        if (response == "OK") {
            form.submit();
        } else if (response == "Duplicate") {
            showWarningModal("There is another product having the name " + productName);
        } else {
            showErrorModal("Unknown response from server");
        }

    }).fail(function() {
        showErrorModal("Could not connect to the server");
    });

    return false;
}	