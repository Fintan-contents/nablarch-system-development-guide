#!/bin/bash
set -u
docker-compose down
docker-compose build --force-rm
docker-compose up -d
