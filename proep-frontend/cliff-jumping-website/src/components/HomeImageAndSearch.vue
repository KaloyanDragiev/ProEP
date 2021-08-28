<template>
  <div>
    <snackbarLaningPage v-if="getCurrentUser && getRouteMessage" :message="getRouteMessage"></snackbarLaningPage>
    <!-- <header>
      <h1>Jump into the fun now!</h1>
      <h2>Search for a jumping site nearby..</h2>
     
    </header>-->

    <!--<div class="input-container">
      <v-toolbar>
        <v-autocomplete
          v-model="select"
          :loading="loading"
          :items="items"
          :search-input.sync="search"
          cache-items
          class="mx-4"
          hide-no-data
          hide-details
          placeholder="Where do you want to jump?"
          solo-inverted
        ></v-autocomplete>
        <v-btn icon>
          <v-icon>mdi-dots-vertical</v-icon>
        </v-btn>
      </v-toolbar>
    </div>-->
    <v-content>
      <section>
        <v-img gradient="to top right, rgba(100,115,201,.33), rgba(25,32,72,.7)" height="600">
          <v-parallax :src="images.link" class="dimmer" height="600" color="transperant">
            <v-layout column align-center justify-center class="white--text">
              <h1
                class="white--text mb-2 display-1 text-xs-center"
                style="font-weight: 900; text-shadow: 3px 2px #000000"
              >Cliffing.com</h1>
              <div
                class="white--text subheading mb-3 text-xs-center"
                style="font-weight: 900; text-shadow: 2px 2px #000000"
              >The place for every Cliffer</div>
              <v-btn
                class="blue lighten-2 mt-5"
                color="primary"
                dark
                large
                to="/jumping-sites"
              >Get Started</v-btn>
            </v-layout>
          </v-parallax>
        </v-img>
      </section>

      <section>
        <v-layout column wrap class="my-5" align-center>
          <!-- <v-flex xs12 sm4 class="my-3">
            <div class="text-xs-center">
              <h2 class="headline">Cliffing.com</h2>
              <span class="subheading">No more restrictions, no more limits</span>
            </div>
          </v-flex>-->
          <v-flex xs12>
            <v-container grid-list-xl>
              <v-layout row wrap align-center>
                <v-flex xs12 md4>
                  <v-card class="elevation-0 transparent">
                    <v-card-text class="text-xs-center">
                      <v-icon x-large class="blue--text text--lighten-2">mdi-map-search-outline</v-icon>
                    </v-card-text>
                    <v-card-title primary-title class="layout justify-center">
                      <div class="headline text-xs-center">Find a great place to jump</div>
                    </v-card-title>
                    <v-card-text>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</v-card-text>
                  </v-card>
                </v-flex>
                <v-flex xs12 md4>
                  <v-card class="elevation-0 transparent">
                    <v-card-text class="text-xs-center">
                      <v-icon
                        x-large
                        class="blue--text text--lighten-2"
                      >mdi-account-multiple-plus-outline</v-icon>
                    </v-card-text>
                    <v-card-title primary-title class="layout justify-center">
                      <div class="headline">Enroll into breathtaking events</div>
                    </v-card-title>
                    <v-card-text>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</v-card-text>
                  </v-card>
                </v-flex>
                <v-flex xs12 md4>
                  <v-card class="elevation-0 transparent">
                    <v-card-text class="text-xs-center">
                      <v-icon
                        x-large
                        class="blue--text text--lighten-2"
                      >mdi-newspaper-variant-multiple</v-icon>
                    </v-card-text>
                    <v-card-title primary-title class="layout justify-center">
                      <div
                        class="headline text-xs-center"
                      >Read the latest news in the cliffing community</div>
                    </v-card-title>
                    <v-card-text>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</v-card-text>
                  </v-card>
                </v-flex>
              </v-layout>
            </v-container>
          </v-flex>
        </v-layout>
      </section>

      <section>
        <v-parallax :src="images.groupDiving" height="380">
          <v-layout column align-center justify-center>
            <div
              class="headline white--text mb-3 text-xs-center"
            >Cliffing is an online community with the purpose to unite cliffers in one place</div>
            <em>Our mission is to make cliff jumping available for wider audience</em>
            <v-btn
              class="blue lighten-2 mt-5"
              color="primary"
              dark
              large
              to="/about-us"
            >Get more info</v-btn>
          </v-layout>
        </v-parallax>
      </section>

      <section>
        <v-container grid-list-md>
          <v-layout row wrap>
            <v-flex xs12 text-xs-center class="mt-5">
              <div class="headline">Are you amazed? Stay tuned!</div>
              <br />
              <div>We are lunching the beta in a few time. If you want to be one of the first Endorfine users we will email you as soon as we're ready. In the beginning only few people will test before the launch. Let us know how Endorfine will help you!</div>
            </v-flex>
            <v-flex xs8 offset-xs2>
              <v-card class="elevation-0 transparent">
                <v-card-text>
                  <v-flex xs12 v-if="!subscribed">
                    <v-text-field
                      box
                      label="Email address"
                      :rules="emailRules"
                      v-model="email"
                      hint="Enter your email!"
                      persistent-hint
                    ></v-text-field>
                  </v-flex>
                  <v-flex xs12 v-if="!subscribed">
                    <v-text-field box multi-line label="Bio and curiosities"></v-text-field>
                  </v-flex>
                  <v-flex xs12 class="text-xs-center" v-if="!subscribed">
                    <v-btn
                      class="blue lighten-2 mb-5"
                      color="primary"
                      large
                      @click="subscribe"
                    >Get in touch</v-btn>
                  </v-flex>
                  <v-flex xs12 class="text-xs-center" v-if="subscribed">
                    <v-btn class="green lighten-2 mb-5" color="primary" large>Welcome on board!</v-btn>
                  </v-flex>
                </v-card-text>
              </v-card>
            </v-flex>
          </v-layout>
        </v-container>
      </section>

      <section>
        <v-parallax :src="images.social" height="380">
          <v-layout column align-center justify-center>
            <div
              class="headline white--text mb-3 text-xs-center"
            >We are dropping cool news and opportunities on socials</div>
          </v-layout>
          <v-layout justify-space-around justify-center>
            <v-btn color>
              <v-icon x-large dark>mdi-facebook</v-icon>
            </v-btn>

            <v-btn color>
              <v-icon x-large dark>mdi-twitter</v-icon>
            </v-btn>

            <v-btn color>
              <v-icon x-large dark>mdi-reddit</v-icon>
            </v-btn>

            <v-btn color>
              <v-icon x-large dark>mdi-instagram</v-icon>
            </v-btn>
            <v-btn color>
              <v-icon x-large dark>mdi-discord</v-icon>
            </v-btn>
          </v-layout>
        </v-parallax>
      </section>
    </v-content>
  </div>
</template>

<script>
import snackbarLaningPage from "../components/snackbarLandingPage";
export default {
  name: "HomeImageAndSearch",
  props: {
    msg: String
  },
  components: { snackbarLaningPage },
  data() {
    return {
      loading: false,
      items: [],
      search: null,
      select: null,

      images: {
        groupDiving:
          "http://therunwayjournal.com/wp-content/uploads/2014/10/skylars-cliff-jumping-1001.jpg",
        link:
          "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fimage.redbull.com%2Frbcom%2F010%2F2014-08-13%2F1331671020326_4%2F0100%2F0%2F1%2Fcliff-diving-italy.jpg&f=1&nofb=1",
        social:
          "https://images.pexels.com/photos/1974927/pexels-photo-1974927.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260",
        logo:
          "https://files.123freevectors.com/wp-content/uploads/new/people/261-cliff-diver-free-vector-illustration-l.png"
      }
    };
  },
  created() {
    console.log(this.$route.query.message);
    this.$store.state.snackbarHome = true;
  },
  computed: {
    jumpingSitesArray() {
      return this.$store.getters.getListOfJumpingSites;
    },
    getRouteMessage() {
      return this.$route.query.message;
    },
    getCurrentUser() {
      return this.$store.getters.getCurrentUser;
    }
  },
  watch: {
    search(val) {
      val && val !== this.select && this.querySelections(val);
    }
  },
  methods: {
    querySelections(v) {
      this.loading = true;
      // Simulated ajax query
      setTimeout(() => {
        this.items = this.jumpingSitesArray.filter(e => {
          return (e || "").toLowerCase().indexOf((v || "").toLowerCase()) > -1;
        });
        this.loading = false;
      }, 500);
    }
  }
};
</script>

<style lang="scss" scoped>
img {
  filter: brightness(60%);
  width: 100%;
}
.dimmer {
}
.image-container {
  background: linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5)),
    url("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fimage.redbull.com%2Frbcom%2F010%2F2014-08-13%2F1331671020326_4%2F0100%2F0%2F1%2Fcliff-diving-italy.jpg&f=1&nofb=1");
  background-size: cover;
  width: 100vw;
  height: calc(100vh - 103px);
  background-position: center; /* Center the image */
  background-repeat: no-repeat; /* Do not repeat the image */
  margin: auto;
  position: relative;
  header {
    position: absolute;
    top: 30%;
    left: 50%;
    transform: translate(-50%, -50%);
    color: white;
    text-align: center;
  }
  h1 {
    text-transform: uppercase;
    margin: 0;
    font-size: 4rem;
  }
  p {
    margin: 0;
    font-size: 1.5rem;
  }
}

.input-container {
  position: absolute;
  width: 80%;

  text-align: -webkit-center;
  top: 45%;
  left: 25%;
  // margin: 0 auto;
}
.v-autocomplete {
  max-width: 800px;
  z-index: 10;
}
</style>
