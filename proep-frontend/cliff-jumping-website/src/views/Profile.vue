<template>
  <body>
    
    <ProfileComponent></ProfileComponent>    
</body>
</template>

<script>
import ProfileComponent from "../components/ProfileComponent";

export default {
  name: "Profile",
  props: {
    msg: String
  }, 
  components:{
    ProfileComponent
  }
}
</script>

<style lang="scss" scoped>
body{
    background-image: url('../assets/sea.jpeg'); 
    background-repeat: no-repeat;
    background-attachment: fixed;
    background-size: cover;
    padding: 1%;
}
</style>



