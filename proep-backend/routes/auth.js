const router = require('express').Router();
var connection = require('../database');
const bcrypt = require('bcryptjs');

router.post('/login', async function (request, response) {

	connection.query('SELECT * FROM user WHERE email = ?', [request.body.email], function (error, results, fields) {
		if (results.length > 0 && bcrypt.compareSync(request.body.password, results[0].password)) {
			//request.session.loggedin = true;
			request.session.user = results[0];
			response.setHeader('X-Authenticated', 'true');
			delete results[0].password;
			response.json(results);
		} else {
			response.status(401).send();
		}
	});
});

router.post('/register', async function (request, response) {
	connection.query('SELECT email FROM user WHERE email = ?', [request.body.email],
		(error, results) => {
			if (results.length > 0) {
				response.status(422).send();
				response.send('The Email is already in use!');
				return;
			} else {
				let hashedPassword = bcrypt.hashSync(request.body.password, 14);
				request.body.password = hashedPassword;

				connection.query('INSERT INTO user (firstName, lastName, password, email, userType)  VALUES (?, ?, ?, ?, ?)', [request.body.firstName, request.body.lastName, hashedPassword, request.body.email, request.body.userType]);
				response.status(201).send();
			}
		});
});


router.post('/register/web', async function (request, response) {
	connection.query('SELECT email FROM user WHERE email = ?', [request.body.email],
		(error, results) => {
			if (results.length > 0) {
				response.status(422).send();
				return;
			} else {
				let storePassword = request.body.password;
				let hashedPassword = bcrypt.hashSync(request.body.password, 14);
				request.body.password = hashedPassword;

				connection.query('INSERT INTO user (firstName, lastName, password, country, city, email, experienceLevel)  VALUES (?, ?, ?, ?, ?, ?, ?)', [request.body.firstName, request.body.lastName, hashedPassword, request.body.country, request.body.city, request.body.email, request.body.experienceLevel]);

				connection.query('SELECT * FROM user WHERE email = ?', [request.body.email], function (error, results, fields) {
					if (results.length > 0 && bcrypt.compareSync(storePassword, results[0].password)) {
						//request.session.loggedin = true;
						console.log(results);
						request.session.user = results[0];
						response.setHeader('X-Authenticated', 'true');
						response.setHeader('Access-Control-Allow-Origin', 'https://cliffing-proep.web.app/');
						response.setHeader('Access-Control-Allow-Credentials', 'true');
						response.setHeader('Access-Control-Allow-Methods: GET, POST, OPTIONS');
						response.setHeader("Access-Control-Allow-Headers: Content-Type, Authorization");
						delete results[0].password;
						response.json(results);
					} else {
						response.status(401).send();
					}
				});

			}
		});
});

router.get('/logout', (request, response) => {
	request.session.reset();
	response.status(204).send();
});

module.exports = router;