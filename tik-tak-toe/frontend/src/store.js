import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const GAME_OVER = 'gameover'

export default new Vuex.Store({
    state: {
        isGameOver: false
    },
    getters: {
        gameOver: (state) => state.isGameOver
    },
    actions: {
        "SOCKET_oops"(state, server) {
            Vue.toasted.global.appError({
                message: server.message
            }).goAway(1200);
        },
        "SOCKET_success"(state, server) {
            Vue.toasted.global.appSuccess({
                message: server.message
            }).goAway(1200);
        },
        "SOCKET_info"(state, server) {
            Vue.toasted.global.appInfo({
                message: server.message
            }).goAway(1200);
        },
        "SOCKET_gameover"(state, server) {
            Vue.toasted.global.appInfo({
                message: server
            }).goAway(5200);
            state.isGameOver = true
        }
    },
    mutations: {
        "MUTATION_gameover"(state) {
          this.isGameOver = true
          state.isGameOver = true
        },
        [GAME_OVER](state) {
            state.isGameOver = true
        }
    }
})
