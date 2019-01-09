const path = require('path')
const Pact = require('@pact-foundation/pact').Pact
const { getUser } = require('../src/client')

const ENDPOINT = {
  url: 'http://127.0.0.1',
  port: port
}

describe("Consumer testing", () => {
  beforeEach(() => {
    return provider.addInteraction({
      state: "I have a valid active customer",
      uponReceiving: "The id for valid active customer",
      withRequest: {
        method: "GET",
        path: "/users/1",
        headers: { Accept: "application/json" }
      },
      willRespondWith: {
        status: 200,
        headers: { "Content-Type": "application/json" },
        body: [{
          id: 1,
          name: "Customer name",
          birthday: "1989-02-11",
          isActive: true
        }]
      }
    })
  })

  it("should return customer information if customer active", done => {
    return getUser(ENDPOINT, 1)
      .then(response => {
        console.log(response)

        expect(provider.verify()).to.not.throw()
        done()
      })
      .then(() => provider.verify())
      .catch(err => { console.log(err); done() })
  })
})
