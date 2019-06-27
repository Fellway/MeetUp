function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#image-uploaded').attr('src', e.target.result);
        }

        reader.readAsDataURL(input.files[0]);
    }
}

function removeUploadedPhoto() {
    $("#image-uploaded").attr('src', '#');
    $("#imgInp").val("");
}

$("#imgInp").change(function () {
    $("#imgInp").hide();
    $("#image-uploaded").show();
    $(".image-placeholder").css("height", "auto");
    $(".image-placeholder-description").hide();
    $(".image-placeholder").removeClass("thumbnail");
    $(".image-placeholder").removeClass("hoverable");
    $("span").show();
    readURL(this);
});

$("span").click(function () {
    removeUploadedPhoto();
    $("#imgInp").show();
    $("#image-uploaded").hide();
    $(".image-placeholder").css("height", "300px");
    $(".image-placeholder-description").show();
    $(".image-placeholder").addClass("thumbnail");
    $(".image-placeholder").addClass("hoverable");
    $("span").hide();
});