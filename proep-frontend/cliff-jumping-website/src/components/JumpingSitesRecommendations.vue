<template>
  <div class="firstPart">
    <div class="first-section">
      <div class="search-toolbar">
        <v-toolbar color="orange accent-1">
          <v-app-bar-nav-icon class="hidden-sm-and-down"></v-app-bar-nav-icon>
          <v-autocomplete
            :items="jumpingSitesArray"
            chips
            clearable
            hide-details
            hide-selected
            item-text="name"
            item-value="symbol"
            label="Search for a jumping site..."
            solo
          >
            <template v-slot:no-data v-for="item in jumpingSitesArray">
              <v-list-item :key="item.id">
                <v-list-item-title>
                  Search for your favorite
                  <strong>Cryptocurrency</strong>
                </v-list-item-title>
              </v-list-item>
            </template>
            <template v-slot:selection="{ attr, on, item, selected }">
              <v-chip
                v-bind="attr"
                :input-value="selected"
                color="blue-grey"
                class="white--text"
                v-on="on"
              >
                <v-icon left>mdi-coin</v-icon>
                <span v-text="item.name"></span>
              </v-chip>
            </template>
            <template v-slot:item="{ item }">
              <v-list-item-avatar
                color="indigo"
                class="headline font-weight-light white--text"
              >{{ item.name }}</v-list-item-avatar>
              <v-list-item-content>
                <v-list-item-title v-text="item.name"></v-list-item-title>
                <v-list-item-subtitle v-text="item.symbol"></v-list-item-subtitle>
              </v-list-item-content>
              <v-list-item-action>
                <v-icon>mdi-coin</v-icon>
              </v-list-item-action>
            </template>
          </v-autocomplete>
        </v-toolbar>
      </div>
      <div class="recommendations-list">
        <v-card class="mx-auto">
          <v-toolbar color="pink" dark>
            <v-toolbar-title>Recommendations</v-toolbar-title>
          </v-toolbar>

          <v-list two-line>
            <v-list-item-group v-model="selected" multiple active-class="pink--text">
              <template v-for="(item, index) in jumpingSitesArray">
                <v-list-item :key="item.title">
                  <template v-slot:default="{ active }">
                    <v-list-item-content>
                      <v-list-item-title v-text="item.id"></v-list-item-title>
                      <v-list-item-subtitle class="text--primary" v-text="item.headline"></v-list-item-subtitle>
                      <v-list-item-subtitle v-text="item.name"></v-list-item-subtitle>
                    </v-list-item-content>

                    <v-list-item-action>
                      <v-list-item-action-text v-text="item.action"></v-list-item-action-text>
                      <v-icon v-if="!active" color="grey lighten-1">star_border</v-icon>

                      <v-icon v-else color="yellow">star</v-icon>
                    </v-list-item-action>
                  </template>
                </v-list-item>

                <v-divider v-if="index + 1 < items.length" :key="index"></v-divider>
              </template>
            </v-list-item-group>
          </v-list>
        </v-card>
      </div>
      <!--
       <div class="jumping-sites-map"></div>
      -->
    </div>
    <div class="second-section"></div>
  </div>
</template>

<script>
export default {
  name: "JumpingSitesComponent",
  methods: {
    search() {}
  },
  computed: {
    jumpingSitesArray() {
      return this.$store.getters.getListOfJumpingSites;
    },
    count() {
      return this.$store.getters.getCount;
    }
  },
  props: {
    msg: String
  },
  data: () => ({
    selected: [2],
    items: [
      {
        action: "Intermediate level",
        title: "Varna, Tyulenovo",
        subtitle: "Jumping site description"
      },
      {
        action: "Intermediate level",
        title: "Location 2",
        subtitle: "Jumping site description"
      },
      {
        action: "Intermediate level",
        title: "Location 3",
        subtitle: "Jumping site description"
      },
      {
        action: "Intermediate level",
        title: "Location 4",
        subtitle: "Jumping site description"
      },
      {
        action: "Intermediate level",
        title: "Location 5",
        subtitle: "Jumping site description"
      }
    ]
  })
};
</script>

<style lang="scss" scoped>
.first-section {
  display: inline;
}
.firstPart {
  width: 100%;
}
.search-toolbar {
  margin-right: 0%;
}
.recommendations-list {
  margin-right: 0%;
}
/*
.jumping-sites-map {
  display: block;
  background-image: url('../assets/google-maps-example.png');
    width: 70%;

  background-size: cover;
height: 500px;
}
*/
</style>