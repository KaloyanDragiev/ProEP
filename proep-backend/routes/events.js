const router = require('express').Router();
var connection = require('../database');

router.get("/events", (req, res, next) => {
    connection.query('SELECT * from event', function (err, result, fields) {
        result.forEach((event) => {
            if (event.image != '') {
                event.image = req.protocol + '://' + req.get('host') + '/' + event.image;
            }

        })
        console.log(result);
        res.json(result);
    })
});

router.get("/events/:name", (req, res, next) => {
    let query = "SELECT * from event where name = '" + req.params.name + "'";

    connection.query(query, function (err, result) {
        result.forEach((event) => {
            if (event.image != '') {
                event.image = req.protocol + '://' + req.get('host') + '/' + event.image;
            }

        })
        console.log(result);
        res.json(result);
    })
});

router.post("/events/create", (req, res, next) => {
    // if(req.session.user) {
    let query = 'INSERT INTO event (name, date, description, JumpingSite_idJumpingSite, image)  VALUES (?, ?, ?, ?, ?)';

    connection.query(query, [req.body.name, req.body.date, req.body.description, req.body.idJumpingSite, req.body.image]);

    query = 'SELECT * from event where idEvent =(SELECT MAX(idEvent ) FROM event)';
    connection.query(query, function (err, result) {
        res.json(result);
    });
});

router.post("/events/create/web", (req, res, next) => {
    // if(req.session.user) {
    let query = 'INSERT INTO event (name, date, description, JumpingSite_idJumpingSite, image, idUser)  VALUES (?, ?, ?, ?, ?, ?)';

    connection.query(query, [req.body.name, req.body.date, req.body.description, req.body.idJumpingSite, req.body.image, req.body.idUser]);

    query = 'SELECT * from event where idEvent =(SELECT MAX(idEvent ) FROM event)';
    connection.query(query, function (err, result) {
        res.json(result);
    });
});

router.delete("/events/delete/:idEvent", (req, res, next) => {
    // if(req.session.user) {
    let query = 'DELETE FROM event WHERE idEvent = ?';

    connection.query(query, [req.params.idEvent]);
    res.status(204).send();
    // }
    // else {
    //     res.status(401).send();
    // }  
});

router.get("/events/getSites/:id", (req, res, next) => {
    connection.query('SELECT js.* from event e JOIN jumpingsite js ON js.idJumpingSite = e.JumpingSite_idJumpingSite WHERE e.idEvent = ?', [req.params.id], function (err, result, fields) {

        console.log(result);
        res.json(result);
    });

});

router.get("/events/getSites", (req, res, next) => {
    connection.query('SELECT js.name, js.coordinates, e.idEvent from event e JOIN jumpingsite js ON js.idJumpingSite = e.JumpingSite_idJumpingSite', function (err, result, fields) {

        console.log(result);
        res.json(result);
    });

});

module.exports = router;