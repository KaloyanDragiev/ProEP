<template>
  <v-container class="container xs-2 md-2 xl-2">
    <v-toolbar flat>
      <v-btn color="secondary" @click="back()">
        <v-icon>mdi-arrow-left</v-icon>Back
      </v-btn>
    </v-toolbar>

    <!-- <img :src="image" alt />
      <h1>{{article.id}}</h1>
    <h2>{{article.title}}</h2>-->

    <v-parallax :src="image" class="dimmer" height="600" color="transperant">
      <v-layout column align-left justify-end class="white--text">
        <h1
          class="white--text mb-2 display-1 text-xs-center"
          style="font-weight: 900; text-shadow: 3px 2px #000000"
        >{{article.title}}</h1>
        <!-- <div
            class="white--text subheading mb-3 text-xs-center"
            style="font-weight: 900; text-shadow: 2px 2px #000000"
        >{{article.title}}</div>-->
        <!-- <v-btn
            class="blue lighten-2 mt-5"
            color="primary"
            dark
            large
            to="/jumping-sites"
        >Get Started</v-btn>-->
      </v-layout>
    </v-parallax>

    <p>{{article.body}}</p>
  </v-container>
</template>

<script>
// import ArticleItem from "../components/ArticleItem.vue";

export default {
  name: "news",
  components: {},

  data() {
    return {
      image:
        "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fi.ytimg.com%2Fvi%2F9Hw0oloUxnA%2Fmaxresdefault.jpg&f=1&nofb=1"
    };
  },
  computed: {
    newsArray() {
      return this.$store.getters.getNews;
    },
    article() {
      let idArticle = this.$route.params.id;
      console.log("newsArray", this.newsArray);
      console.log("idarticle", idArticle);
      let article = this.newsArray.filter(el => el.id == idArticle);
      console.log(article);
      return article[0];
    }
  },
  methods: {
    text: item => item.title + " — " + item.body,
    back() {
      this.$router.go(-1);
    },

    getSelectedArticle(idArticle) {
      console.log(this.$store.getters.getSingleNews(idArticle));
      return this.$store.getters.getSingleNews(idArticle);
    }
  },

  created() {
    this.$store.dispatch("getNews");
  }
};
</script>


<style lang="scss" scoped>
.container {
  max-width: 1200px;
}
</style>