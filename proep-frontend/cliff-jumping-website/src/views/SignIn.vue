<template>
  <div>
    <SignInMenu></SignInMenu>
    <!-- <RegisterForm v-if="NeedRegistration"></RegisterForm> -->
    <!-- <h1 v-on:click="$store.commit('increment')">COUNT: {{$store.state.count}}</h1> -->
  </div>
</template>

<script>
import SignInMenu from "../components/SignInMenu";
//import RegisterForm from "../components/RegisterForm";
export default {
  data() {
    return {
      //NeedRegistration:false
    };
  },
  name: "SignIn",
  components: {
    SignInMenu
    //RegisterForm,
  }
};
</script>

<style lang="scss" scoped>
.signIn {
  text-align: center;
}
</style>
