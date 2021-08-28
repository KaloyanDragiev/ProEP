var mysql = require('mysql');

const connection = mysql.createConnection({
  host: '10.43.209.132',
  user: 'admin',
  password: '[E].>/|(QGK7}S_m',
  database: 'cliffing'

});

connection.connect(function (err) {
  if (err) {
    console.error('error connecting: + ' + err.stack);
    return;
  }
  console.log('Connected as id ' + connection.threadId);
});

module.exports = connection;