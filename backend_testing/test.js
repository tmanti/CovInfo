const axios = require('axios');

token = "";
id = "";

axios.get('http://192.168.0.163:8080/').then((resp)=>{
    console.log(resp.data);
})

axios.post('http://192.168.0.163:8080/api/auth/login', {username:"root", password:"cp317project"}).then((resp)=>{
  console.log(resp.data);
  token = resp.data.token;
  console.log(token)
  axios.post('http://192.168.0.163:8080/api/resource/add', {name:"dfghadfhadfhafdg", type:'T', date:Date.now, comment:'test'}, {
    headers:{
      'Authorization':token
    }
  }).then((resp)=>{
    console.log(resp.data);
    axios.get('http://192.168.0.163:8080/api/resources/', {
      headers:{
        'Authorization':token
      }
    }).then((resp)=>{
      console.log(resp.data);
      id = resp.data[0].id;
      console.log("id" + id)
      axios.delete('http://192.168.0.163:8080/api/resource/'+id, {
        headers:{
          'Authorization':token
        }
      }).then((resp)=>{
        console.log(resp.data);
        axios.get('http://192.168.0.163:8080/api/resources/', {
          headers:{
            'Authorization':token
          }
        }).then(resp=>{
          console.log(resp.data)
        }).catch(e=>{
          console.log(e.response.data)
        })
      }).catch((e)=>{
        console.log(e.response.data);
      })
    }).catch((e)=>{
      console.log(e.response.data);
    })
  }).catch((e)=>{
    console.log(e.response.data);
  })
}).catch((e)=>{
  console.log(e.response.data);
})