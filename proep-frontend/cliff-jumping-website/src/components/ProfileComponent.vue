<template>
  <body>
    <div class="container emp-profile">
      <form method="post">
        <div class="row">
          <div class="col-md-4">
            <div class="profile-img">
              <img
                src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS52y5aInsxSm31CvHOFHWujqUx_wWTS9iM6s7BAm21oEN_RiGoog"
                alt
              />
              <div class="file btn btn-lg btn-primary">
                Change Photo
                <input type="file" name="file" />
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="profile-head">
              <h5>{{getUser.firstName}} {{getUser.lastName}}</h5>
              <h6>
                Experience Level:
                <span>{{getUser.experienceLevel}}</span>
              </h6>
              <p class="proile-rating">
                Total Logs :
                <span>7</span>
              </p>
              <ul class="nav nav-tabs" id="myTab" role="tablist">
                <li class="nav-item">
                  <a
                    class="nav-link active"
                    id="home-tab"
                    data-toggle="tab"
                    role="tab"
                    aria-controls="home"
                    aria-selected="true"
                    v-on:click="myfunction()"
                    >About</a
                  >
                </li>
                <li class="nav-item">
                  <a
                    class="nav-link"
                    id="profile-tab"
                    data-toggle="tab"
                    role="tab"
                    aria-controls="profile"
                    aria-selected="false"
                    v-on:click="myfunction2()"
                    >My Logs</a
                  >
                </li>
              </ul>
            </div>
          </div>
          <div class="col-md-2">
            <button type="button" class="btn btn-warning">Edit</button>
          </div>
        </div>
        <div class="row">
          <div class="col-md-4">
            <div class="profile-work">
              <p>Subscribed Events</p>
              <a href>Event 1</a>
              <br />
              <a href>Event 2</a>
              <br />
              <a href>Event 3</a>
              <br />
              <p>Your Jumping Sites & Events</p>
              <a href>Event 1</a>
              <br />
              <a href>Event 2</a>
              <br />
              <a href>Event 3</a>
              <br />
              <a href>Jumping Site 1</a>
              <br />
              <a href>Jumping Site 2</a>
              <br />
            </div>
          </div>
          <div class="col-md-8">
            <div class="tab-content profile-tab" id="myTabContent">
              <div
                class="tab-pane fade show active"
                id="home"
                role="tabpanel"
                aria-labelledby="home-tab"
              >
                <div class="row">
                  <div class="col-md-6">
                    <label>First Name</label>
                  </div>
                  <div class="col-md-6">
                    <p>{{getUser.firstName}} </p>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-6">
                    <label>Last Name</label>
                  </div>
                  <div class="col-md-6">
                    <p>{{getUser.lastName}} </p>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-6">
                    <label>Email</label>
                  </div>
                  <div class="col-md-6">
                    <p>{{getUser.email}} </p>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-6">
                    <label>Country</label>
                  </div>
                  <div class="col-md-6">
                    <p>{{getUser.country}}</p>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-6">
                    <label>City</label>
                  </div>
                  <div class="col-md-6">
                    <p>{{getUser.city}}</p>
                  </div>
                </div>
              </div>
              <div
                class="tab-pane fade"
                id="profile"
                role="tabpanel"
                aria-labelledby="profile-tab"
              >
                <div class="row">
                  <div class="col-md-12">
                    <h5><b>My Logs:</b></h5>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-6">
                    <label
                      ><b>Location: </b> Maldives <b>Description:</b> Great
                      Location <b>Date:</b> 20/20/20</label
                    >
                  </div>
                  <div class="col-md-6">
                    <button type="button" class="btn btn-danger">Delete</button>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-6">
                    <label
                      ><b>Location: </b> Spain <b>Description:</b> Great
                      Location <b>Date:</b> 20/20/20</label
                    >
                  </div>
                  <div class="col-md-6">
                    <button type="button" class="btn btn-danger">Delete</button>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-6">
                    <label
                      ><b>Location: </b> Italy <b>Description:</b> Great
                      Location <b>Date:</b> 20/20/20</label
                    >
                  </div>
                  <div class="col-md-6">
                    <button type="button" class="btn btn-danger">Delete</button>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-6">
                    <label
                      ><b>Location: </b> Rio De Janeiro
                      <b>Description:</b> Great Location
                      <b>Date:</b> 20/20/20</label
                    >
                  </div>
                  <div class="col-md-6">
                    <button type="button" class="btn btn-danger">Delete</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </form>
    </div>
  </body>
</template>

<script>
export default {
  el: "#home-tab",
  methods: {
    myfunction: function() {
      document.getElementById("home-tab").className += " active show";
      document.getElementById("home").className += " active show";

      document.getElementById("profile-tab").className = "nav-link";
      document.getElementById("profile").className = "tab-pane fade";
    },
    myfunction2: function() {
      document.getElementById("profile-tab").className += " active show";
      document.getElementById("profile").className += " active show";

      document.getElementById("home-tab").className = "nav-link";
      document.getElementById("home").className = "tab-pane fade";
    },
  },
  computed: {
    getUser() {
      return this.$store.getters.getCurrentUser;
    }
  }
};
</script>

<style lang="scss" scoped>
.emp-profile {
  padding: 3%;
  margin-top: 2%;
  border-radius: 0.5rem;
  opacity: 0.9;
  background: #fff;
}
.profile-img {
  text-align: center;
}
.profile-img img {
  width: 70%;
  height: 100%;
}
.profile-img .file {
  position: relative;
  overflow: hidden;
  margin-top: -20%;
  width: 70%;
  border: none;
  border-radius: 0;
  font-size: 15px;
  background: #212529b8;
}
.profile-img .file input {
  position: absolute;
  opacity: 0;
  right: 0;
  top: 0;
}
.profile-head h5 {
  color: #333;
}
.profile-head h6 {
  color: #0062cc;
}
.profile-edit-btn {
  border: none;
  border-radius: 1.5rem;
  width: 70%;
  padding: 2%;
  font-weight: 600;
  color: #6c757d;
  cursor: pointer;
}
.proile-rating {
  font-size: 12px;
  color: #818182;
  margin-top: 5%;
}
.proile-rating span {
  color: #495057;
  font-size: 15px;
  font-weight: 600;
}
.profile-head .nav-tabs {
  margin-bottom: 5%;
}
.profile-head .nav-tabs .nav-link {
  font-weight: 600;
  border: none;
}
.profile-head .nav-tabs .nav-link.active {
  border: none;
  border-bottom: 2px solid #0062cc;
}
.profile-work {
  padding: 14%;
  margin-top: -15%;
}
.profile-work p {
  font-size: 12px;
  color: #818182;
  font-weight: 600;
  margin-top: 10%;
}
.profile-work a {
  text-decoration: none;
  color: #495057;
  font-weight: 600;
  font-size: 14px;
}
.profile-work ul {
  list-style: none;
}
.profile-tab label {
  font-weight: 600;
}
.profile-tab p {
  font-weight: 600;
  color: #0062cc;
}
</style>
