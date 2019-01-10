require_relative '../spec_helper'
require 'pact/consumer/rspec'

Pact.configure do | config |
  config.doc_generator = :markdown
end

Pact.service_consumer 'ruby-service' do
  has_pact_with 'api-in-go' do
    mock_service :item_service do
      port 8888
      pact_specification_version "2.0.0"
    end
  end
end
