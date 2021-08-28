<template>
  <div data-app class="text-center">
    <v-dialog class="dialog" v-model="dialog" max-width="1200" light>
      <v-card class="wrapper">
        <v-card-title class="headline grey lighten-2" primary-title>Create Article</v-card-title>

        <form class="content" enctype="multipart/form-data">
          <v-text-field label="Article Title" v-model="name" required></v-text-field>
          <!-- <v-select :items="items" label="Difficulty Level" v-model="difficultyLevel"></v-select> -->
          <!-- <v-text-field label="Approximate height (in meters)" v-model="height" required></v-text-field>
          -->
          <v-container fluid>
            <v-textarea
              outlined
              clearable
              clear-icon="mdi-marker-cancel"
              label="Text"
              v-model="description"
              value="This is clearable text."
            ></v-textarea>
          </v-container>

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

          <!-- <v-row>
            <v-col>
              <GoogleMap class="google-map"></GoogleMap>
            </v-col>
          </v-row>-->

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
// import { Article } from "../interfaces/Article";
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
        return this.$store.state.dialogCreateArticle;
      },
      set(state) {
        this.$store.commit("closeDialogCreateArticle", state);
      }
    }
  },
  methods: {
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
      this.$store.commit("closeDialogCreateArticle", state);
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
    }
  }
};
</script>

<style lang="scss" scoped>
.content {
  padding: 16px;
}
</style>