#!/bin/bash

# Color codes for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Print functions for consistent output formatting

print_header() {
    local message="$1"
    echo ""
    echo "=========================================="
    echo "$message"
    echo "=========================================="
    echo ""
}

print_success() {
    local message="$1"
    echo -e "  ${GREEN}✓${NC} $message"
}

print_error() {
    local message="$1"
    echo -e "  ${RED}✗${NC} $message"
}

print_warning() {
    local message="$1"
    echo -e "  ${YELLOW}⚠${NC} $message"
}

print_info() {
    local message="$1"
    echo -e "  ${BLUE}ℹ${NC} $message"
}

print_file_header() {
    local current="$1"
    local total="$2"
    local filename="$3"
    echo ""
    echo "[$current/$total] Checking: $filename"
}

print_summary_line() {
    local label="$1"
    local count="$2"
    local color="${3:-}"

    if [ -n "$color" ]; then
        printf "%-30s ${color}%s${NC}\n" "$label:" "$count"
    else
        printf "%-30s %s\n" "$label:" "$count"
    fi
}
