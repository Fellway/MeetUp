$(document).ready(function () {
    $('select').formSelect();
});

var datePickerOptions = {
    format: 'dd-mm-yyyy'
};

$(document).ready(function () {
    $('.datepicker').datepicker(datePickerOptions);
});

var timepickerOptions = {
    twelveHour: false
};

$(document).ready(function () {
    $('.timepicker').timepicker(timepickerOptions);
});

window.onload = function () {
    var elems = document.querySelectorAll("input[type=range]");
    M.Range.init(elems);
};

$(document).ready(function(){
    $('.modal').modal({
        onCloseStart: function(){
            $('#description').val(tinyMCE.activeEditor.getContent({format : 'text'}));
            checkDescription();
        }
    });
});

var title = $("#title");
var description = $("#description");

function checkTitle() {
    if ($("#b-helper-title").length) {
        $("#b-helper-title").hide();
    }

    var vTitle = title.val();

    if (vTitle.length > 4 && vTitle.length <= 100) {
        title.removeClass("invalid");
        title.addClass("valid");
        $("#helper-title").hide();
        return true;
    }else {
        $("#helper-title").attr("data-error", "Title must be at least 5 and up to 100 characters long.").show(500);
        title.addClass("invalid");
        title.removeClass("valid");
    }
}

function checkDescription() {
    if ($("#b-helper-description").length) {
        $("#b-helper-description").hide();
    }

    var vDescription = description.val();

    if (vDescription.length > 4 && vDescription.length <= 1000) {
        description.removeClass("invalid");
        description.addClass("valid");
        $("#helper-description").hide();
        return true;
    }else {
        $("#helper-description").attr("data-error", "Description must be at least 5 and up to 1000 characters long.").show(500);
        description.addClass("invalid");
        description.removeClass("valid");
    }
}

$(document).ready(function () {
    if ($("#b-helper-title").length) {
        title.addClass("invalid");
    }
});

title.keyup(checkTitle);
title.change(checkTitle);
description.keyup(checkDescription);
description.change(checkDescription);
