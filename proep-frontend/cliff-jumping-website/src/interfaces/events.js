export class Event {

    id = null;
    name = "";
    date = "";
    description = "";
    jumpingSiteId = null;
    image = "";
    idUser = null;

    constructor(id, name, date, description, jumpingSiteId, image, idUser) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.description = description;
        this.jumpingSiteId = jumpingSiteId;
        this.image = image;
        this.idUser = idUser
    }
}