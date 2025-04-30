const express = require('express');
require('dotenv').config();
const cookieParser = require('cookie-parser');
const app = express()
const port = 80

const save_path ='./results.json';
const fs = require('fs');
var save_file = require(save_path);


var daily_state = {
  data: {
    paka: 0,
    sraka: 0,
  },
  timestamp: 0,
};

function loadResults(){
  daily_state = save_file;
}

async function saveResults() {
  daily_state.timestamp = Date.now();

  fs.writeFile(save_path, JSON.stringify(daily_state, null, 2), (err) => {
    if (err) {
      console.error('Error saving results:', err);
    } else {
      console.log('Results saved successfully.');
    }
  });
}

setInterval(saveResults, 5 * 60 * 1000); // Save every 5 minutes

app.use(cookieParser());

app.get('/', (req, res) => {

  if (req.cookies['from-wife']) {
    res.sendFile(__dirname + '/front/thanks.html');
  }
  else {
    res.sendFile(__dirname + '/front/index.html');
  }
});

app.get('/daily', (req, res) => {
  //res.sendFile(__dirname + '/results.json');
  res.json(daily_state);
});

app.get('/results', (req, res) => {
  res.sendFile(__dirname + '/front/results.html');
});


async function verifyCaptcha(captchaResponse) {
  params = new URLSearchParams({
    secret: process.env.CAPTCHA_SECRET_KEY,
    response: captchaResponse
  });

  const response = await fetch('https://www.google.com/recaptcha/api/siteverify', {
    method: 'POST',
    body: params
  });
  const data = await response.json();
  if (data.success) {
    return true;
  } else {
    console.error('Captcha verification failed:', data['error-codes']);
    return false;
  }
}

app.post('/', express.urlencoded({ extended: true }), (req, res) => {
  const formData = req.body;
  console.log('Form Data:', formData);
  captchaResponse = formData['g-recaptcha-response'];
  if (!captchaResponse) {
    res.status(400).send('Captcha response is missing.');
    return;
  }

  if (!verifyCaptcha(captchaResponse)) {
    res.status(403).send('Captcha verification failed.');
    return;
  }

  if (formData.vote === 'PAKA') {
    daily_state.data.paka++;
  } else if (formData.vote === 'SRAKA') {
    daily_state.data.sraka++;
  } else {
    console.error('Invalid vote:', formData.vote);
  }
   // vote has been accepted so timeout is set for 1 hour
   res.cookie('from-wife', Date.now(), { maxAge: 1000*60 }); // 1 hour
   res.redirect('results');
});

app.use(express.static('front/static'));


app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
  loadResults();
  console.log(`Loaded results: PAKA: ${daily_state.data.paka}, SRAKA: ${daily_state.data.sraka}`);
})

