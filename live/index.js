const express = require('express')
const cookieParser = require('cookie-parser');
const app = express()
const port = 80

app.get('/', (req, res) => {
  //res.send("Hello World");
    res.cookie('userID', 2137);
    res.cookie('origin', 'papaj');
    res.sendFile(__dirname + '/front/index.html');
})
app.use(express.static('front'));
app.use(cookieParser());
app.post('/', express.urlencoded({ extended: true }), (req, res) => {
  const formData = req.body;
  console.log('Form Data:', formData);
  // Check cookies on form submission
  console.log('Cookies:', req.cookies);
  //console.log(req);
  //res.sendFile(__dirname + '/front/response.html');
  res.send(formData);
});

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
})
