<template>
  <div id="app-instasearch">
    <div class="input-container">
      <input type="text" placeholder="Type a name of jumping side" v-model="jumpingSiteSearch" />
    </div>
    <div class="jumping-sites">
      <v-expansion-panels>
        <v-expansion-panel v-for="site in filteredPhotoFeed" :key="site.id">
          <v-expansion-panel-header @click="getLogs(site)">{{site.name}}</v-expansion-panel-header>

          <v-expansion-panel-content>
            <v-btn
              class="red button"
              light
              v-if="userId === site.userId || isAdmin "
              @click="deleteJumpingSite(site)"
            >
              <v-icon dark>mdi-trash-can-outline</v-icon>DELETE
            </v-btn>
            <v-btn
              color="secondary"
              light
              v-if=" userId === site.userId || isAdmin "
              @click="updateJumpingSite(site)"
            >
              <v-icon dark>mdi-table-edit</v-icon>UPDATE
            </v-btn>

            <v-img :src="site.image" aspect-ratio="2"></v-img>
            <br />
            <b>Difficulty Level:</b>
            {{site.difficultyLevel}}
            <br />
            <b>Coordinates:</b>
            {{site.coordinates}}
            <br />
            <b>Height:</b>
            {{site.height}}
            <br />
            <b>Rating:</b>
            {{site.rate}}
            <br />
            <b>Posted by:</b>
            {{site.userId}}
            <br />

            <!-- <v-btn @click="getLogs(site)">Get Logs</v-btn> -->
            <div data-app>
              <v-row justify="center">
                <v-dialog v-model="dialog" persistent max-width="1000px">
                  <template v-slot:activator="{ on }">
                    <v-btn v-if="userId != -1 " color="primary" dark v-on="on">
                      <v-icon dark>mdi-plus</v-icon>Log
                    </v-btn>
                  </template>

                  <!-- Log a jump section -->
                  <v-card>
                    <v-card-title>
                      <span class="headline">Log your jump</span>
                    </v-card-title>
                    <v-card-text>
                      <input type="text" v-model="logDescription" />
                      Rate the Jump 1-5 stars
                      <v-container>
                        <v-row>
                          <v-rating v-model="rating"></v-rating>
                        </v-row>
                        <v-row>
                          <v-textarea
                            solo
                            name="input-7-4"
                            label="Leave a comment"
                            v-model="logComment"
                          ></v-textarea>
                        </v-row>
                      </v-container>
                      <small>both fields are required</small>
                    </v-card-text>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn color="blue darken-1" text @click="dialog = false">Close</v-btn>
                      <v-btn color="blue darken-1" text @click="addLog(site); dialog = false">Save</v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </v-row>
            </div>
            <v-divider class="mx-4" horizontal></v-divider>
            <v-card v-for="log in logsForSite" :key="log.logId" class="log_card" light>
              <v-list-item three-line>
                <v-list-item-content>
                  <div class="overline mb-4"></div>
                  <v-list-item-title class="headline mb-1">{{log.description}}</v-list-item-title>
                </v-list-item-content>

                <v-list-item-avatar tile size="80" color="grey">Here User Avatar</v-list-item-avatar>
              </v-list-item>
              <v-rating readonly v-model="log.rating"></v-rating>
              <v-card-actions>
                <v-btn
                  outlined
                  class="button"
                  light
                  v-if="userId === site.userId || isAdmin "
                  @click="deleteLog(log)"
                >
                  <v-icon dark>mdi-trash-can-outline</v-icon>
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-expansion-panel-content>
        </v-expansion-panel>
      </v-expansion-panels>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import Vue from "vue";
import Vuex from "vuex";
import VueAxios from "vue-axios";
import { Log } from "../interfaces/Log";
// import { JumpingSide } from "../interfaces/jumpingSite";

Vue.use(Vuex);
Vue.use(VueAxios, axios);
export default {
  name: "App",
  components: {},
  data: () => ({
    dialog: false,
    rating: 0,
    jumpingSiteSearch: "",
    getJumpingSitesArray: [],
    logComment: "",
    logDescription: "",
    logsForSite: null,
    UpdateJumpingSite: false
  }),
  methods: {
    open() {
      this.$store.state.dialogJumpingSite = true;
    },
    deleteJumpingSite(site) {
      console.log(site);
      axios.delete(`/jumpingsites/delete/web/${site.id}`).then(res => {
        console.log(res);

        let index = null;
        let i = 0;
        this.filteredPhotoFeed.map(el => {
          if (el.id === site.id) {
            index = i;
          } else {
            i++;
          }
        });
        if (index) {
          this.filteredPhotoFeed.splice(index, 1);
        }
        // this.filteredPhotoFeed.pop(site.id);
      });
    },

    updateJumpingSite(site) {
      this.$store.commit("currentSite", site);
      this.$store.state.dialogUpdateJumpingSite = true;
      console.log(site);
      console.log(this.$store.getters.getCurrentSite);
    },
    addLog(site) {
      let vm = this;
      console.log(this.rating);
      axios
        .post(`/jumpingsites/${site.id}/logs/web`, {
          logDescription: vm.logComment,
          rating: vm.rating,
          idUser: vm.$store.getters.getCurrentUser.idUser
        })
        .then(res => {
          console.log(res);
          vm.logsForSite.push(
            new Log(
              res.data[0].logId,
              res.data[0].logDescription,
              res.data[0].rating
            )
          );
        });
    },
    getLogs(site) {
      let returnArray = [];
      let vm = this;

      axios.get(`/jumpingsites/${site.id}/logs`).then(res => {
        //Check if logs are present
        if (!(res.data.length > 0)) {
          vm.logsForSite = [];
          // If not -> return
          return;
        }
        res.data.map(el => {
          returnArray.push(new Log(el.logId, el.logDescription, el.rating));

          vm.logsForSite = returnArray;
        });
      });
    }
  },

  computed: {
    logs() {
      return this.logsForSite;
    },
    jumpingSitesArray() {
      return this.$store.getters.getListOfJumpingSites;
    },
    filteredPhotoFeed: function() {
      let jumpingSites = this.jumpingSitesArray;
      console.log(jumpingSites);
      let jumpingSiteSearch = this.jumpingSiteSearch;

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
    },

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
    }
  }
};
</script>
<style scoped>
.log_card {
  margin: 15px;
}
.red {
  background: red !important;
  color: white;
}
.jumping-sites {
  width: 25vw;
}
#app-instasearch {
  margin-right: 0;
}
.photo {
  list-style: none;
  display: grid;
  grid-template-columns: 200px auto;
  margin-top: 20px;
  align-items: center;
  background-color: #e9edf0;
  padding: 30px 50px;
  border-radius: 5px;
  border: 1px solid #e3e3e3;
}

.author {
  font-size: 25px;
  margin-left: 20px;
  color: #75818e;
}

.photo img {
  border-radius: 5px;
  width: 200px;
}

.input-container {
  border-radius: 5px;
  background: #677482;
  padding: 10px;
  max-width: 50vw;
}

.input-container input {
  border: none;
  background: transparent;
  color: white;
  padding: 6px 15px;
  width: 33vw;
  font-size: 18px;
}

::placeholder {
  /* Chrome, Firefox, Opera, Safari 10.1+ */
  color: #a6b0ba;
  opacity: 1; /* Firefox */
}
.orange {
  background: goldenrod;
}

.button {
  margin: 16px !important;
  margin-left: 0px !important;
}

@media screen and (max-width: 992px) {
  .jumping-sites {
    width: 100%;
  }
}
</style>