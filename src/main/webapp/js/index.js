$(document).ready(function () {
    $(".btn_base64").bind('click', function (event) {
        var ul = $(event.target).parent().parent();
        var dataType = $(event.target).data("type");
        var result = ul.find("span[encode-type='" + dataType + "']").text("");
        var value = ul.find(":text.ipt_value[encode-type='" + dataType + "']").val();
        if (dataType === "encode") {
            result.text(window.btoa(encodeURI(value)));
        } else {
            result.text(decodeURI(window.atob(value)));
        }
    });

    $(".btn_request").bind('click', function (event) {
        var row = $(event.target).parent().parent();
        // clear history
        row.find("td.result").text("");

        var base = row.find("td.baseUrl").text();
        console.log("Get base url: " + base);
        base += "?test=true";

        // append parameters
        $.each(row.find(":text.ipt_value"), function (i, e) {
            var element = $(e);
            var value = $.trim(element.val());
            if (value === "") {
                return true;
            }
            var name = element.attr("id");
            var valueBase64 = window.btoa(encodeURI(value)).replace(new RegExp("=", "g"), "~");
            console.log("Get parameter  name: " + name);
            console.log("Get parameter value: " + value);
            console.log("Value base64 encode: " + valueBase64);
            base += "&" + name + "=" + valueBase64;
            console.log("Current request url:" + base);
        });
        //ajax request
        $.get(base, function (data, status) {
            console.log("Get ajax response   data: " + data);
            console.log("Get ajax response status: " + status);
            var dataDecode = window.atob(data);
            console.log("Get ajax response decode: " + dataDecode);
            row.find("td.result").text(dataDecode);
        });
    });

    $(".btn_reset").bind('click', function (event) {
        var row = $(event.target).parent().parent();
        row.find("td.parameters").find(":text.ipt_value").val("");
        row.find("td.result").text("");
    })
});