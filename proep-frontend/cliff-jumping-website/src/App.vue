<template>
  <div id="app">
    <v-app>
      <dialogCreateJumpingSite />
      <dialogUpdateJumpingSite />
      <dialogCreateEvent />
      <dialogCreateArticle />
      <v-app-bar class="app_bar" color="white">
        <!-- -->

        <router-link class="nav_links" to="/">Home</router-link>

        <router-link class="nav_links" to="/jumping-sites">Jumping Sites</router-link>
        <router-link class="nav_links" to="/events">Events</router-link>
        <router-link class="nav_links" to="/news">News</router-link>
        <router-link class="nav_links" to="/about-us">About Us</router-link>

        <v-spacer v-if="!currentUser"></v-spacer>
        <v-btn raised v-if="!currentUser" color="error" class="sign_in" to="/sign-in">
          <v-icon>mdi-login-variant</v-icon>Sign In
        </v-btn>
        <v-spacer v-if="currentUser"></v-spacer>

        <div class="text-center">
          <v-menu offset-y>
            <template v-slot:activator="{ on, attrs }">
              <!-- <v-btn color="primary" dark v-bind="attrs" v-on="on">Dropdown</v-btn> -->
              <v-card hover class="avatar" flat color="primary" v-on="on" v-bind="attrs">
                <v-avatar
                  v-if="currentUser"
                  class="white--text"
                  color="primary"
                  size="46"
                >{{ currentUserInitials }}</v-avatar>
              </v-card>
            </template>
            <v-list>
              <v-list-item v-for="(item, index) in items" :key="index" @click="call(item.method)">
                <v-list-item-title :to="item.link">
                  <router-link :to="item.link">
                    <v-icon>{{item.icon}}</v-icon>
                    {{ item.title }}
                  </router-link>
                </v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
        </div>
      </v-app-bar>

      <v-main class="main">
        <!-- Provides the application the proper gutter -->

        <!-- If using vue-router -->
        <router-view></router-view>
      </v-main>
      <!-- <router-view /> -->
    </v-app>
  </div>
</template>
<script>
import dialogCreateJumpingSite from "./components/dialogCreateJumpingSite";
import dialogUpdateJumpingSite from "./components/dialogUpdateJumpingSite";
import dialogCreateEvent from "./components/dialogCreateEvent";
import dialogCreateArticle from "./components/dialogCreateArticle";
import { User } from "./interfaces/User";
export default {
  name: "",
  created() {
    this.$store.dispatch("getJumpingSites");
    this.$store.dispatch("AddMarkers");
  },
  mounted() {
    var retrievedObject = localStorage.getItem("user");
    let vm = this;
    console.log("retrievedObject: ", JSON.parse(retrievedObject));
    if (retrievedObject) {
      let parsed = JSON.parse(retrievedObject);
      let user = new User(
        parsed.idUser,
        parsed.email,
        parsed.city,
        parsed.userType,
        parsed.experienceLevel,
        parsed.firstName,
        parsed.lastName,
        parsed.country
      );
      console.log("User initializedf", user);
      vm.$store.commit("changeCurrentUser", user);
    }
  },
  data: () => ({
    items: [
      { title: "Profile", link: "/profile", icon: "mdi-account" },
      { title: "Logout", link: "logout", icon: "mdi-logout", method: "logout" }
    ]
  }),
  components: {
    dialogCreateJumpingSite,
    dialogUpdateJumpingSite,
    dialogCreateEvent,
    dialogCreateArticle
  },
  computed: {
    currentUser() {
      return this.$store.getters.getCurrentUser;
    },
    currentUserInitials() {
      return this.$store.getters.getCurrentUserInitials;
    }
  },
  methods: {
    logout() {
      this.$store.state.currentUser = null;
      this.$router.push("sign-in");
      localStorage.setItem("user", null);
    },
    call(method, args = []) {
      this[method](...args);
    }
  }
};
</script>

<style lang="scss">
html {
  height: 100vh;
  background: whitesmoke;
}
body {
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;

  color: #2c3e50;
}

#nav {
  // text-align: center;
  // padding: 20px;
  // a {
  //   font-weight: bold;
  //   color: #2c3e50;
  //   padding: 20px;
  //   text-decoration: none;

  //   &.router-link-exact-active {
  //     color: #42b983;
  //   }

  //   &:hover {
  //     color: #42b983;
  //   }
  // }
  // img {
  //   width: 55px;
  // }
}

.nav-links {
  font-size: 100%;

  &:hover {
    text-decoration: none;
  }
}
.green {
  background: #42b983 !important;
  color: white !important;
  font-weight: bold;
}

.main {
  width: 100vw;

  max-width: none;
}
.avatar {
  cursor: pointer;
  text-decoration: none;
  &:hover {
  }
}
a {
  text-decoration: none;
  &:hover {
    text-decoration: none;
  }
}
.nav_links {
  margin: 10px;
  padding: 10px;

  &:hover {
    background: whitesmoke;
    border-radius: 20%;
  }
}
.v-toolbar {
  // Pay attention to that
  flex: none !important;
}
.app_bar {
  z-index: 10;
}

@media screen and (max-width: 720px) {
  .app_bar {
    height: auto !important;
    display: grid;
    position: fixed;
  }

  .v-toolbar__content {
    display: grid;
  }
  .nav_links {
    margin: 0px;
    padding: 0px;
    display: block !important;
  }
}
</style>
