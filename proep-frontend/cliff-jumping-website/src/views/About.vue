<template>
  <body>
    
    <div class="pa-6 text-center">
  <AboutUsInfo></AboutUsInfo>
  </div>
</body>
</template>

<script>
import AboutUsInfo from "../components/AboutUsInfo";
export default {
  name: "About",
  props: {
    msg: String
  }, 
  components:{
    AboutUsInfo
  }
}
</script>