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
		c.JSON(http.StatusNotFound, gin.H{
			"user": user{},
		})
	}
}
