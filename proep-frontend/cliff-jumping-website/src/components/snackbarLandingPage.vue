<template>
  <div class="text-center ma-2">
    <!-- <v-btn dark @click="snackbar = true">Open Snackbar</v-btn> -->
    <v-snackbar class="snackbar" color="error" v-model="getState" multi-line top light absolute>
      <b>{{ message }}</b>
      <v-btn color="white" text @click="close()">Close</v-btn>
    </v-snackbar>
  </div>
</template>

<script>
export default {
  data: () => ({
    snackbar: false,
    text: "Hello, I'm a snackbar"
  }),
  props: ["message"],
  computed: {
    getState() {
      return this.$store.state.snackbarHome;
    }
  },
  methods: {
    close() {
      this.$store.state.snackbarHome = false;
    }
  }
};
</script>
<style lang="scss" scoped>
.snackbar {
  //   color: red !important;
}
</style>