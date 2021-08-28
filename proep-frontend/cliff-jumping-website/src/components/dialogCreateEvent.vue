<template>
  <div data-app class="text-center">
    <v-dialog class="dialog" v-model="dialog" width="1000" light>
      <v-card class="wrapper">
        <v-card-title class="headline grey lighten-2" primary-title>Create Event</v-card-title>

        <form class="content" enctype="multipart/form-data">
          <v-text-field label="Event Name" v-model="name" required></v-text-field>
          <!-- <v-select :items="items" label="Difficulty Level" v-model="difficultyLevel"></v-select> -->
          <!-- <v-text-field label="Approximate height (in meters)" v-model="height" required></v-text-field>
          -->
          <v-container fluid>
            <v-textarea
              outlined
              clearable
              clear-icon="mdi-marker-cancel"
              label="Description"
              v-model="description"
              value="This is clearable text."
            ></v-textarea>
          </v-container>
          <v-row justify="center">
            <v-date-picker
              v-model="picker"
              full-width
              :landscape="$vuetify.breakpoint.smAndUp"
              class="mt-4"
            ></v-date-picker>
          </v-row>
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
          <v-select
            :items="sites"
            label="Location (Jumping Site)"
            v-model="siteId"
            outlined
            :item-text="textSite"
            item-value="id"
          ></v-select>
          <!-- <v-row>
            <v-col>
              <GoogleMap class="google-map"></GoogleMap>
            </v-col>
          </v-row>-->

          <!-- <v-input
            :success-messages="['Latitude:' + Lat + ' , Logitude: ' + Long]"
            success
            disabled
            v-model="coordinates"
          >Choose Location on map (right-click)</v-input>-->
          <!-- <div class="button_wrapper">
            <v-btn class="create_button" @click="addJumpingSite()">Create</v-btn>
          </div>-->

          <v-divider></v-divider>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" text @click="closeDialog(false)">Cancel</v-btn>
            <v-btn class="create_button" @click="createEvent()" :disabled="isDisabled">Create</v-btn>
          </v-card-actions>
          <!-- I moved the form from upper level -->
        </form>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
// import GoogleMap from "../components/GoogleMap.vue";
import axios from "axios";
import { JumpingSide } from "../interfaces/jumpingSite";
import { Event } from "../interfaces/events";
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
      image: "",
      picker: new Date().toISOString().substr(0, 10),
      description: "",
      site: "",
      siteId: ""
    };
  },
  components: {},
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
    currentUser() {
      return this.$store.getters.getCurrentUser;
    },
    sites() {
      return this.$store.getters.getListOfJumpingSites;
    },
    ...mapGetters({}),
    dialog: {
      get() {
        return this.$store.state.dialogCreateEvent;
      },
      set(state) {
        this.$store.commit("closeDialogCreateEvent", state);
      }
    }
  },
  methods: {
    textSite: item =>
      item.name +
      " — Level" +
      item.difficultyLevel +
      " — Rate: " +
      item.rate +
      " stars" +
      " — Coordinates: " +
      item.coordinates,

    createEvent() {
      let vm = this;
      console.log(vm.picker);
      console.log(vm.siteId);
      console.log("Current User", vm.currentUser.idUser);
      axios
        .post("/events/create/web", {
          name: vm.name,
          date: vm.picker,

          description: vm.description,
          idJumpingSite: vm.siteId,
          image: vm.image,
          idUser: vm.currentUser.idUser
        })
        .then(res => {
          console.log(res);
          let obj = res.data[0];
          let events = vm.$store.getters.getEvents;
          let event = new Event(
            obj.idEvent,
            obj.name,
            obj.date,
            obj.description,
            obj.JumpingSite_idJumpingSite,
            obj.image,
            obj.idUser
          );
          events.push(event);
          vm.$store.commit("addEvents", events);
          vm.closeDialog(false);
        });
    },
    closeDialog(state) {
      this.$store.commit("closeDialogCreateEvent", state);
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