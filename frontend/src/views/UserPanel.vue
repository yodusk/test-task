<template>
  <div class="materialContainer" v-if="user">
    <div class="box">
      <div class="title">Пользователь: {{ user.username }}</div>
      <div class="input">
        <label for="firstName">Имя</label>
        <input type="text" name="firstName" id="firstName" v-model="user.firstName" placeholder="First name">
        <span class="spin"></span>
      </div>
      <div class="input">
        <label for="lastName">Фамилия</label>
        <input type="text" name="lastName" id="lastName" v-model="user.lastName" placeholder="Last name">
        <span class="spin"></span>
      </div>
      <div class="input">
        <label for="birthdate">Дата рождения</label>
        <input type="text" name="birthdate" id="birthdate" v-model="user.birthDate" placeholder="BirthDate">
        <span class="spin"></span>
      </div>
      <div class="input">
        <label for="description">О себе</label>
        <input type="text" name="description" id="description" v-model="user.description" placeholder="description">
        <span class="spin"></span>
      </div>
      <div class="input">
        <label for="address">Адрес</label>
        <input type="text" name="address" id="address" v-model="user.address" placeholder="address">
        <span class="spin"></span>
      </div>
      <div class="button login">
        <button @click="saveChanges"><span>Save changes</span> <i class="fa fa-check"></i></button>
      </div>
      <div class="button register" v-if="isAdmin">
        <button @click="pushToAdmin"><span>Admin panel</span> <i class="fa fa-check"></i></button>
      </div>
      <div class="button register">
        <button @click="logOut"><span>Log Out</span> <i class="fa fa-check"></i></button>
      </div>
    </div>
  </div>
</template>

<script>

import userService from "@/services/user.service";

export default {
  name: 'UserPanel',
  data() {
    return {
      user: this.$store.state.auth.user
    }
  },
  created() {
    if (!this.$store.state.auth.status.loggedIn) {
      this.$router.push("/login");
    }
  },
  methods: {
    logOut() {
      this.$store.dispatch('auth/logout');
      this.$router.push('/login');
    },
    pushToAdmin() {
      this.$router.push('/admin');
    },
    saveChanges() {
      userService.updateAccountData({
        firstName: this.user.firstName,
        lastName: this.user.lastName,
        birthDate: this.user.birthDate,
        description: this.user.description,
        address: this.user.address,
        roles: this.user.roles
      }).then(() => {
        this.$store.state.auth.user = this.user;
      })
    }
  },
  computed: {
    isAdmin() {
      return this.user && this.user['roles'] && this.user['roles'].includes('ROLE_ADMIN')
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

label {
  font-size: 6px;
  text-align: left;
  display: block;
  text-indent: -60%;
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
