require_relative 'pact_helper'
require 'client/item_service_client'

module Client
  describe "Contract between ruby-service and api-in-go", :pact => true do

    before do
      ItemServiceClient.base_uri item_service.mock_service_base_url
    end

    describe ".find_item_by_id" do
      context "when an item by the given id exists" do

        before do
          item_service.given("there is an item with id ID_00001").
            upon_receiving("a GET to /items/ID_00001").with(
              method: :get,
              path: '/items/ID_00001',
              headers: {'Accept' => 'application/json'} ).
          will_respond_with(
            status: 200,
            headers: {'Content-Type' => 'application/json;charset=utf-8'},
            body: {
              sku: 'ID_00001',
              title: 'Nice product',
              quantityInStock: 100,
            }
          )
        end

        it "returns the item" do
          expect(ItemServiceClient.find_items_by_id('ID_00001')).to eq Client::Item.new(sku: 'ID_00001', title: 'Nice product', quantityInStock: 100)
        end

      end

      context "when an item by the given id does not exist" do

        before do
          item_service.given("there is not an item with id ID_00002").
            upon_receiving("a GET to /items/ID_00002").with(
              method: :get,
              path: '/items/ID_00002',
              headers: {'Accept' => 'application/json'} ).
          will_respond_with(status: 404)
        end

        it "returns nil" do
          expect(ItemServiceClient.find_items_by_id('ID_00002')).to be_nil
        end

      end
    end
  end
end
