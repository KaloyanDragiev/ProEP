<template>
  <div>
    <v-card
      class="elevation-1 mx-auto article"
      outlined
      v-for="article in newsArray"
      :key="article.id"
    >
      <v-list-item three-line>
        <v-list-item-content>
          <div class="overline mb-4">[{{article.id}}]</div>
          <v-list-item-title class="headline mb-1">{{article.title}}</v-list-item-title>
          <v-list-item-subtitle>{{article.body}}</v-list-item-subtitle>
        </v-list-item-content>

        <v-list-item-avatar tile size="120" color="grey">
          <v-img
            class="white--text align-end"
            height="200px"
            src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
          ></v-img>
        </v-list-item-avatar>
      </v-list-item>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn
          outlined
          color="primary"
          text
          :to="{ name: 'NewsInfo', params: { id: article.id } }"
        >Read Article</v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>

<script>
export default {
  name: "ArticleItem",
  props: {
    msg: String
  },
  computed: {
    newsArray() {
      return this.$store.getters.getNews;
    }
  }
};
</script>

<style lang="scss" scoped>
.action-button {
  color: red !important;
}
.article {
  margin: 20px auto !important;
}
</style>