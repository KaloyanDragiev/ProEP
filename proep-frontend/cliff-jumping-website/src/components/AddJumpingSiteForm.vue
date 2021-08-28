<template>
  <v-app class="input-container">
    <v-card raised class="card-wrapper">
      <h1>Create Jumping Site</h1>
      <form enctype="multipart/form-data">
        <v-text-field label="Location Name" v-model="name" required></v-text-field>
        <v-select :items="items" label="Difficulty Level" v-model="difficultyLevel"></v-select>
        <v-text-field label="Approximate height (in meters)" v-model="height" required></v-text-field>
        <v-row>
          <v-col cols="6" sm="4">
            <v-img
              src="https://cdn.vuetifyjs.com/images/parallax/material2.jpg"
              gradient="to top right, rgba(100,115,201,.33), rgba(25,32,72,.7)"
            ></v-img>
          </v-col>
          <v-col>
            <v-file-input placeholder="Upload your picture" type="file" ref = "file" label="Upload Picture" prepend-icon="mdi-camera" @change="OnSelect"></v-file-input>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <GoogleMap class="google-map"></GoogleMap>
          </v-col>
        </v-row>
        <v-input
          :success-messages="['Latitude:' + Lat + ' , Logitude: ' + Long]"
          success
          disabled
          v-model="coordinates"
        >Choose Location on map (right-click)</v-input>
        <div class="button_wrapper">
          <v-btn class="create_button" @click="addJumpingSite()">Create</v-btn>
        </div>
      </form>
    </v-card>
  </v-app>
</template>
<script>
import GoogleMap from "../components/GoogleMap.vue";
import axios from "axios";
import { JumpingSide } from "../interfaces/jumpingSite";

export default {
  data: () => ({
    items: ["Beginner", "Intermediate", "Pro"],
    name: "",
    coordinates: "",
    difficultyLevel: "",
    height: null,
    rate: 5
  }),
  components: {
    GoogleMap
  },
  computed: {
    Long() {
      return this.$store.getters.getLongitude;
    },
    Lat() {
      return this.$store.getters.getLatitude;
    }
  },
  methods: {
    onSelect()  {
      const file =this.$refs.file.files[0];
      this.file = file;
    },
    addJumpingSite() {
      //const formData = FormData();
      //formData.append('file', this.file);

      /*try {
        axios.post('file', formData);
      }
      catch(err) {
        console.log(err);
      }*/


      let vm = this;
      let site = new JumpingSide(
        null,
        this.difficultyLevel,
        this.name,
        this.height,

        vm.$store.getters.getLatitude + " " + vm.$store.getters.getLongitude,

        this.rate,
        "",
        vm.$store.getters.getCurrentUser.idUser
      );
      axios
        .post("/jumpingsites/create/web", {
          name: this.name,
          difficultyLevel: this.difficultyLevel,
          coordinates:
            vm.$store.getters.getLatitude + " " + vm.$store.getters.getLatitude,
          height: this.height,
          rate: this.rate,  
          image: this.image,
          idUser: vm.$store.getters.getCurrentUser.idUser
        })
        .then(res => {
          vm.$store.commit("addJumpingSites", site);
          console.log(res);
          this.$router.push("/jumping-sites");
        });
    }
  }
};
</script>
<style scoped>
.input-container {

  margin-left: 33%;
}
.card-wrapper {
  padding: 26px;
}
.h1 {
  margin: auto;
  text-align: center;
}
.google-map {
  height: 20vw;
  float: left;
}
.button_wrapper {
  width: 100%;
  margin: auto;
  text-align: center;
}
</style>
