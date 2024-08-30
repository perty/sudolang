class Cave {
    constructor(number, connected) {
        this.number = number;
        this.connected = connected;
    }
}

class Player {
    constructor(current, arrows = 5) {
        this.current = current;
        this.arrows = arrows;
    }
}

class Wumpus {
    constructor(current) {
        this.current = current;
    }
}

class SuperBats {
    constructor(current) {
        this.current = current;
    }
}

class BottomlessPit {
    constructor(current) {
        this.current = current;
    }
}

class HuntTheWumpus {
    constructor() {
        this.caves = this.generateCaves();
        this.player = new Player(this.randomCave());
        this.wumpus = new Wumpus(this.randomCave());
        this.superBats = [new SuperBats(this.randomCave()), new SuperBats(this.randomCave())];
        this.bottomlessPits = [new BottomlessPit(this.randomCave()), new BottomlessPit(this.randomCave())];
    }

    generateCaves() {
        let caves = [];
        for (let i = 0; i < 20; i++) {
            caves.push(new Cave(i, []));
        }
        caves[0].connected = [1, 4, 7];
        caves[1].connected = [0, 2, 9];
        caves[2].connected = [1, 3, 11];
        caves[3].connected = [2, 4, 13];
        caves[4].connected = [0, 3, 5];
        caves[5].connected = [4, 6, 14];
        caves[6].connected = [5, 7, 16];
        caves[7].connected = [0, 6, 8];
        caves[8].connected = [7, 9, 17];
        caves[9].connected = [1, 8, 10];
        caves[10].connected = [9, 11, 18];
        caves[11].connected = [2, 10, 12];
        caves[12].connected = [11, 13, 19];
        caves[13].connected = [3, 12, 14];
        caves[14].connected = [5, 13, 15];
        caves[15].connected = [14, 16, 19];
        caves[16].connected = [6, 15, 17];
        caves[17].connected = [8, 16, 18];
        caves[18].connected = [10, 17, 19];
        caves[19].connected = [12, 15, 18];
        return caves;
    }

    randomCave() {
        return this.caves[Math.floor(Math.random() * this.caves.length)];
    }

    describeCave() {
        const cave = this.player.current;
        this.appendOutput(`You are in cave ${cave.number}.`);
        this.appendOutput(`Connected caves: ${cave.connected.join(', ')}.`);

        const adjacentCaves = cave.connected.map(c => this.caves[c.number]);

        if (adjacentCaves.some(c => c === this.wumpus.current)) {
            this.appendOutput("You smell something terrible nearby.");
        }
        if (adjacentCaves.some(c => this.bottomlessPits.some(p => p.current === c))) {
            this.appendOutput("You feel a draft.");
        }
        if (adjacentCaves.some(c => this.superBats.some(b => b.current === c))) {
            this.appendOutput("You hear flapping wings.");
        }

        this.appendOutput(`You have ${this.player.arrows} arrows left.`);
    }

    appendOutput(text) {
        const outputDiv = document.getElementById('game-output');
        outputDiv.innerText += text + '\n';
        outputDiv.scrollTop = outputDiv.scrollHeight;
    }

    playerTurn() {
        const action = prompt('Do you want to move or shoot? (m/s): ').toLowerCase();

        if (action === 'm') {
            const target = parseInt(prompt('Which cave do you want to move to? '));
            if (isNaN(target) || target < 0 || target >= 20) {
                this.appendOutput("Invalid cave number. Try again.");
            } else if (!this.player.current.connected.includes(this.caves[target])) {
                this.appendOutput("You can't move there. Try again.");
            } else {
                this.player.current = this.caves[target];
                this.appendOutput(`You moved to cave ${target}.`);
            }
        } else if (action === 's') {
            const pathInput = prompt('Enter up to 5 caves to shoot through, separated by spaces: ');
            const path = pathInput.split(' ').map(num => this.caves[parseInt(num)]);

            if (path.length === 0 || path.some(cave => isNaN(cave.number) || cave.number < 0 || cave.number >= 20)) {
                this.appendOutput("Invalid path. Try again.");
            } else {
                this.shootArrow(path);
            }
        } else {
            this.appendOutput("Invalid action. Try again.");
        }

        this.checkConditions();
    }

    shootArrow(path) {
        this.appendOutput(`You shoot through caves: ${path.map(c => c.number).join(', ')}.`);
        let currentCave = this.player.current;
        for (let cave of path) {
            if (cave === this.wumpus.current) {
                this.appendOutput("You killed the Wumpus! You win!");
                this.wumpus = null;
                return;
            }
            if (!currentCave.connected.includes(cave)) {
                this.appendOutput("The arrow missed and returned to you. You lose.");
                this.player = null;
                return;
            }
            currentCave = cave;
        }
        this.appendOutput("The Wumpus moves!");
        this.wumpus.current = this.randomCave();
    }

    checkConditions() {
        if (this.player.current === this.wumpus.current) {
            this.appendOutput("You've encountered the Wumpus!");
            if (Math.random() < 0.5) {
                this.appendOutput("The Wumpus moves to another cave.");
                this.wumpus.current = this.randomCave();
            } else {
                this.appendOutput("The Wumpus attacks and kills you. You lose.");
                this.player = null;
            }
        } else if (this.superBats.some(b => b.current === this.player.current)) {
            this.appendOutput("Super bats grab you and drop you in a random cave!");
            this.player.current = this.randomCave();
        } else if (this.bottomlessPits.some(p => p.current === this.player.current)) {
            this.appendOutput("You fell into a bottomless pit. You lose.");
            this.player = null;
        }
    }

    gameLoop() {
        while (this.player && this.wumpus) {
            this.describeCave();
            this.playerTurn();
        }
        if (!this.player) {
            this.appendOutput("Game over!");
        }
    }
}

const game = new HuntTheWumpus();
function gameLoop() {
    game.gameLoop();
}
