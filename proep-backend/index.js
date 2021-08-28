var express = require("express");
var session = require('client-sessions');
var bodyParser = require('body-parser');
var cors = require('cors');

var port = process.env.PORT || 3000
var app = express();

app.use(session({
	cookieName: "session",
	secret: 'secret',
	duration: 30 * 60 * 1000, //30 min
	//httpOnly: true,
	ephemeral: true
}));

app.use('/uploads', express.static('uploads'));

app.use(bodyParser.urlencoded({
	extended: false
}));
app.use(bodyParser.json());

app.use(function (req, res, next) {
	res.header("Access-Control-Allow-Origin", "*"); // update to match the domain you will make the request from
	res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
	res.header("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");
	next();
});

//Login, Register, Logout + hashing the password with bcrypt!
const authRoute = require('./routes/auth');
app.use('/', authRoute);

//Users
const userRoute = require('./routes/users');
app.use('/', userRoute);

//Jumping Sites
const jumpingRoute = require('./routes/jumpingsites');
app.use('/', jumpingRoute);

//Events
const eventRoute = require('./routes/events');
app.use('/', eventRoute);

//Home
const homeRoute = require('./routes/home');
app.use('/', homeRoute);

//News
const newsRoute = require('./routes/news');
app.use('/', newsRoute);

app.listen(port, () => {
	console.log("Server running on port 3000");

});