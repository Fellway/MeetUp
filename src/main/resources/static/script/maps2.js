var map;
var marker;
var lat;
var lng;

function createMap() {
    var myLatlng = new google.maps.LatLng(tLat,tLng);

    var options = {
        center: {
            lat: tLat,
            lng: tLng
        },
        disableDefaultUI: true,
        zoom: 15
    };

    map = new google.maps.Map(document.getElementById('map'), options);

    var marker = new google.maps.Marker({
        position: myLatlng,
        map: map,
        title: 'Hello!'
    });

    marker.setMap(map);
}

