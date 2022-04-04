<template>
  <body>
  <div class="container-lg">
    <div class="table-responsive">
      <div class="table-wrapper">
        <div class="table-title">
          <div class="row">
            <div class="col-sm-8"><h2>User <b>Details</b></h2></div>
            <div class="col-sm-4">
              <button type="button" class="btn btn-info add-new" @click="pushToAccount">Return home</button>
            </div>
          </div>
        </div>
        <table class="table table-bordered">
          <thead>
          <tr>
            <th>Id</th>
            <th>Username</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Birth Date</th>
            <th>Description</th>
            <th>Address</th>
            <th>Roles</th>
            <th>Actions</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="userProfile in users">
            <td>{{ userProfile["id"] }}</td>
            <td>{{ userProfile["username"] }}</td>

            <td v-if="editable[userProfile.id]"><input class="form-control" type="text"
                                                       v-model="userProfile.firstName"
                                                       name="firstName" id="firstName" placeholder="firstName"></td>
            <td v-else>{{ userProfile["firstName"] }}</td>

            <td v-if="editable[userProfile.id]"><input class="form-control" type="text" v-model="userProfile.lastName"
                                                       name="lastName" id="lastName" placeholder="lastName"></td>
            <td v-else>{{ userProfile["lastName"] }}</td>

            <td v-if="editable[userProfile.id]">
              <input class="form-control" type="text" v-model="userProfile.birthDate"
                     name="birthDate" id="birthDate" placeholder="birthDate">
            </td>
            <td v-else>{{ userProfile["birthDate"] }}</td>

            <td v-if="editable[userProfile.id]"><input class="form-control" type="text"
                                                       v-model="userProfile.description" name="description"
                                                       id="description" placeholder="description"></td>
            <td v-else>{{ userProfile["description"] }}</td>

            <td v-if="editable[userProfile.id]"><input class="form-control" type="text" v-model="userProfile.address"
                                                       name="address" id="address" placeholder="address"></td>
            <td v-else>{{ userProfile["address"] }}</td>

            <td v-if="editable[userProfile.id]">
              <select v-model="userProfile.roles" name="role" id="role">
                <option value="ROLE_ADMIN">ADMIN</option>
                <option value="ROLE_CLIENT">CLIENT</option>
              </select></td>
            <td v-else>{{ getRole(userProfile["roles"]) }}</td>

            <td>
              <a v-if="editable[userProfile.id]" class="add" title="Add" data-toggle="tooltip"><i class="material-icons"
                                                                                                  @click="makeEditable(userProfile)">&#xE03B;</i></a>
              <a v-if="!editable[userProfile.id]" class="edit" title="Edit" data-toggle="tooltip"><i
                  class="material-icons"
                  @click="makeEditable(userProfile)">&#xE254;</i></a>
              <a class="delete" title="Delete" data-toggle="tooltip"><i class="material-icons"
                                                                        @click="deleteUser(userProfile.username)">&#xE872;</i></a>
            </td>
          </tr>

          </tbody>
        </table>

        <div class="container-lg">
          <input v-model="newUsername" class="form-control" type="text"
                 name="newUsername" id="newUsername" placeholder="Username">
          <p v-if="!validUsername && newUsername.length > 4">Username already exists</p>
          <input v-model="newPassword" class="form-control" type="text"
                 name="newPassword" id="newPassword" placeholder="Password">
          <p v-if="!validPasswordLength">Password length is too short</p>
          <input v-model="newFirstName" class="form-control" type="text"
                 name="newFirstName" id="newFirstName" placeholder="First Name">

          <input v-model="newLastName" class="form-control" type="text"
                 name="newLastName" id="newLastName" placeholder="Last Name">

          <input v-model="newBirthDate" class="form-control" type="text"
                 name="newBirthDate" id="newBirthDate" placeholder="Birthdate">
          <input v-model="newDescription" class="form-control" type="text"
                 name="newDescription" id="newDescription" placeholder="Description">

          <input v-model="newAddress" class="form-control" type="text"
                 name="newAddress" id="newAddress" placeholder="Address">

          <select v-model="newRole" class="form-control" name="newRole" id="newRole">
            <option value="ROLE_ADMIN">ADMIN</option>
            <option value="ROLE_CLIENT">CLIENT</option>
          </select>
        </div>
        <div class="col-lg">
          <button :disabled="!validUsername || !validPasswordLength" type="button" class="btn btn-info add-new"
                  @click="createUser"><i class="fa fa-plus"></i> Add New
          </button>
        </div>
      </div>
    </div>
  </div>
  </body>
</template>

<script>
import UserService from "@/services/user.service";
import Datepicker from "vue3-date-time-picker";
import vSelect from "vue-select";
import moment from "moment";
import AuthService from "@/services/auth.service";

export default {
  name: "AdminPanel",
  components: {Datepicker, vSelect},
  data() {
    return {
      user: this.$store.state.auth.user,
      users: [],
      editable: [],
      newUsername: '',
      newPassword: '',
      newFirstName: null,
      newLastName: null,
      newBirthDate: null,
      newDescription: null,
      newAddress: null,
      newRole: "ROLE_CLIENT",
      allowedUsername: false
    }
  },
  computed: {
    validUsername() {
      if (this.newUsername.length > 0) {
        UserService.validUsername(this.newUsername).then((response) => this.allowedUsername = response.data)
        return this.allowedUsername;
      }
    },
    validPasswordLength() {
      return this.newPassword.length > 6 || this.newPassword.length === 0;
    },
  },
  methods: {
    getRole(roles) {
      if (roles.includes('ROLE_ADMIN')) {
        return "ADMIN"
      }
      return "CLIENT"
    },
    getAllUsers() {
      UserService.getAllUsers().then((response) => {
            this.users = response.data
            for (let i = 0; i < this.users.length; i++) {
              this.editable[this.users[i].id] = false
            }
          },
          () => {
            this.$store.dispatch('auth/logout')
            this.$router.push("/login")
          })
    },
    pushToAccount() {
      this.$router.push("/user");
    },
    createUser() {
      console.log(this.newRole)
      let user = this.removeEmpty({
        username: this.newUsername,
        password: this.newPassword,
        firstName: this.newFirstName,
        lastName: this.newLastName,
        birthDate: this.formatDate(this.newBirthDate),
        description: this.newDescription,
        address: this.newAddress,
      });
      user.roles = [this.newRole];
      AuthService.register(user).then(() => this.getAllUsers());
    },
    formatDate(date) {
      return (moment(date).format("YYYY-MM-DD") === "Invalid date") ? null : moment(date).format("YYYY-MM-DD");
    },
    makeEditable(userProfile) {
      if (this.editable[userProfile.id]) {
        UserService.updateByUsername(userProfile.username, this.removeEmpty({
          firstName: userProfile.firstName,
          lastName: userProfile.lastName,
          birthDate: this.formatDate(userProfile.birthDate),
          description: userProfile.description,
          address: userProfile.address,
          roles: (Array.isArray(userProfile.roles)) ? userProfile.roles : [userProfile.roles]
        })).then((response) => {
          userProfile = response.data;
        })
      }
      this.editable[userProfile.id] = !this.editable[userProfile.id]
    },
    removeEmpty(obj) {
      return Object.fromEntries(Object.entries(obj).filter(([_, v]) => v != null));
    },
    deleteUser(username) {
      UserService.deleteByUsername(username).then(() => {
            UserService.getAllUsers().then((response) => {
              this.users = response.data
            })
          }
      )
    }
  },
  created() {
    if (!this.user || !this.user['roles'] || !this.user['roles'].includes('ROLE_ADMIN')) {
      this.$router.push("/unauthorized");
    }
    this.getAllUsers();
  }
}
</script>

<style scoped>
body {
  color: #404E67;
  background: #F5F7FA;
  font-family: 'Open Sans', sans-serif;
}

.table-wrapper {
  margin: 30px auto;
  background: #fff;
  padding: 20px;
  box-shadow: 0 1px 1px rgba(0, 0, 0, .05);
  word-wrap: break-word
}

.table-title {
  padding-bottom: 10px;
  margin: 0 0 10px;
}

.table-title h2 {
  margin: 6px 0 0;
  font-size: 22px;
}

.table-title .add-new {
  float: right;
  height: 30px;
  font-weight: bold;
  font-size: 12px;
  text-shadow: none;
  min-width: 100px;
  border-radius: 50px;
  line-height: 13px;
}

.table-title .add-new i {
  margin-right: 4px;
}

table.table {
  table-layout: fixed;
}

table.table tr th, table.table tr td {
  border-color: #e9e9e9;
  font-size: 10px;
}

table.table th i {
  font-size: 12px;
  margin: 0 5px;
  cursor: pointer;
}

table.table th:last-child {
  width: 100px;
}

table.table td a {
  cursor: pointer;
  display: inline-block;
  margin: 0 5px;
  min-width: 24px;
}

table.table td a.edit {
  color: #FFC107;
}

table.table td a.delete {
  color: #E34724;
}

table.table td i {
  font-size: 12px;
}

table.table td a.add i {
  font-size: 12px;
  margin-right: -1px;
  position: relative;
  top: 3px;
}

table.table .form-control {
  box-shadow: none;
  border-radius: 2px;
  font-size: 8px;
}

</style>