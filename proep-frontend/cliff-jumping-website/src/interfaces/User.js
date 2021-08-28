export class User {
    email = "";
    password = null;
    city = "";
    userType = null;
    experienceLevel = "";
    firstName = "";
    lastName = "";
    country = "";
    idUser = null;

    constructor(idUser, email, city, userType, experienceLevel, firstName, lastName, country) {
        this.email = email;
        this.idUser = idUser;
        // this.password = pass;
        this.city = city;
        this.userType = userType;
        this.experienceLevel = experienceLevel;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
    }
}