import Vue from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify';
import Vuetify from 'vuetify';
import 'es6-promise/auto'
import {
  store
} from './store/store.js'
import Vuex from 'vuex';
import {
  JumpingSide
} from "./interfaces/jumpingSite";

Vue.use(Vuex);
Vue.config.productionTip = false
Vue.use(Vuetify);
new Vue({

  router,
  vuetify,
  store,
  JumpingSide,
  render: h => h(App),
  computed: {
    count() {
      return this.$store.state.count
    }
  }
}).$mount('#app')