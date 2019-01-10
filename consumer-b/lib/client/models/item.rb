module Client
  class Item

    attr_reader :sku
    attr_reader :title
    attr_reader :quantityInStock

    def initialize attributes
      @sku = attributes[:sku]
      @title = attributes[:title]
      @quantityInStock = attributes[:quantityInStock]
    end

    def == other
      other.is_a?(Item) && other.sku == self.sku
    end

  end
end
