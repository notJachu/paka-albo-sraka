const express = require('express')
const app = express()
const port = 80

app.get('/', (req, res) => {
  //res.send("Hello World");
    res.sendFile(__dirname + '/front/index.html');
})
app.use(express.static('front'));

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
})
