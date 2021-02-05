<template>
  <div class="tictactoe-board">
    <div v-for="(n, i) in 3" :key="i">
      <div v-for="(n, j) in 3" :key="j">
        <cell @click="!gameOver && performMove(i, j)" :value="board.cells[i][j]"></cell>
      </div>
    </div>
  </div>
</template>

<script>
import Board from "./Board";
import { mapGetters } from 'vuex';

export default {
  data() {
    return {
      board: new Board(),
      firstPlayer: true,
    }
  },
  computed: {
    ...mapGetters(['gameOver']),
    gameover() {
      return this.$store.state.isGameOver
    }
  },
  mounted() {

  },
  watch: {
    gameover(newvalue, oldvalue) {
      if (newvalue) {
        console.log('newvalues is ' + newvalue + ' oldvalue is ' + oldvalue)
        this.$socket.emit('reset-game')
      }
    }
  },
  methods: {
    performMove(x, y) {
      if (!this.board.doMove(x, y)) {
        // Invalid move.
        return;
      }
      this.$forceUpdate();
      if (!this.gameOver) {
        this.$socket.emit('move', {x: x, y: y, firstPlayer: this.firstPlayer})
        this.firstPlayer = !this.firstPlayer
      }
    },
  }
}
</script>

<style>
.tictactoe-board {
  display: flex;
  flex-wrap: wrap;
  width: 204px;
  height: 204px;
}
</style>
