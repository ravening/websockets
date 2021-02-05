export default class Board {
    constructor() {
        this.firstPlayer = true;
        this.cells = [
            ['', '', ''],
            ['', '', ''],
            ['', '', '']
        ];
    }

    /**
     * @param x The x location
     * @param y The y location
     * @returns {boolean} True if successful, False if invalid move.
     */
    doMove(x, y) {
        if (this.cells[x][y] !== '') {
            return false;
        }

        this.cells[x][y] = (this.firstPlayer ? 'X' : 'O');
        this.firstPlayer = !this.firstPlayer;
        return true;
    }

}
