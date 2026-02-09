#!/bin/bash

# Schema validation script using ajv-cli
# This script validates a JSON file against a JSON schema

set -euo pipefail

# Check if ajv is installed
if ! command -v ajv &> /dev/null; then
    echo "Error: ajv-cli is not installed"
    echo "Install with: npm install -g ajv-cli ajv-formats"
    exit 1
fi

# Check arguments
if [ $# -ne 2 ]; then
    echo "Usage: $0 <schema_file> <json_file>"
    exit 1
fi

SCHEMA_FILE="$1"
JSON_FILE="$2"

# Validate that files exist
if [ ! -f "$SCHEMA_FILE" ]; then
    echo "Error: Schema file not found: $SCHEMA_FILE"
    exit 1
fi

if [ ! -f "$JSON_FILE" ]; then
    echo "Error: JSON file not found: $JSON_FILE"
    exit 1
fi

# Run ajv validation
# --strict=false: don't fail on unknown keywords
# -c: reference to common.schema.json for $ref resolution
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SCHEMAS_DIR="$(cd "$SCRIPT_DIR/../.." && pwd)/schemas"

if ! ajv validate -s "$SCHEMA_FILE" -d "$JSON_FILE" --strict=false --spec=draft7 -c ajv-formats -r "${SCHEMAS_DIR}/common.schema.json" 2>&1; then
    exit 1
fi

exit 0
