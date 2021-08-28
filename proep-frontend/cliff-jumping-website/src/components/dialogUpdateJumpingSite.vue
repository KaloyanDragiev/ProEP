<template>
  <div data-app class="text-center">
    <v-dialog class="dialog" v-model="dialog" width="1000" persistent>
      <v-card class="wrapper">
        <v-card-title class="headline grey lighten-2" primary-title>Update JumpingSite</v-card-title>

        <form class="content" enctype="multipart/form-data">
          <v-label>Current Name</v-label>
          <v-text-field :label="currentSite.name" v-model="name" required></v-text-field>
          <v-label>Current Difficulty</v-label>
          <v-select :items="items" :label="currentSite.difficultyLevel" v-model="difficultyLevel"></v-select>
          <v-label>Current Height</v-label>
          <v-text-field :label="currentSite.height" v-model="height" required></v-text-field>
          <v-row>
            <v-col cols="6" sm="4" md="3">
              <v-label>Current Image</v-label>
              <v-img
                :src="currentSite.image"
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
              <!-- <GoogleMap class="google-map"></GoogleMap> -->
            </v-col>
          </v-row>
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
            <v-btn class="create_button" @click="update()">Update</v-btn>
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
import Vue from "vue";
export default {
  data() {
    return {
      items: ["Beginner", "Intermediate", "Pro"],
      name: "",
      coordinates: "",
      difficultyLevel: "",
      height: null,
      rate: 5,
      image: ""
    };
  },
  async created() {},
  components: {},
  computed: {
    ...mapGetters({
      nameFromStore: "name",
      currentSite: "getCurrentSite"
    }),
    Long() {
      return this.$store.getters.getLongitude;
    },
    Lat() {
      return this.$store.getters.getLatitude;
    },
    dialog: {
      get() {
        return this.$store.state.dialogUpdateJumpingSite;
      },
      set(state) {
        this.$store.commit("closeDialog", state);
      }
    }
  },
  methods: {
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
    closeDialog(state) {
      this.$store.commit("closeDialogUpdateJumpinSite", state);
    },
    update() {
      let vue = Vue;
      let vm = this;
      axios
        .put("jumpingsites/edit", {
          name: vm.name,
          difficultyLevel: vm.difficultyLevel,
          coordiantes:
            vm.$store.getters.getLongitude +
            " " +
            vm.$store.getters.getLatitude,
          height: vm.height,
          image: vm.image,
          idJumpingSite: vm.currentSite.id
        })
        .then(() => {
          let site = new JumpingSide(
            vm.currentSite.id,
            vm.name,
            vm.difficultyLevel,
            vm.height,
            vm.$store.getters.getLongitude +
              " " +
              vm.$store.getters.getLatitude,
            vm.currentSite.rate,
            vue.axios.defaults.baseURL + vm.image,
            vm.currentSite.userId
          );
          vm.$store.dispatch("updateSite", site);
          vm.closeDialog();
          console.log(site);
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