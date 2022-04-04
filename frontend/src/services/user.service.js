import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8080/users/';

class UserService {
    //get all users for admin panel (ROLE_ADMIN)
    getAllUsers() {
        return axios.get(API_URL + 'all', {headers: authHeader()});
    }

    // get Information about current user
    whoami() {
        return axios.get(API_URL + 'whoami', {headers: authHeader()});
    }

    // update current user information
    updateAccountData(updateDto) {
        return axios.put(API_URL + 'update', updateDto, {headers: authHeader()});
    }

    // update user by username (ROLE_ADMIN)
    updateByUsername(username, updateDto) {
        return axios.put(API_URL + 'update/' + username, updateDto, {headers: authHeader()});
    }

    // delete user by username (ROLE_ADMIN)
    deleteByUsername(username) {
        return axios.delete(API_URL + username, {headers: authHeader()});
    }

    // validate username for registration (no authentication needed)
    validUsername(username) {
        return axios.get(API_URL + 'valid/' + username);
    }
}

export default new UserService();