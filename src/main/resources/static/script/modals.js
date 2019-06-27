function openModal(){
    var elem = document.getElementById('modal1');
    var instance = M.Modal.getInstance(elem);
    instance.open();
}

$(document).ready(function(){
    $(".modal-overlay").click(function () {
        $("#modal1").fadeOut();
        $(".modal-overlay").fadeOut("slow", function (){
            $(".modal-wrapper").remove();
        });
    });
    $(".modal-close").click(function () {
        $("#modal1").fadeOut();
        $(".modal-overlay").fadeOut("slow", function (){
            $(".modal-wrapper").remove();
        });
    });
});