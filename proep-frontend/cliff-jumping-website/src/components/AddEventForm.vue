<template>

    <v-app class="input-container">
        <v-card raised class="card-wrapper" >
            <h1>Create Event</h1>
            <form >
              <v-text-field 
                label="Event Name" 
                required>
              </v-text-field>
               <v-select
                :items="items"
                label="Date"
               ></v-select>
               <v-text-field 
                label="Description" 
                required>
              </v-text-field>
              <v-row>
                  <v-col cols="6" sm="4">
                      <v-img
                        src="https://cdn.vuetifyjs.com/images/parallax/material2.jpg"
                        gradient="to top right, rgba(100,115,201,.33), rgba(25,32,72,.7)"
                      ></v-img>
                  </v-col>
                  <v-col>
                      <v-file-input 
                      label="Upload Picture"
                      prepend-icon="mdi-camera"
                      ></v-file-input>
                  </v-col>
              </v-row>
              <v-row>
             <v-col>
                  <GoogleMap class="google-map"></GoogleMap>
             </v-col>   
              </v-row>
              <v-input :success-messages="['Latitude:** , Logitude:**']" success disabled>Choose Location on map</v-input>
              <div class="button_wrapper">
               <v-btn class="create_button">Create</v-btn>
              </div>
            </form>
        </v-card>
    </v-app>
</template>
<script>
import GoogleMap from "../components/GoogleMap.vue";
export default {
    data: () => ({
      items: ['Beginner', 'Intermediate', 'Pro'],
    }),
    components: {
    GoogleMap,
  },
}
</script>
<style scoped>
.input-container {
  width: 33vw;
  margin-left: 33%;
}
.card-wrapper {
  padding: 26px;
}
.h1{
    margin:auto;
    text-align: center;
}
.google-map {
  width: 25vw; 
  height: 15vw;
  float: left;
}
.button_wrapper {
  width: 100%;
  margin: auto;
  text-align: center;
}
</style>