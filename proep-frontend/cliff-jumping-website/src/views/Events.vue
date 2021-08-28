<template>
  <div class="about">
    <v-toolbar class="toolbar" flat dense>
      <v-btn @click.stop="drawer = !drawer" icon>
        <v-icon>mdi-format-list-bulleted-square</v-icon>
      </v-btn>
      <v-toolbar-title>Events</v-toolbar-title>
      <v-spacer></v-spacer>

      <v-btn color="primary" light class="mx-1" @click="openCreateEvents()">
        <v-icon>mdi-plus</v-icon>CREATE
      </v-btn>
    </v-toolbar>
    <v-col>
      <v-row></v-row>
    </v-col>
    <v-container v-if="AddEventForm">
      <v-card height="100vh" class="overflow-hidden">
        <AddEventForm></AddEventForm>
      </v-card>
    </v-container>
    <v-container v-if="!AddEvent" class="container">
      <v-card height="100vh" class="overflow-hidden">
        <v-navigation-drawer v-if="drawer" v-model="drawer" dark absolute light width="25vw">
          <span class="events" v-for="event of eventsArray" :key="event.id">{{event.title}}</span>
          <JoinedEventsList class="joined-events-component" v-if="registered"></JoinedEventsList>
        </v-navigation-drawer>
        <GoogleMapEvents class="google-map"></GoogleMapEvents>
      </v-card>
    </v-container>
  </div>
</template>

<script>
import AddEventForm from "../components/AddEventForm";
import JoinedEventsList from "../components/JoinedEventsList.vue";
// import GoogleMap from "../components/GoogleMap.vue";
import GoogleMapEvents from "../components/GoogleMapEvents.vue";
export default {
  name: "EventsPage",
  components: {
    AddEventForm,
    JoinedEventsList,
    GoogleMapEvents
  },
  data() {
    return {
      drawer: true,
      registered: true,
      AddEvent: false
    };
  },
  created() {
    this.$store.dispatch("getEvents");
  },
  computed: {
    eventsArray() {
      return this.$store.getters.getEvents;
    }
  },
  methods: {
    openCreateEvents() {
      this.$store.state.dialogCreateEvent = true;
    }
  }
};
</script>

<style lang="scss" scoped>
.joined-events-component {
  width: 80vw !important;
}
.about {
  width: 100vw;
}
.events {
  width: 20vw;
  float: left;
  margin-right: 0%;
}

.google-map {
  width: 100%;
  float: left;
  max-width: none !important;
}
.jumping-site-content {
  width: 100vw;
}
.jsC {
  // width: 20vw;
  //margin-right: 0%;
  float: left;
}

.google-map {
  width: 100vw;
  float: left;
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
</style>