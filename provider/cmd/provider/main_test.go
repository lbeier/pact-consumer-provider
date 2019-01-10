package main

import (
	"fmt"
	"testing"

	"github.com/gin-gonic/gin"

	"github.com/pact-foundation/pact-go/dsl"
	"github.com/pact-foundation/pact-go/types"
)

const (
	PACT_BROKER_HOST     = "http://127.0.0.1:3000"
	PACT_BROKER_USERNAME = "broker"
	PACT_BROKER_PASSWORD = "broker"
	PORT                 = 7777
)

// The actual Provider test itself
func TestPact(t *testing.T) {
	go startServer()

	pact := dsl.Pact{
		Provider: "service-a",
	}

	// Verify the Provider - Latest Published Pacts for any known consumers
	pact.VerifyProvider(t, types.VerifyRequest{
		ProviderBaseURL:            fmt.Sprintf("http://127.0.0.1:%d", PORT),
		BrokerURL:                  PACT_BROKER_HOST,
		ProviderStatesSetupURL:     fmt.Sprintf("http://127.0.0.1:%d/setup", PORT),
		BrokerUsername:             PACT_BROKER_USERNAME,
		BrokerPassword:             PACT_BROKER_PASSWORD,
		PublishVerificationResults: true,
		ProviderVersion:            "1.0.0",
	})
}

func startServer() {
	router := gin.Default()
	router.GET("/users", usersHandler)
	router.POST("/setup", func(c *gin.Context) {
		var state types.ProviderState
		if c.BindJSON(&state) == nil {
			fmt.Println(state)
		}
	})

	err := router.Run(fmt.Sprintf(":%d", PORT))
	if err != nil {
		panic(err)
	}
}
