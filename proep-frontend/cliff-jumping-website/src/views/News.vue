<template>
  <div>
    <v-container class="xs-12 md-6 xl-4">
      <v-toolbar class="toolbar" flat>
        <h4>News</h4>
        <v-spacer></v-spacer>

        <v-autocomplete
          class="search"
          append-icon="mdi-magnify"
          outlined
          dense
          :items="newsArray"
          :item-text="text"
          item-value="id"
          v-model="articleId"
          @change="navigateToArticle(articleId)"
        ></v-autocomplete>
        <v-btn
          v-if="isAdmin"
          color="primary"
          light
          class="create_button mx-1"
          @click="openCreateArticle()"
        >
          <v-icon>mdi-plus</v-icon>CREATE
        </v-btn>
        <!-- <v-fab-transition>
          <v-btn v-show="!hidden" color="pink" dark absolute bottom right fab>
            <v-icon>mdi-plus</v-icon>
          </v-btn>
        </v-fab-transition>-->
      </v-toolbar>

      <ArticleItem></ArticleItem>
    </v-container>
  </div>
</template>

<script>
import ArticleItem from "../components/ArticleItem.vue";

export default {
  name: "news",
  components: {
    ArticleItem
  },
  data() {
    return {
      articleId: null
    };
  },
  computed: {
    newsArray() {
      return this.$store.getters.getNews;
    },
    isAdmin() {
      if (this.$store.getters.getCurrentUser) {
        return this.$store.getters.getCurrentUser.userType === "Admin";
      } else {
        return null;
      }
    }
  },
  methods: {
    text: item => item.title + " — " + item.body,
    itemId: item => item.id,
    navigateToArticle(id) {
      console.log(id);
      this.$router.push({ name: "NewsInfo", params: { id: id } });
    },
    openCreateArticle() {
      this.$store.state.dialogCreateArticle = true;
    }
  },

  created() {
    this.$store.dispatch("getNews");
  }
};
</script>


<style lang="scss" scoped>
.article {
  width: 60vw;
  max-width: 1200px !important;
}

.toolbar {
  align-items: end;
}
.search {
  align-self: end !important;
}
.create_button {
  margin-bottom: 15px;
}
</style>