export class Log {
    logId = null;
    description = "";
    rating = 0;


    constructor(id, description, rating) {
        this.logId = id;
        this.description = description;
        this.rating = rating;
    }
}