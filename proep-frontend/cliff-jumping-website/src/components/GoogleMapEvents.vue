<template>
  <div class="App"></div>
</template>

<script>
import gmapsInit from "../utils/gmaps";
import MarkerClusterer from "@google/markerclusterer";
//import { Marker } from '../interfaces/Marker';
//import AddJumpingSiteForm from "../components/AddJumpingSiteForm.vue";
export default {
  name: "App",
  data: () => ({
    Longitude: 0,
    Latitude: 0,
    markers: []
  }),
  async crated() {},
  async mounted() {
    this.$store.dispatch("AddMarkersEvents");

    let vm = this;
    try {
      const google = await gmapsInit();
      const geocoder = new google.maps.Geocoder();
      const map = new google.maps.Map(this.$el);
      geocoder.geocode({ address: "Netherlands" }, (results, status) => {
        if (status !== "OK" || !results[0]) {
          throw new Error(status);
        }
        var SelectMarker = new google.maps.Marker({
          icon: {
            path: google.maps.SymbolPath.BACKWARD_CLOSED_ARROW,
            scale: 7
          },
          map: map,
          title: "Selected Location"
        });
        // Set Markers
        var infowindow = new google.maps.InfoWindow();
        var marker, i, content;
        for (i = 0; i < vm.Markers.length; i++) {
          marker = new google.maps.Marker({
            position: new google.maps.LatLng(
              vm.Markers[i].Latitude,
              vm.Markers[i].Longitude
            ),
            map: map,
            title: vm.Markers[i].Name
          });
          vm.markers.push(marker);
          // Marker onclick listener
          google.maps.event.addListener(
            marker,
            "click",
            (function(marker, i) {
              return function() {
                content =
                  '<div id="content">' +
                  '<div id="siteNotice">' +
                  "</div>" +
                  '<h1 id="firstHeading" class="firstHeading">' +
                  vm.Markers[i].Name +
                  "</h1>" +
                  '<div id="bodyContent">' +
                  "<p><b>Difficulty Level: </b>" +
                  vm.Markers[i].difficultyLevel +
                  "</p>" +
                  "<p><b>Height : </b>" +
                  vm.Markers[i].height +
                  "</p>" +
                  "<p><b>Rating : </b>" +
                  vm.Markers[i].rate +
                  "</p>" +
                  "</div>" +
                  "</div>";
                infowindow.setContent(content);
                infowindow.open(map, marker);
                //change position to markers position
                map.setCenter(
                  new google.maps.LatLng(
                    vm.Markers[i].Latitude,
                    vm.Markers[i].Longitude
                  )
                );
              };
            })(marker, i)
          );
        }
        //handle Marker Clusters
        new MarkerClusterer(map, vm.markers, {
          imagePath:
            "https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m"
        });
        // right-click listener
        google.maps.event.addListener(map, "rightclick", function(event) {
          var lat = event.latLng.lat();
          var lng = event.latLng.lng();
          var LatLng = new google.maps.LatLng(lat, lng);
          if (vm.OnCreateJumpingSite) {
            SelectMarker.setPosition(LatLng);
            vm.GetCoordinates(lng, lat);
          }
          console.log(vm.Markers);
        });

        map.setCenter(results[0].geometry.location);
        map.fitBounds(results[0].geometry.viewport);
      });
    } catch (error) {
      console.error(error);
    }
  },
  methods: {
    GetCoordinates(long, lat) {
      this.$store.dispatch("setLongLat", { long, lat });
    }
  },
  computed: {
    Markers() {
      return this.$store.getters.getMarkers;
    },
    OnCreateJumpingSite() {
      return this.$store.getters.getdialogJumpingSiteState;
    }
  }
};
</script>

<style>
html,
body {
  margin: 0;
  padding: 0;
}

.App {
  width: 50vw;
  height: 100vh;
  float: right;
}
</style>