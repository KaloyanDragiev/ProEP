<template>
  <div>
    <v-toolbar class="toolbar" flat>
      <v-btn @click.stop="drawer = !drawer" icon>
        <v-icon>mdi-format-list-bulleted-square</v-icon>
      </v-btn>
      <v-toolbar-title>Jumping Sites</v-toolbar-title>
      <v-spacer></v-spacer>

      <v-btn color="primary" light class="mx-1" @click="openCreateJumpingSite()">
        <v-icon>mdi-plus</v-icon>CREATE
      </v-btn>
    </v-toolbar>
    <JumpingSitesRecommendations v-if="isLogged"></JumpingSitesRecommendations>
    <v-col>
      <v-row></v-row>
    </v-col>
    <v-container v-if="AddJumpingSite">
      <v-card height="100vh" class="overflow-hidden">
        <AddJumpingSiteForm></AddJumpingSiteForm>
      </v-card>
    </v-container>
    <v-container v-if="UpdateJumpingSite">
      <v-card height="100vh" class="overflow-hidden">
        <UpdateumpingSiteForm site="site"></UpdateumpingSiteForm>
      </v-card>
    </v-container>
    <v-container v-if="!AddJumpingSite" class="container">
      <v-card height="100vh" class="overflow-hidden">
        <v-navigation-drawer class="drawer" v-if="drawer" v-model="drawer" absolute dark>
          <JumpingSitesComponent class="jsC"></JumpingSitesComponent>
        </v-navigation-drawer>
        <GoogleMap class="google-map"></GoogleMap>
      </v-card>
    </v-container>
  </div>
</template>

<script>
// @ is an alias to /src
import AddJumpingSiteForm from "../components/AddJumpingSiteForm";
import JumpingSitesRecommendations from "../components/JumpingSitesRecommendations";
import JumpingSitesComponent from "../components/JumpingSitesComponent";
import GoogleMap from "../components/GoogleMap.vue";
export default {
  data() {
    return {
      drawer: true,
      AddJumpingSite: false
    };
  },
  name: "JumpingSites",

  components: {
    JumpingSitesRecommendations,
    GoogleMap,
    JumpingSitesComponent,
    AddJumpingSiteForm
  },
  methods: {
    openCreateJumpingSite() {
      this.$store.state.dialogJumpingSite = true;
    }
  },
  computed: {
    jumpingSitesArray() {
      return this.$store.getters.getListOfJumpingSites;
    },
    count() {
      return this.$store.getters.getCount;
    },
    isLogged() {
      return this.$store.getters.getToken;
    }
  },
  created() {
    this.$store.dispatch("getJumpingSites");
    this.$store.dispatch("AddMarkers");
  }
};
</script>

<style lang="scss" scoped>
.google-map {
  width: 100vw !important;
  // float: left;
  max-width: none !important;
}
.jumping-site-content {
  width: 100vw;
}
.jsC {
  float: left;
}
.container {
  padding: 0;
  margin: 0;
  max-width: none !important;
}

.toolbar {
  padding: 4px 16px !important;
}

@media screen and (min-width: 992px) {
  .drawer {
    width: 25vw !important;
  }
}
</style>