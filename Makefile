.PHONY: start-build stop setup-kafka-connect help

# Default target
help:
	@echo "Available commands:"
	@echo "  start-build   - Build and start all microservices and infrastructure"
	@echo "  stop          - Stop all running containers"
	@echo "  setup-kafka-connect - Setup Kafka Connect connections"
	@echo "  help          - Show this help message"

# Start all services
start:
	@echo "🚀 Starting all microservices and infrastructure..."
	@cd infrastructure/scripts && ./start.sh

# Build and start all services
start-build:
	@echo "🚀 Building and starting all microservices and infrastructure..."
	@cd infrastructure/scripts && ./start.sh build

# Stop all services
stop:
	@echo "🛑 Stopping all running containers..."
	@cd infrastructure/scripts && ./stop.sh

# Setup Kafka Connect connections
setup-kafka-connect:
	@echo "🔗 Setting up Kafka Connect connections..."
	@cd infrastructure/scripts && ./setup-kafka-connect-connections.sh
