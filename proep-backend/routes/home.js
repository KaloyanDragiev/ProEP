const router = require('express').Router();

router.get('/home', function(request, response) {
	if (request.session.loggedin) {
		response.send('Welcome back, ' + request.session.id + '!');
	} else {
		response.send('Please login to view this page!');
	}
	response.end();
});

module.exports = router;