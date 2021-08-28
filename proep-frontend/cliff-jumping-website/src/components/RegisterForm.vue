<template>
  <div>
    <v-app class="image-container">
      <p id="login-text">Register an account</p>
      <div class="input-container">
        <v-card raised class="card-wrapper">
          <form>
            <v-text-field v-model="FirstName" label="First Name" required></v-text-field>
            <v-text-field v-model="LastName" label="Last Name" required></v-text-field>
            <v-text-field v-model="email" :error-messages="emailErrors" label="E-mail" required></v-text-field>
            <v-text-field v-model="password" label="Password" required></v-text-field>
            <v-text-field v-model="country" label="Country" required></v-text-field>
            <v-text-field v-model="city" label="City" required></v-text-field>
            <v-select :items="ExperienceLevels" label="Experience Level"></v-select>
            <div class="button_wrapper">
              <v-btn class="mr-4 half_size login">Register</v-btn>
              <v-btn class="half_size">Back</v-btn>
            </div>
          </form>
        </v-card>
      </div>
    </v-app>
  </div>
</template>

<script>
import { validationMixin } from "vuelidate";
import { required, maxLength, email } from "vuelidate/lib/validators";

export default {
  name: "RegisterForm",
  props: {
    msg: String
  },
  mixins: [validationMixin],

  validations: {
    name: { required, maxLength: maxLength(10) },
    email: { required, email }
  },

  data: () => ({
    FirstName: "",
    LastName: "",
    email: "",
    password: "",
    country: "",
    city: "",
    experienceLevel: "",
    ExperienceLevels: ["Begginer", "Intermediate", "Pro"],
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

  methods: {}
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
  margin-left: 33%;
}
#login-text {
  text-align: center;
  font-size: 5vh;
  padding-top: 10%;
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
