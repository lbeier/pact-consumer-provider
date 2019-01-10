require 'httparty'
require 'client/models/item'

module Client
  class ItemServiceClient
    include HTTParty
    base_uri 'http://itemsservice'

    def self.find_items_by_id id
      response = get("/items/#{id}", :headers => {'Accept' => 'application/json'})
      when_successful(response) do
        Client::Item.new(parse_body(response))
      end
    end

    def self.when_successful response
      if response.success?
        yield
      elsif response.code == 404
        nil
      else
        raise response.body
      end
    end

    def self.parse_body response
      JSON.parse(response.body, {:symbolize_names => true})
    end
  end
end
