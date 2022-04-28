#!/bin/bash
# file: deploy.sh
echo "====== Build & U.Test ======"
./mvnw clean install package -DskipTests=true
echo "====== Build Image Docker ======"
docker build . --tag api-exchange-rate
echo "====== Post Execution ======"
docker compose up -d

