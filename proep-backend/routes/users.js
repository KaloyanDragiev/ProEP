const router = require('express').Router();
var connection = require('../database');

router.get("/users", (req, res, next) => {
    connection.query('SELECT * from user', function (err, result, fields) {
    console.log(result);
    res.json(result);
    })
});
  
router.get("/users/:idUser", (req, res, next) => {
    let query = "SELECT * from user where idUser = '" + req.params.idUser + "'";
    console.log(query);
    connection.query(query, function (err, result) {
    console.log(result);
    res.json(result);
    })
});

router.put("/users/:idUser", (req, res, next) => {
    let query = "SELECT * from user where idUser = '" + req.params.idUser + "'";
    console.log(query);
    connection.query(query, function (err, result) {
    console.log(result);
    res.json(result);
    })
});


router.delete("/users/delete/:idUser", (req, res, next) => {
    // if(req.session.user && req.session.user.userType == "Admin") {

        let query = 'DELETE FROM user WHERE idUser = ?'; 

        connection.query(query, [req.params.idUser]);
        res.status(204).send(); 
    // }
    // else {
    //     res.status(401).send();
    // }
});

module.exports = router;