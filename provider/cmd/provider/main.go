package main

import (
	"github.com/gin-gonic/gin"
	"net/http"
)

func main() {
	router := gin.Default()
	router.GET("/users/:id", usersHandler)
	err := router.Run(":8989")
	if err != nil {
		panic(err)
	}
}

func usersHandler(c *gin.Context) {
	value := c.Param("id")

	if value == "0" {
		c.Status(http.StatusBadRequest)
		return
	}

	if value == "1" {
		c.Status(http.StatusOK)
		return
	}

	c.Status(http.StatusNotFound)
}
