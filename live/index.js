const express = require('express')
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

app.get('/', (req, res) => {
  res.sendFile(__dirname + '/front/index.html');
});

app.get('/daily', (req, res) => {
  //res.sendFile(__dirname + '/results.json');
  res.json(daily_state);
});

app.get('/results', (req, res) => {
  res.sendFile(__dirname + '/front/results.html');
});


app.post('/', express.urlencoded({ extended: true }), (req, res) => {
  const formData = req.body;
  console.log('Form Data:', formData);
  res.redirect('results');
  if (formData.vote === 'PAKA') {
    daily_state.data.paka++;
  } else if (formData.vote === 'SRAKA') {
    daily_state.data.sraka++;
  } else {
    console.error('Invalid vote:', formData.vote);
  }
});

app.use(express.static('front/static'));


app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
  loadResults();
  console.log(`Loaded results: PAKA: ${daily_state.data.paka}, SRAKA: ${daily_state.data.sraka}`);
})

