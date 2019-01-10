package main

import (
	"fmt"
	"github.com/pact-foundation/pact-go/utils"
	"testing"

	"github.com/gin-gonic/gin"

	"github.com/pact-foundation/pact-go/dsl"
	"github.com/pact-foundation/pact-go/types"
)

const (
	logDir             = "./logs"
	providerName       = "api-in-go"
	providerVersion    = "1.0.0"
	pactBrokerHost     = "http://localhost:3000"
	pactBrokerUsername = "broker"
	pactBrokerPassword = "broker"
)

// The actual Provider test itself
func TestPact(t *testing.T) {
	port, err := utils.GetFreePort()
	if err != nil {
		t.Fatal(err)
	}

	go startServer(port)

	pact := dsl.Pact{
		Provider: providerName,
		LogDir:   logDir,
		DisableToolValidityCheck: true,
	}

	pact.VerifyProvider(t, types.VerifyRequest{
		ProviderBaseURL:            fmt.Sprintf("http://127.0.0.1:%d", port),
		BrokerURL:                  pactBrokerHost,
		ProviderStatesSetupURL:     fmt.Sprintf("http://127.0.0.1:%d/setup", port),
		BrokerUsername:             pactBrokerUsername,
		BrokerPassword:             pactBrokerPassword,
		PublishVerificationResults: true,
		ProviderVersion:            providerVersion,
	})
}

func startServer(port int) {
	router := gin.Default()
	router.GET("/users/:id", usersHandler)
	router.GET("/items/:sku", itemsHandler)
	router.POST("/setup", func(c *gin.Context) {})

	err := router.Run(fmt.Sprintf(":%d", port))
	if err != nil {
		panic(err)
	}
}
