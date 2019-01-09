const path = require('path')
const Pact = require('@pact-foundation/pact').Pact

global.port = 9999
global.provider = new Pact({
  consumer: "A JS consumer",
  provider: "A Golang provider",
  port: global.port,
  pactfileWriteMode: 'update',
  log: path.resolve(process.cwd(), 'logs', 'mockserver-integration.log'),
  dir: path.resolve(process.cwd(), "pacts"),
  pactfileWriteMode: 'update',
  spec: 2,
})
