const express = require('express')
const cookieParser = require('cookie-parser');
const app = express()
const port = 80

const save_path ='./results.json';
const fs = require('fs');
const { type } = require('os');
var save_file = require(save_path);

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

  // parse data
  console.log(save_file);
  console.log(typeof save_file.place);
  console.log(typeof save_file.place[0]);
  if (save_file.timestamp) {
    const lastTimestamp = new Date(save_file.timestamp);
    const currentTimestamp = new Date();
    const timeDifference = currentTimestamp - lastTimestamp; // Difference in milliseconds
    console.log(`Time since last timestamp: ${timeDifference} ms`);
  } else {
    console.log('No previous timestamp found.');
  }
  save_file.timestamp = new Date().toString();

  // Save data
  fs.writeFile
  (save_path, JSON.stringify(save_file), (err) => {
    if (err) throw err;
    console.log('Data has been saved!');
  });
});

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
})
