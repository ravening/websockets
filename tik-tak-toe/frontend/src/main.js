import Vue from 'vue'
import store from './store'
import App from './App.vue'
import VueSocketIO from 'vue-socket.io'
import Toasted from 'vue-toasted';
import TicTacToe from "@/TicTacToe";
import Cell from "@/Cell";

Vue.use(Toasted);

Vue.component('tic-tac-toe', TicTacToe);
Vue.component('cell', Cell);

// Lets Register a Global Toasts.
Vue.toasted.register('appError',
    (payload) => {
        if (!payload.data) {
            return "Message not definded."
        }
        return payload.data;
    },
    {
        type: 'error'
    });

Vue.toasted.register('appSuccess',
    (payload) => {
        if (!payload.data) {
            return "Message not definded."
        }
        return payload.data;
    }, {
        type: 'success'
    });

Vue.toasted.register('appInfo',
    (payload) => {
        if (!payload) {
            return "Message not definded."
        }
        return payload.message;
    }, {
        type: 'info'
    });

// Now setup our socket and vuex configuration
Vue.use(new VueSocketIO({
    debug: true,
    connection: 'http://localhost:9092',
    vuex: {
        store,
        actionPrefix: 'SOCKET_',
        mutationPrefix: 'MUTATION_'
    },
    options: {} //Optional options
}))

new Vue({
    store,
    render: h => h(App)
}).$mount('#app')
