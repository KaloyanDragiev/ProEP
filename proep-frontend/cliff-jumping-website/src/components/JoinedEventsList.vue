<template>
  <v-card class="mx-auto" tile>
    <v-toolbar dark flat>
      <div class="input-container">
        <v-text-field
          type="text"
          outlined
          placeholder="Type a name of an Event you know"
          v-model="eventSearch"
          prepend-icon="mdi-magnify"
        ></v-text-field>
      </div>
    </v-toolbar>
    <!-- <h1>User Id {{userId}}</h1> -->
    <v-expansion-panels>
      <v-expansion-panel v-for="event in filteredPhotoFeed" :key="event.id">
        <v-expansion-panel-header>{{event.name}}</v-expansion-panel-header>

        <v-expansion-panel-content>
          <v-btn
            color="error"
            light
            v-if="userId == event.idUser || isAdmin "
            @click="deleteEvent(event)"
          >
            <v-icon dark>mdi-trash-can-outline</v-icon>DELETE
          </v-btn>
          <v-btn
            color="secondary"
            light
            v-if="userId == event.idUser || isAdmin "
            @click="updateJumpingSite(event)"
          >
            <v-icon dark>mdi-table-edit</v-icon>UPDATE
          </v-btn>

          <v-btn color="primary" light v-if="userId !== -1" @click="deleteJumpingSite(event)">
            <v-icon dark>mdi-playlist-plus</v-icon>SUBSCRIBE
          </v-btn>

          <v-img :src="event.image" aspect-ratio="2"></v-img>
          <br />
          <b>Difficulty Level:</b>
          {{event.name}}
          <br />
          <b>Coordinates:</b>
          {{event.date}}
          <br />
          <b>Description:</b>
          {{event.description}}
          <br />
          <b>Created by:</b>
          {{event.idUser}}
          <br />

          <!-- <b>{{userId === event.userId}}</b> -->
          <v-divider class="mx-4" horizontal></v-divider>
          <h4>Infromation for Jumpingsite</h4>
          <div v-for="site in JumpingSites" :key="site.id">
            <div v-if="site.id === event.jumpingSiteId">
              <b>Difficulty Level:</b>
              {{site.difficultyLevel}}
              <br />
              <b>Coordinates:</b>
              {{site.height}}
              <br />
              <b>Height:</b>
              {{site.coordinates}}
              <br />
              <b>Rating:</b>
              {{site.rate}}
              <br />
              <b>Posted by:</b>
              {{site.userId}}
              <br />
            </div>
          </div>
        </v-expansion-panel-content>
      </v-expansion-panel>
    </v-expansion-panels>
  </v-card>
</template>

<script>
import axios from "axios";
export default {
  name: "JoinedEventsList",
  data: () => ({
    eventSearch: ""
  }),
  props: {
    msg: String
  },
  computed: {
    userId() {
      if (this.$store.getters.getCurrentUser) {
        return this.$store.getters.getCurrentUser.idUser;
      } else {
        return -1;
      }
    },
    isAdmin() {
      if (this.$store.getters.getCurrentUser) {
        return this.$store.getters.getCurrentUser.userType === "Admin";
      } else {
        return null;
      }
    },
    eventsArray() {
      return this.$store.getters.getEvents;
    },
    JumpingSites() {
      return this.$store.getters.getListOfJumpingSites;
    },
    filteredPhotoFeed: function() {
      let jumpingSites = this.eventsArray;
      console.log(jumpingSites);
      let jumpingSiteSearch = this.eventSearch;

      if (!jumpingSiteSearch) {
        return jumpingSites;
      }

      jumpingSiteSearch = jumpingSiteSearch.trim().toLowerCase();

      jumpingSites = jumpingSites.filter(function(item) {
        if (item.name.toLowerCase().indexOf(jumpingSiteSearch) !== -1) {
          return item;
        }
      });
      console.log(jumpingSites);
      return jumpingSites;
    }
  },
  methods: {
    deleteEvent(event) {
      console.log(event);
      axios.delete(`/events/delete/${event.id}`).then(res => {
        console.log(res);

        let index = null;
        let i = 0;
        this.eventsArray.map(el => {
          if (el.id === event.id) {
            index = i;
          } else {
            i++;
          }
        });
        if (index) {
          this.eventsArray.splice(index, 1);
        }
        // this.filteredPhotoFeed.pop(site.id);
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.white {
  background: white;
  color: white;
  border: 2px solid white;
}

.input-container {
  width: 100%;
  margin-top: 30px;
}
.input-container input {
  border: 1px white solid !important;

  background: transparent;
  color: white;

  width: 100%;
  font-size: 18px;
}
</style>
