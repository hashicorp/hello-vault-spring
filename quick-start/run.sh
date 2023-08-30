#!/bin/bash
# Copyright (c) HashiCorp, Inc.
# SPDX-License-Identifier: MPL-2.0
set -euo pipefail
IFS=$'\n\t'

echo "Starting Vault dev server.."
container_id=$(docker run --rm --detach -p 8200:8200 -e 'VAULT_DEV_ROOT_TOKEN_ID=dev-only-token' hashicorp/vault:latest)

echo "Running quickstart example."
mvn clean package
java -jar target/quickstart.jar

echo "Stopping Vault dev server.."
docker stop "${container_id}" > /dev/null

echo "Vault server has stopped."
