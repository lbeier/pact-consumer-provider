const axios = require('axios')

exports.getUser = (endpoint, id) => {
  const url = endpoint.url
  const port = endpoint.port

  return axios.request({
    method: 'GET',
    baseURL: `${url}:${port}`,
    url: `/users/${id}`,
    headers: { 'Accept': 'application/json' },
  })
}
