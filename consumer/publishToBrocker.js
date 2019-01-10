let publisher = require('@pact-foundation/pact-node')
let path = require('path')

let opts = {
  providerBaseUrl: 'http://127.0.0.1:8989',
  pactFilesOrDirs: [path.resolve(process.cwd(), 'pacts')],
  pactBroker: 'http://127.0.0.1:3000',
  pactBrokerUsername: 'broker',
  pactBrokerPassword: 'broker',
  consumerVersion: revision = require('child_process').execSync('git rev-parse HEAD').toString().trim()
}

publisher.publishPacts(opts).then(() => done())
