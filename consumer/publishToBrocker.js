let publisher = require('@pact-foundation/pact-node');
let path = require('path');

let opts = {
  providerBaseUrl: 'http://provider',
  pactFilesOrDirs: [path.resolve(process.cwd(), 'pacts')],
  pactBroker: 'http://localhost:3000',
  pactBrokerUsername: 'broker',
  pactBrokerPassword: 'broker',
  consumerVersion: '2.0.0'
};

publisher.publishPacts(opts).then(() => done());
