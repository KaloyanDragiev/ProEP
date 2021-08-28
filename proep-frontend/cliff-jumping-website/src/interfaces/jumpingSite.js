export class JumpingSide {

    id = null;
    name = "";
    difficultyLevel = "";
    coordinates = "";
    height = null;
    rate = "";
    image = "";
    userId = null;

    constructor(id, name, difficultyLevel, height, coordinates, rate, image, userId) {
        this.id = id;
        this.name = name;
        this.difficultyLevel = difficultyLevel;
        this.height = height;
        this.coordinates = coordinates;
        this.rate = rate;
        this.image = image;
        this.userId = userId;
    }
}