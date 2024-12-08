import Vue from 'vue';
import Vuex from 'vuex';
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        userId: null,
        token: null,
        email: null,
        role: null, // 添加角色字段
    },
    mutations: {
        setUser(state, payload) {
            state.userId = payload.id;
            state.token = payload.token;
            state.role = payload.role;
            state.email = payload.email;
        },
        clearUser(state) {
            state.userId = null;
            state.token = null;
            state.role = null;
            state.email = null

        },
    },
    plugins: [createPersistedState()],

    actions: {
        login({ commit }, userData) {
            commit('setUser', userData);
        },
        logout({ commit }) {
            commit('clearUser');
        },
    },
    getters: {
        isLoggedIn: (state) => !!state.token,
        getUserId: (state) => state.userId,
        getUserRole: (state) => state.role,
        getEmail: (state) => state.email,
    },
});