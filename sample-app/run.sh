#!/bin/sh
# Copyright IBM Corp. 2022, 2024
# SPDX-License-Identifier: MPL-2.0


# (re)start application and its dependencies
docker compose down --volumes
docker compose up -d --build
