package main

import (
	"github.com/gin-gonic/gin"
	"net/http"
)

func main() {
	router := gin.Default()
	router.GET("/users/:id", usersHandler)
	router.GET("/items/:sku", itemsHandler)
	err := router.Run(":8989")
	if err != nil {
		panic(err)
	}
}

type user struct {
	ID       int    `json:"id"`
	Name     string `json:"name"`
	Birthday string `json:"birthday"`
	IsActive bool   `json:"isActive"`
}

func usersHandler(c *gin.Context) {
	value := c.Param("id")

	switch value {
	case "0":
		c.Status(http.StatusBadRequest)
	case "1":
		user := &user{
			ID:       1,
			Name:     "Customer name",
			Birthday: "1989-02-11",
			IsActive: true,
		}
		c.JSON(http.StatusOK, user)
	default:
		c.JSON(http.StatusNotFound, user{})
	}
}

type item struct {
	SKU             string `json:"sku"`
	Title           string `json:"title"`
	QuantityInStock int    `json:"quantityInStock"`
}

func itemsHandler(c *gin.Context) {
	value := c.Param("sku")

	switch value {
	case "ID_00002":
		c.JSON(http.StatusNotFound, item{})
	case "ID_00001":
		item := &item{
			SKU:             "ID_00001",
			Title:           "Nice product",
			QuantityInStock: 100,
		}
		c.JSON(http.StatusOK, item)
	default:
		c.Status(http.StatusBadRequest)
	}
}
