export class Article {

    id = null;
    title = "";
    body = "";
    timePublished = null;
    userId = null;
    image = "";
    constructor(id, title, body, timePublished, userdId, image) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.timePublished = timePublished;
        this.userId = userdId;
        this.image = image;
    }
}