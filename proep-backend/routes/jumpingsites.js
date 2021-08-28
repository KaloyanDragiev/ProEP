const router = require('express').Router();
var connection = require('../database');
const multer = require('multer');

const storage = multer.diskStorage({
    destination: function (req, file, cb) {
        cb(null, './uploads');
    },
    filename: function (req, file, cb) {
        cb(null, file.originalname)
    }
});
const upload = multer({
    storage: storage
});

router.get("/jumpingsites", (req, res, next) => {
    connection.query('SELECT * from jumpingsite', function (err, result, fields) {
        result.forEach((site) => {
            if (site.image != '') {
                site.image = req.protocol + '://' + req.get('host') + '/' + site.image;
            }

        })
        console.log(result);
        res.json(result);

    })
});

router.get("/jumpingsites/:name", (req, res, next) => {

    let query = "SELECT * from jumpingsite where name = '" + req.params.name + "'";
    connection.query(query, function (err, result) {
        result.forEach((site) => {
            if (site.image != '') {
                site.image = req.protocol + '://' + req.get('host') + '/' + site.image;
            }
        })
        res.json(result);
    })
});

router.get("/jumpingsite/:id", (req, res, next) => {

    let query = "SELECT * from jumpingsite where idJumpingSite = '" + req.params.id + "'";
    connection.query(query, function (err, result) {
        result.forEach((site) => {
            if (site.image != '') {
                site.image = req.protocol + '://' + req.get('host') + '/' + site.image;
            }

        })
        console.log(result);
        res.json(result);
    })
});

router.get("/jumpingsites/:id/logs", (req, res, next) => {
    let query = 'SELECT * from log where JumpingSite_idJumpingSite = ?';
    connection.query(query, [req.params.id], function (err, result) {
        res.json(result);
    });
})

router.get("/jumpingsites/:id/logs/:idLog", (req, res, next) => {
    let query = 'SELECT * from log where JumpingSite_idJumpingSite = ? and idLog = ?';
    connection.query(query, [req.params.id, req.params.idLog], function (err, result) {
        res.json(result);
    });
})

router.post("/jumpingsites/:id/logs/web", (req, res, next) => {
    // if(req.session.user) {
    let query = 'INSERT INTO log (logDescription, rating, dateOfLog, User_idUser, JumpingSite_idJumpingSite)  VALUES (?, ?, ?, ?, ?)';
    let datetimenow = new Date().toISOString().slice(0, 19).replace('T', ' ');
    console.log(req.body.rating);
    connection.query(query, [req.body.logDescription, req.body.rating, datetimenow, req.body.idUser, req.params.id]);

    query = 'SELECT * from log where JumpingSite_idJumpingSite = ? and logId=(SELECT MAX(logId) FROM log)';
    connection.query(query, [req.params.id, req.params.idLog], function (err, result) {
        res.json(result);
    });

})

router.post("/jumpingsites/:id/logs", (req, res, next) => {
    // if(req.session.user) {
    let query = 'INSERT INTO log (logDescription, rating, dateOfLog, User_idUser, JumpingSite_idJumpingSite)  VALUES (?, ?, ?, ?, ?)';
    let datetimenow = new Date().toISOString().slice(0, 19).replace('T', ' ');
    console.log(req.body.rating);
    connection.query(query, [req.body.logDescription, req.body.rating, datetimenow, req.session.user.idUser, req.params.id]);

    res.status(201).send();

    // }
    // else {
    //     res.status(401).send();
    // }
})


router.post("/jumpingsites/create", (req, res, next) => {
    // if(req.session.user) {
    // console.log(req.file);
    let query = 'INSERT INTO jumpingsite (name, difficultyLevel, coordinates, height, rate, image, User_idUser)  VALUES (?, ?, ?, ?, ?, ?, ?)';

    connection.query(query, [req.body.name, req.body.difficultyLevel, req.body.coordinates, req.body.height, req.body.rate, "", req.body.idUser]);
    res.status(201).send();
    // }
    // else {
    //     res.status(401).send();
    // }

});

router.post("/jumpingsites/create/web", upload.single('image'), (req, res, next) => {
    // if(req.session.user) {
    console.log(req.file);

    let query = 'INSERT INTO jumpingsite (name, difficultyLevel, coordinates, height, rate, image, User_idUser)  VALUES (?, ?, ?, ?, ?, ?, ?)';

    connection.query(query, [req.body.name, req.body.difficultyLevel, req.body.coordinates, req.body.height, req.body.rate, req.file.path, req.body.idUser]);


    query = 'SELECT * from jumpingsite where idJumpingSite=(SELECT MAX(idJumpingSite) FROM jumpingsite)';
    connection.query(query, [req.params.id, req.params.idLog], function (err, result) {
        res.json(result);
    });

    // }
    // else {
    //     res.status(401).send();
    // }

});
router.post("/jumpingsites/create/web/jsonbody", (req, res, next) => {

    let query = 'INSERT INTO jumpingsite (name, difficultyLevel, coordinates, height, rate, image, User_idUser)  VALUES (?, ?, ?, ?, ?, ?, ?)';

    connection.query(query, [req.body.name, req.body.difficultyLevel, req.body.coordinates, req.body.height, req.body.rate, req.body.image, req.body.idUser]);

    query = 'SELECT * from jumpingsite where idJumpingSite=(SELECT MAX(idJumpingSite) FROM jumpingsite)';
    connection.query(query, [req.params.id, req.params.idLog], function (err, result) {
        res.json(result);
    });

});

// Upload Image route
router.post('/jumpingsites/upload', upload.single('file'), (req, res) => {
    res.json({
        file: req.file
    });
});

router.delete("/jumpingsites/delete/:idJumpingSite", (req, res, next) => {
    // if(req.session.user) {

    let query = 'DELETE FROM log WHERE JumpingSite_idJumpingSite = ?';

    connection.query(query, [req.params.idJumpingSite]);

    query = 'DELETE FROM jumpingsite WHERE idJumpingSite = ?';

    connection.query(query, [req.params.idJumpingSite]);
    res.status(204).send();
    // }
    // else {
    //     res.status(401).send();
    // }  
});

router.delete("/jumpingsites/delete/web/:idJumpingSite", (req, res, next) => {
    // if(req.session.user) {

    let query = 'DELETE FROM log WHERE JumpingSite_idJumpingSite = ?';

    connection.query(query, [req.params.idJumpingSite]);

    query = 'DELETE FROM jumpingsite WHERE idJumpingSite = ?';

    connection.query(query, [req.params.idJumpingSite]);

    query = 'DELETE FROM event WHERE JumpingSite_idJumpingSite = ?';

    connection.query(query, [req.params.idJumpingSite]);
    res.status(204).send();
    // }
    // else {
    //     res.status(401).send();
    // }  
});

router.delete("/jumpingsites/log/:logId", (req, res, next) => {
    // if(req.session.user) {
    let query = 'DELETE FROM log WHERE logId = ?';
    connection.query(query, [req.params.logId]);
    res.status(204).send();
    // }
    // else {
    //     res.status(401).send();
    // }  
});
//Need to discuss this with Front-End what to update ?

router.put("/jumpingsites/edit", (req, res, next) => {

    let query = 'UPDATE jumpingsite SET name = ?, difficultyLevel = ?, height = ?, image=?, coordinates = ? WHERE idJumpingSite = ?';
    connection.query(query, [req.body.name, req.body.difficultyLevel, req.body.height, req.body.image, req.body.coordinates, req.body.idJumpingSite]);

    res.status(204).send();

    // Template for Create JumpingSItes

    // let query = 'INSERT INTO jumpingsite (name, difficultyLevel, coordinates, height, rate, image, User_idUser)  VALUES (?, ?, ?, ?, ?, ?, ?)';
    // connection.query(query, [req.body.name, req.body.difficultyLevel, req.body.coordinates, req.body.height, req.body.rate, req.body.image, req.body.idUser]);
    // query = 'SELECT * from jumpingsite where idJumpingSite=(SELECT MAX(idJumpingSite) FROM jumpingsite)';
    // connection.query(query, [req.params.id, req.params.idLog], function (err, result) {
    //     res.json(result);
    // });
});

module.exports = router;