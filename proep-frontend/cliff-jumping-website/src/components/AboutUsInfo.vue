<template>
<body>
  <div class="about_us">
    <v-card class="contact_info" max-width="1500" padding="200px" align="center" justify="center">
      <h2>- Our Mission -</h2>
      <v-card-text class="mission">Make cliff jumping available for wider audience</v-card-text>
      <h2>- About Us -</h2>

      <v-card-text class="about_us_info">
        We are a team of developers that is passionate for Cliff Jumping.
        Our main focus is to make it available for the wider audience by creating this community for people with shared interests.
        We are creating this brand new category in outdoor activities called “Cliff Jumping”.
      </v-card-text>
    </v-card>
  </div>
  <div class="img_holder">
    <v-row>
      <v-img
        src="@/assets/Jump.jpg"
        aspect-ratio="1"
        class="Jump_img"
        max-width="1280"
        max-height="720"
      ></v-img>
    </v-row>
  </div>
  <div class="contact_form">
    <p class="contact_header" style="text-align:middle">Contact Form</p>
    <div class="messege_text">
      <v-container fluid>
        <v-row>
          <v-col cols="12" md="8">
            <v-textarea
              solo
              outlined
              name="input"
              v-model="messege"
              placeholder="Ask us anything"
              :error-messages="messegeError"
              @input="$v.messege.$touch()"
              @blur="$v.messege.$touch()"
              required
            ></v-textarea>
            <p v-if="err_messege" class="error_messege">Insert Messege</p>
          </v-col>
        </v-row>
      </v-container>
    </div>
    <div class="person_info">
      <v-form>
        <v-container>
          <v-row>
            <v-col cols="12" md="4">
              <v-text-field
                v-model="name"
                :error-messages="nameError"
                @input="$v.name.$touch()"
                @blur="$v.name.$touch()"
                :counter="10"
                label="Name"
                required
              ></v-text-field>
            </v-col>

            <v-col cols="12" md="4">
              <v-text-field
                v-model="email"
                :error-messages="emailError"
                @input="$v.email.$touch()"
                @blur="$v.email.$touch()"
                label="E-mail"
                required
              ></v-text-field>
            </v-col>
          </v-row>
          <div class="my-2">
            <v-btn small>Send</v-btn>
          </div>
          <p v-if="err_email" class="error_messege">Insert correct Name and Email</p>
        </v-container>
      </v-form>
    </div>
  </div>
  <div data-app class="text-center">
    <v-dialog v-model="dialog" width="500">
      <template v-slot:activator="{ on }">
        <v-btn color="red lighten-2" dark v-on="on">Click Me</v-btn>
      </template>

      <v-card>
        <v-card-title class="headline grey lighten-2" primary-title>Privacy Policy</v-card-title>

        <v-card-text>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" text @click="dialog = false">I accept</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</body>
</template>
<script lang="ts">
import { validationMixin } from "vuelidate";
import { required, email } from "vuelidate/lib/validators";

export default {
  name: "ContactInfo",
  props: {
    msg: String
  },

  mixins: [validationMixin],

  validations: {
    name: { required },
    email: { required, email },
    messege: { required }
  },
  data: () => ({
    name: "",
    email: "",
    messege: "",
    //aboutus: this.$store.state.info,
    err_email: null,
    err_messege: null
  }),
  computed: {
    AboutUsInfo() {
      return this.$store.getters.getInfo;
    },
    emailError() {
      const errors = [];
      if (!this.$v.email.$dirty) return errors;
      !this.$v.email.email && errors.push("Must be valid e-mail");
      !this.$v.email.required && errors.push("E-mail is required");
      return errors;
    },
    nameError() {
      const errors = [];
      if (!this.$v.name.$dirty) return errors;
      !this.$v.name.required && errors.push("Name is required");
      return errors;
    },
    messegeError() {
      const errors = [];
      if (!this.$v.messege.$dirty) return errors;
      !this.$v.messege.required && errors.push("Enter Messege");
      return errors;
    }
  }
};
</script>

<style lang="scss" scoped>
.img_holder {
  width: 1280px;
  margin-left: auto;
  margin-right: auto;
  // margin-left: 15%;
  margin-bottom: 5%;
}
.about_us {
  width: 1500px;
  margin-left: auto;
  margin-right: auto;
  // margin-left: 10%;
  margin-top: 5%;
  margin-bottom: 5%;
}
.messege_text {
  width: 1200px;
  margin-top: 0%;
  margin-left: 15%;
}
.person_info {
  margin-left: 15%;
}
.contact_header {
  font-size: 100%;
  margin-left: 15%;
  margin-bottom: 0%;
}
.error_messege {
  margin-left: 25%;
  color: red;
}
h2 {
  text-align: center;
  color: rgb(51, 38, 38);
}
.contact_form {
  width: 1500px;
  margin-left: auto;
  margin-right: auto;
}
.about_us_info {
  padding: 30px;
  padding-left: 100px;
  padding-right: 100px;
}
.mission {
  padding: 30px;
}
</style>