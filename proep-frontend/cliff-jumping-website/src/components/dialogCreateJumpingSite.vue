<template>
  <div data-app class="text-center">
    <v-dialog class="dialog" v-model="dialog" width="1000">
      <v-card class="wrapper">
        <v-card-title class="headline grey lighten-2" primary-title>Create JumpingSite</v-card-title>

        <form class="content" enctype="multipart/form-data">
          <v-text-field label="Location Name" v-model="name" required></v-text-field>
          <v-select :items="items" label="Difficulty Level" v-model="difficultyLevel"></v-select>
          <v-text-field label="Approximate height (in meters)" v-model="height" required></v-text-field>
          <v-row>
            <v-col cols="6" sm="4" md="3">
              <v-img
                src="https://cdn.vuetifyjs.com/images/parallax/material2.jpg"
                gradient="to top right, rgba(100,115,201,.33), rgba(25,32,72,.7)"
              ></v-img>
            </v-col>
            <v-col>
              <input
                placeholder="Upload your picture"
                type="file"
                ref="file"
                label="Upload Picture"
                prepend-icon="mdi-camera"
                @change="selectImage"
              />
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
          <!-- <div class="button_wrapper">
            <v-btn class="create_button" @click="addJumpingSite()">Create</v-btn>
          </div>-->

          <v-divider></v-divider>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" text @click="closeDialog(false)">Cancel</v-btn>
            <v-btn class="create_button" @click="addJumpingSite()" :disabled="isDisabled">Create</v-btn>
          </v-card-actions>
          <!-- I moved the form from upper level -->
        </form>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import GoogleMap from "../components/GoogleMap.vue";
import axios from "axios";
import { JumpingSide } from "../interfaces/jumpingSite";
export default {
  data() {
    return {
      items: ["Beginner", "Intermediate", "Pro"],
      name: "",
      coordinates: "",
      difficultyLevel: "",
      height: null,
      rate: 5,
      file: "",
      disabled: true,
      image: ""
    };
  },
  components: { GoogleMap },
  computed: {
    isDisabled() {
      return this.disabled;
    },
    Long() {
      return this.$store.getters.getLongitude;
    },
    Lat() {
      return this.$store.getters.getLatitude;
    },
    ...mapGetters({}),
    dialog: {
      get() {
        return this.$store.state.dialogJumpingSite;
      },
      set(state) {
        this.$store.commit("closeDialog", state);
      }
    }
  },
  methods: {
    closeDialog(state) {
      this.$store.commit("closeDialog", state);
    },
    async selectImage() {
      let vm = this;
      const file = this.$refs.file.files[0];
      this.file = file;

      const formData = new FormData();
      formData.append("file", this.file);

      try {
        await axios.post("/jumpingsites/upload", formData).then(res => {
          console.log(res);
          vm.image = res.data.file.path;
        });
        console.log("Uploaded");
        vm.disabled = false;
      } catch (err) {
        console.log("Something went error");
      }
    },
    addJumpingSite() {
      /*try {
        axios.post('file', formData);
      }
      catch(err) {
        console.log(err);
      }*/
      let vm = this;
      let site = new JumpingSide(
        null,
        this.name,
        this.difficultyLevel,

        this.height,

        vm.$store.getters.getLongitude + " " + vm.$store.getters.getLatitude,

        this.rate,
        this.image,
        vm.$store.getters.getCurrentUser.idUser
      );
      axios
        .post("/jumpingsites/create/web/jsonbody", {
          name: vm.name,
          difficultyLevel: vm.difficultyLevel,
          coordinates:
            vm.$store.getters.getLongitude +
            " " +
            vm.$store.getters.getLatitude,
          height: vm.height,
          rate: vm.rate,
          image: vm.image,
          idUser: vm.$store.getters.getCurrentUser.idUser
        })
        .then(res => {
          site.id = res.data[0].idJumpingSite;
          console.log(site);
          let allSites = vm.$store.getters.getListOfJumpingSites;
          allSites.push(site);
          vm.$store.commit("addJumpingSites", allSites);
          console.log(res);
          this.closeDialog(false);
        });
    }
  }
};
</script>

<style lang="scss" scoped>
.headline {
  background: whitesmoke !important;
}

.content {
  padding: 16px;
  //   height: 100%;
}

.google-map {
  width: 100% important;
  height: 250px !important;
  float: none;
}
.dialog {
}
</style>