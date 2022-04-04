<template>
  <div class="materialContainer">
    <div class="box">
      <div class="title">LOGIN</div>
      <div class="input">
        <label for="name"></label>
        <input type="text" v-model="username" name="name" id="name" placeholder="username">
        <span class="spin"></span>
      </div>
      <div class="input">
        <label for="pass"></label>
        <input type="password" v-model="password" name="pass" id="pass" placeholder="password">
        <span class="spin"></span>
      </div>
      <p>{{ this.message }}</p>
      <div class="button login">
        <button @click="handleLogin"><span>GO</span> <i class="fa fa-check"></i></button>
      </div>
      <div class="button register">
        <button @click="redirectToRegistration"><span>Create account</span> <i class="fa fa-check"></i></button>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "Login",
  data() {
    return {
      username: '',
      password: '',
      message: ''
    }
  },
  created() {
    if (this.$store.state.auth.status.loggedIn) {
      this.$router.push("/user");
    }
  }
  ,
  methods: {
    redirectToRegistration() {
      this.$router.push("/signup");
    },
    handleLogin() {
      this.$store.dispatch("auth/login", {
        username: this.username,
        password: this.password
      }).then(
          () => {
            this.$router.push("/user");
          },
          (error) => {
            this.message =
                (error.response &&
                    error.response.data &&
                    error.response.data.message) ||
                error.message ||
                error.toString();
          });
    }
  }
}
</script>

<style scoped>
.box {
  position: relative;
  top: 0;
  opacity: 1;
  float: left;
  padding: 60px 50px 40px 50px;
  width: 100%;
  background: #fff;
  border-radius: 10px;
  transform: scale(1);
  -webkit-transform: scale(1);
  -ms-transform: scale(1);
  z-index: 5;
}

.box:before {
  content: "";
  width: 100%;
  height: 30px;
  border-radius: 10px;
  position: absolute;
  top: -10px;
  background: rgba(255, 255, 255, 0.6);
  left: 0;
  transform: scale(0.95);
  -webkit-transform: scale(0.95);
  -ms-transform: scale(0.95);
  z-index: -1;
}

.overbox .title {
  color: #fff;
}

.overbox .title:before {
  background: #fff;
}

.title {
  width: 100%;
  float: left;
  line-height: 20px;
  font-size: 25px;
  font-weight: 700;
  letter-spacing: 2px;
  color: #ED2553;
  position: relative;
}

.title:before {
  content: "";
  width: 5px;
  height: 100%;
  position: absolute;
  top: 0;
  left: -50px;
  background: #ED2553;
}

.input {
  transition: 300ms cubic-bezier(0.4, 0, 0.2, 1);
  -webkit-transition: 300ms cubic-bezier(0.4, 0, 0.2, 1);
  -ms-transition: 300ms cubic-bezier(0.4, 0, 0.2, 1);
}

.input label, .input input, .input .spin {
  transition: 300ms cubic-bezier(0.4, 0, 0.2, 1);
  -webkit-transition: 300ms cubic-bezier(0.4, 0, 0.2, 1);
  -ms-transition: 300ms cubic-bezier(0.4, 0, 0.2, 1);
}

.button {
  transition: 300ms cubic-bezier(0.4, 0, 0.2, 1);
  -webkit-transition: 300ms cubic-bezier(0.4, 0, 0.2, 1);
  -ms-transition: 300ms cubic-bezier(0.4, 0, 0.2, 1);
}

.button button .button.login button i.fa {
  transition: 300ms cubic-bezier(0.4, 0, 0.2, 1);
  -webkit-transition: 300ms cubic-bezier(0.4, 0, 0.2, 1);
  -ms-transition: 300ms cubic-bezier(0.4, 0, 0.2, 1);
}

.button.login button {
  transition: 300ms cubic-bezier(0.4, 0, 0.2, 1);
  -webkit-transition: 300ms cubic-bezier(0.4, 0, 0.2, 1);
  -ms-transition: 300ms cubic-bezier(0.4, 0, 0.2, 1);
}

.input {
  width: 100%;
  float: left;
}

.input label, .input input, .input .spin {
  width: 100%;
  float: left;
}

.button {
  width: 100%;
  float: left;
}

.button button {
  width: 100%;
  float: left;
}

.input, .button {
  margin-top: 15px;
  height: 50px;
}

.input {
  position: relative;
}

.input input {
  position: relative;
}

.button {
  position: relative;
}

.button button {
  position: relative;
}

.input input {
  height: 60px;
  top: 10px;
  border: none;
  background: transparent;
  font-family: "Roboto", sans-serif;
  font-size: 15px;
  color: rgba(0, 0, 0, 0.8);
  font-weight: 300;
}

.input label {
  font-family: "Roboto", sans-serif;
  font-size: 24px;
  color: rgba(0, 0, 0, 0.8);
  font-weight: 300;
}

.button button {
  font-family: "Roboto", sans-serif;
  font-size: 15px;
  color: rgba(0, 0, 0, 0.8);
  font-weight: 300;
}

.input:before, .input .spin {
  width: 100%;
  height: 1px;
  position: absolute;
  bottom: 0;
  left: 0;
}

.input:before {
  content: "";
  background: rgba(0, 0, 0, 0.1);
  z-index: 3;
}

.input .spin {
  background: #ED2553;
  z-index: 4;
  width: 0;
}

.overbox .input .spin {
  background: white;
}

.overbox .input:before {
  background: rgba(255, 255, 255, 0.5);
}

.input label {
  position: absolute;
  top: 10px;
  left: 0;
  z-index: 2;
  cursor: pointer;
  line-height: 60px;
}

.button {
  margin-top: 20px;
  z-index: 2;
}

.button.login {
  width: 60%;
  left: 20%;
}

.button.login button {
  width: 100%;
  line-height: 64px;
  left: 0;
  background-color: transparent;
  border: 3px solid rgba(0, 0, 0, 0.1);
  font-weight: 900;
  font-size: 18px;
  color: rgba(0, 0, 0, 0.2);
}

.button button {
  width: 100%;
  line-height: 64px;
  left: 0;
  background-color: transparent;
  border: 3px solid rgba(0, 0, 0, 0.1);
  font-weight: 900;
  font-size: 18px;
  color: rgba(0, 0, 0, 0.2);
}

.button.login {
  margin-top: 30px;
}

.button button {
  background-color: #fff;
  color: #ED2553;
  border: none;
}

.button.login button.active span {
  opacity: 0;
  transform: scale(0);
  -webkit-transform: scale(0);
  -ms-transform: scale(0);
}

.button.login button.active i.fa {
  opacity: 1;
  transform: scale(1) rotate(0deg);
  -webkit-transform: scale(1) rotate(0deg);
  -ms-transform: scale(1) rotate(0deg);
}

.button.login button i.fa {
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0;
  left: 0;
  line-height: 60px;
  transform: scale(0) rotate(-45deg);
  -webkit-transform: scale(0) rotate(-45deg);
  -ms-transform: scale(0) rotate(-45deg);
}

.button.login button:hover {
  color: #ED2553;
  border-color: #ED2553;
}

.button button {
  cursor: pointer;
  position: relative;
  z-index: 2;
}

.overbox .title, .overbox .button, .overbox .input {
  z-index: 111;
  position: relative;
  color: #fff !important;
  display: none;
}

.overbox .title {
  width: 80%;
}

.overbox .input {
  margin-top: 20px;
}

.overbox .input input, .overbox .input label {
  color: #fff;
}


body {
  background-image: url(https://lh4.googleusercontent.com/-XplyTa1Za-I/VMSgIyAYkHI/AAAAAAAADxM/oL-rD6VP4ts/w1184-h666/Android-Lollipop-wallpapers-Google-Now-Wallpaper-2.png);
  background-position: center;
  background-size: cover;
  background-repeat: no-repeat;
  min-height: 100vh;
  font-family: "Roboto", sans-serif;
  overflow: hidden;
}

html {
  overflow: hidden;
}

.materialContainer {
  width: 100%;
  max-width: 460px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  -webkit-transform: translate(-50%, -50%);
  -ms-transform: translate(-50%, -50%);
}

* {
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
  margin: 0;
  padding: 0;
  text-decoration: none;
  list-style-type: none;
  outline: none;
}

*:after, *::before {
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
  margin: 0;
  padding: 0;
  text-decoration: none;
  list-style-type: none;
  outline: none;
}
</style>