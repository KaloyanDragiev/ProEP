const router = require('express').Router();
var connection = require('../database');

router.get("/news", (req, res, next) => {
    connection.query('SELECT * from article', function (err, result, fields) {
    res.json(result);
    })
});

router.post("/news/create", (req, res, next) => {
    // if(req.session.user && req.session.user.userType == "Admin") {

        let query = 'INSERT INTO article (title, body, timePublished, User_idUser, image)  VALUES (?, ?, ?, ?, ?)';
        let datetimenow = new Date().toISOString().slice(0, 19).replace('T', ' ');

        connection.query(query, [req.body.title, req.body.body, datetimenow, req.session.user.idUser, req.body.image]);
        res.status(201).send();
    // }
    // else {
    //     res.status(401).send();
    // }
});

router.delete("/news/delete/:idArticle", (req, res, next) => {
    // if(req.session.user && req.session.user.userType == "Admin") {

        let query = 'DELETE FROM article WHERE idArticle = ?'; 

        connection.query(query, [req.params.idArticle]);
        res.status(204).send(); 
    // }
    // else {
    //     res.status(401).send();
    // }
});

module.exports = router;