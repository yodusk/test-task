<template>
  <div class="materialContainer">
    <div class="box">
      <div class="title">Create account</div>
      <div class="input">
        <label for="username"></label>
        <input type="text" name="username" id="username" v-model="username" placeholder="username">
        <span class="spin"></span>
        <p v-if="!validUsername && username.length > 4">Username already exists</p>
      </div>
      <div class="input">
        <label for="pass"></label>
        <input type="password" name="pass" id="pass" v-model="password1" placeholder="password">
        <span class="spin"></span>
        <p v-if="!validPasswordLength">Password length is too short</p>
      </div>
      <div class="input">
        <label for="pass2"></label>
        <input type="password" name="pass2" id="pass2" v-model="password2" placeholder="repeat password">
        <span class="spin"></span>
        <p v-if="!passwordsEqual">Passwords doesn't equal</p>
      </div>
      <div class="input">
        <label for="firstName"></label>
        <input type="text" name="firstName" id="firstName" v-model="firstName" placeholder="First name">
        <span class="spin"></span>
      </div>
      <div class="input">
        <label for="lastName"></label>
        <input type="text" name="lastName" id="lastName" v-model="lastName" placeholder="Last name">
        <span class="spin"></span>
      </div>
      <div class="input">
        <Datepicker v-model="birthDate" placeholder="Birthday" :closeOnAutoApply="false" format="yyyy/MM/dd"
        ></Datepicker>
        <span class="spin"></span>
      </div>
      <div class="input">
        <label for="description"></label>
        <input type="text" name="description" id="description" v-model="description" placeholder="description">
        <span class="spin"></span>
      </div>
      <div class="input">
        <label for="address"></label>
        <input type="text" name="address" id="address" v-model="address" placeholder="address">
        <span class="spin"></span>
      </div>
      <div
          v-bind:class="(!validUsername || !validPasswordLength || !passwordsEqual)?'button login':'button login loginActive'">
        <button :disabled="!validUsername || !validPasswordLength || !passwordsEqual" @click="handleRegister"><span>Register</span>
          <i class="fa fa-check"></i>
        </button>
      </div>
      <div class="button register">
        <button @click="pushToLogin"><span>Already have an account?</span> <i class="fa fa-check"></i></button>
      </div>
    </div>
  </div>
</template>

<script>
import UserService from "@/services/user.service";
import Datepicker from 'vue3-date-time-picker';
import 'vue3-date-time-picker/dist/main.css';
import moment from 'moment';

export default {
  name: "SignUp",
  components: {Datepicker},
  data() {
    return {
      username: '',
      password1: '',
      password2: '',
      firstName: null,
      lastName: null,
      birthDate: null,
      description: null,
      address: null,
      allowedUsername: false,
    }
  },
  computed: {
    validUsername() {
      if (this.username.length > 0) {
        UserService.validUsername(this.username).then((response) => this.allowedUsername = response.data)
        return this.allowedUsername;
      }
    },
    validPasswordLength() {
      return this.password1.length > 6 || this.password1.length < 3;
    },
    passwordsEqual() {
      return this.password1 === this.password2;
    },
    formattedDate() {
      let date = moment(this.birthDate).format("YYYY-MM-DD");
      if (date === undefined) {
        return null;
      }
      return date;
    }
  },
  created() {
    if (this.$store.state.auth.status.loggedIn) {
      this.$router.push("/user");
    }
  },
  methods: {
    pushToLogin() {
      this.$router.push("/login");
    },
    handleRegister() {
      this.$store.dispatch("auth/register", this.removeEmpty({
        username: this.username,
        password: this.password1,
        firstName: this.firstName,
        lastName: this.lastName,
        birthDate: (this.formattedDate === "Invalid date") ? null : this.formattedDate,
        description: this.description,
        address: this.address
      })).then(() =>
          this.$router.push("/login")
      )
    },
    removeEmpty(obj) {
      return Object.fromEntries(Object.entries(obj).filter(([_, v]) => v != null));
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

.button.loginActive button:hover {
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