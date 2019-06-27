$(window).on('resize', function () {
    var video = $('#myVideo');
    var image = $('#myImage');
    var screenWidth = $(window).width();
    if (screenWidth < 700) {
        video.removeAttr('autoplay');
        video.css("display", "none");
        image.css("display", "block");
    } else {
        video.css("display", "block");
        image.css("display", "none");
        video.attr('autoplay');
    }
}).trigger('resize');