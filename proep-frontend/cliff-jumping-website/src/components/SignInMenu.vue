<template>
  <div>
    <div v-if="NeedRegistration" class="image-container">
      <img src="../assets/OfficialLogo.png" alt id="image" />
      <p id="login-text">Welcome Back</p>
      <p id="text-sign">Sign to your account & Let the fun begins!</p>
      <div class="input-container">
        <v-card raised class="card-wrapper">
          <form>
            <p v-if="err" class="red">Wrong Credentials!</p>
            <v-text-field
              v-model="email"
              :error-messages="emailErrors"
              label="E-mail"
              required
              @input="$v.email.$touch()"
              @blur="$v.email.$touch()"
            ></v-text-field>
            <v-text-field
              v-model="password"
              :error-messages="passwordErrors"
              label="Password"
              :type="'password'"
              required
            ></v-text-field>

            <div class="button_wrapper">
              <v-btn class="mr-4 half_size login" @click="login">Log in</v-btn>
              <v-btn @click="clear" class="half_size">clear</v-btn>
              <br />
              <br />You dont have an account yet?
              <v-btn @click="NeedRegistration = false" class="half_size">Register Now</v-btn>
            </div>
          </form>
        </v-card>
      </div>
    </div>

    <!-- Register -->
    <v-app v-if="!NeedRegistration" class="image-container">
      <p id="login-text">Register an account</p>
      <div class="input-container">
        <v-card raised class="card-wrapper">
          <form>
            <v-text-field v-model="firstName" label="First Name" required></v-text-field>
            <v-text-field v-model="lastName" label="Last Name" required></v-text-field>
            <v-text-field v-model="email" :error-messages="emailErrors" label="E-mail" required></v-text-field>
            <v-text-field v-model="password" label="Password" :type="'password'" required></v-text-field>
            <v-text-field v-model="country" label="Country" required></v-text-field>
            <v-text-field v-model="city" label="City" required></v-text-field>
            <v-select :items="experienceLevels" label="Experience Level" v-model="experienceLevel"></v-select>
            <div class="button_wrapper">
              <v-btn class="mr-4 half_size login" @click="register">Register</v-btn>
              <v-btn @click="NeedRegistration = true" class="half_size">Back</v-btn>
            </div>
          </form>
        </v-card>
      </div>
    </v-app>
  </div>
</template>

<script>
import axios from "axios";
import Vue from "vue";
import Router from "vue-router";
import VueCookies from "vue-cookies";
import { User } from "../interfaces/User";
Vue.use(Router);
Vue.use(VueCookies);

import { validationMixin } from "vuelidate";
import { required, maxLength, email } from "vuelidate/lib/validators";

export default {
  name: "SignInMenu",
  props: {
    msg: String
  },
  mixins: [validationMixin],

  validations: {
    name: { required, maxLength: maxLength(10) },
    email: { required, email },
    select: { required },
    checkbox: {
      checked(val) {
        return val;
      }
    }
  },

  data: () => ({
    NeedRegistration: true,
    name: "",
    email: "",
    select: null,
    items: ["Item 1", "Item 2", "Item 3", "Item 4"],
    checkbox: false,
    firstName: "",
    lastName: "",
    password: "",
    country: "",
    city: "",
    userType: "User",
    experienceLevel: "",
    experienceLevels: ["Beginner", "Intermediate", "Advanced"],
    err: null
  }),

  computed: {
    selectErrors() {
      const errors = [];
      if (!this.$v.select.$dirty) return errors;
      !this.$v.select.required && errors.push("Password is required");
      return errors;
    },

    emailErrors() {
      const errors = [];
      if (!this.$v.email.$dirty) return errors;
      !this.$v.email.email && errors.push("Must be valid e-mail");
      !this.$v.email.required && errors.push("E-mail is required");
      return errors;
    },

    users() {
      return this.$store.getters.getUsers;
    },
    isLogged() {
      return this.$store.getters.getToken;
    }
  },

  methods: {
    login() {
      this.$v.$touch();

      console.log(email);

      var vm = this;
      // this.$store
      //   .dispatch("login", { email: vm.email, password: vm.password })
      //   .then(() => {
      //     this.$router.push("/");
      //   });
      // axios.defaults.withCredentials = true;
      axios
        .post("login", {
          email: this.email,
          password: this.password
        })
        .then(result => {
          console.log(result);
          console.log("TOVA E SET COOKIE: ", result.headers["set-cookie"]);
          let user = new User(
            result.data[0].idUser,
            result.data[0].email,
            result.data[0].city,
            result.data[0].userType,
            result.data[0].experienceLevel,
            result.data[0].firstName,
            result.data[0].lastName,
            result.data[0].country
          );
          // localStorage.user = user;
          localStorage.setItem("user", JSON.stringify(user));
          vm.$store.commit(
            "changeCurrentUser",
            new User(
              result.data[0].idUser,
              result.data[0].email,
              result.data[0].city,
              result.data[0].userType,
              result.data[0].experienceLevel,
              result.data[0].firstName,
              result.data[0].lastName,
              result.data[0].country
            )
          );

          this.$router.push({
            path: "/",
            query: {
              message:
                "Welcome back, " + this.$store.getters.getCurrentUser.firstName
            }
          });
          console.log(this.$store.getters.getCurrentUser);
        })
        .catch(error => {
          throw new Error(`API ${error}`);
        });
    },
    clear() {
      this.$v.$reset();
      this.name = "";
      this.email = "";
      this.password = "";
      this.select = null;
      this.checkbox = false;
      this.err = null;
    },
    register() {
      // axios.defaults.withCredentials = true;

      axios
        .post("register/web", {
          email: this.email,
          firstName: this.firstName,
          lastName: this.lastName,
          password: this.password,
          country: this.country,
          city: this.city,
          experienceLevel: this.experienceLevel
        })
        .then(elemet => {
          this.$store.commit("assignUser", elemet.data);
          console.log(this.$store.getters.getCurrentUser);
          this.$router.push("/");
        });

      // this.$store
      //   .dispatch("register", {
      //     email: this.email,
      //     firstName: this.firstName,
      //     lastName: this.lastName,
      //     password: this.password,
      //     country: this.country,
      //     city: this.city,
      //     experienceLevel: this.experienceLevel
      //   })
      //   .then(() => {
      //     console.log(this.$store.getters.getCurrentUser);
      //   });
    }
  }
};
</script>

<style lang="scss" scoped>
.image-container {
  background-image: url("../assets/login_bg.jpg");
  background-size: cover;
  width: 100vw;
  height: 100vh;
  background-position: center; /* Center the image */
  background-repeat: no-repeat; /* Do not repeat the image */
  margin: auto;
  position: relative;
}
.input-container {
  width: 33vw;
  opacity: 0.8;
  margin: auto;
  display: block;
}

#image {
  margin: auto;
  display: block;
  width: 10%;
  opacity: 0.7;
  padding-top: 1%;
}
#login-text {
  text-align: center;
  font-size: 4vh;
  font-weight: bold;
  padding-top: 1%;
}
#text-sign {
  text-align: center;
  font-size: 3vh;
  font-weight: bold;
}
.card-wrapper {
  padding: 26px !important;
}

.half_size {
  width: 40%;
  margin: 10px;
}

.button_wrapper {
  width: 100%;
  margin: auto;
  text-align: center;
  padding-top: 16px;
}

.red {
  color: red;
}
</style>
