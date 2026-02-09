#!/bin/bash

# Main knowledge file validation script
# Validates knowledge files using JSON schema validation and integrity checks

set -euo pipefail

# Get script directory
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BASE_DIR="$(cd "$SCRIPT_DIR/../.." && pwd)"
KNOWLEDGE_DIR="$BASE_DIR/knowledge"
SCHEMAS_DIR="$BASE_DIR/schemas"

# Load libraries
source "$SCRIPT_DIR/lib/output-utils.sh"
source "$SCRIPT_DIR/lib/integrity-validator.sh"

# Initialize counters
total_files=0
schema_failed=0
integrity_failed=0
warnings=0
passed=0

# Arrays to track files with issues
declare -a failed_files
declare -a warning_files

# Function to determine category and schema from file path
get_category_and_schema() {
    local filepath="$1"
    local relpath="${filepath#$KNOWLEDGE_DIR/}"

    case "$relpath" in
        overview.json)
            echo "overview|${SCHEMAS_DIR}/overview.schema.json"
            ;;
        releases/*.json)
            echo "releases|${SCHEMAS_DIR}/releases.schema.json"
            ;;
        checks/*.json)
            echo "checks|${SCHEMAS_DIR}/checks.schema.json"
            ;;
        features/processing/*.json)
            echo "processing|${SCHEMAS_DIR}/processing.schema.json"
            ;;
        features/libraries/*.json)
            echo "libraries|${SCHEMAS_DIR}/libraries.schema.json"
            ;;
        features/handlers/*.json|features/handlers/*/*.json)
            echo "handlers|${SCHEMAS_DIR}/handlers.schema.json"
            ;;
        features/adapters/*.json)
            echo "adapters|${SCHEMAS_DIR}/adapters.schema.json"
            ;;
        features/tools/*.json)
            echo "tools|${SCHEMAS_DIR}/tools.schema.json"
            ;;
        *)
            echo "unknown|"
            ;;
    esac
}

# Main validation logic
print_header "Knowledge Files Validation (Schema + Integrity)"

# Find all JSON files in knowledge directory
mapfile -t json_files < <(find "$KNOWLEDGE_DIR" -name "*.json" -type f | sort)
total_files=${#json_files[@]}

if [ $total_files -eq 0 ]; then
    echo "No JSON files found in $KNOWLEDGE_DIR"
    exit 0
fi

# Process each file
for i in "${!json_files[@]}"; do
    json_file="${json_files[$i]}"
    current=$((i + 1))
    relpath="${json_file#$KNOWLEDGE_DIR/}"

    print_file_header "$current" "$total_files" "$relpath"

    # Get category and schema
    IFS='|' read -r category schema_file <<< "$(get_category_and_schema "$json_file")"

    if [ "$category" = "unknown" ] || [ -z "$schema_file" ]; then
        print_warning "Unknown category, skipping schema validation"
        print_warning "Passed with warnings"
        warnings=$((warnings + 1))
        warning_files+=("$relpath: Unknown category")
        continue
    fi

    # Schema validation
    if "$SCRIPT_DIR/validate-schema.sh" "$schema_file" "$json_file" > /dev/null 2>&1; then
        print_success "Schema validation passed"

        # Integrity validation (only if schema validation passed)
        if validate_integrity "$json_file" "$category" "$KNOWLEDGE_DIR"; then
            print_success "Passed"
            passed=$((passed + 1))
        else
            integrity_result=$?
            if [ $integrity_result -eq 1 ]; then
                # Error
                print_error "Failed (integrity checks)"
                integrity_failed=$((integrity_failed + 1))
                failed_files+=("$relpath: Integrity checks failed")
            elif [ $integrity_result -eq 2 ]; then
                # Warning
                print_warning "Passed with warnings"
                warnings=$((warnings + 1))
                warning_files+=("$relpath: Integrity warnings")
            fi
        fi
    else
        # Schema validation failed
        print_error "Schema validation failed"
        # Show actual error
        "$SCRIPT_DIR/validate-schema.sh" "$schema_file" "$json_file" 2>&1 | sed 's/^/    /'
        print_error "Failed (skipped integrity checks)"
        schema_failed=$((schema_failed + 1))
        failed_files+=("$relpath: Schema validation failed")
    fi
done

# Print summary
print_header "Validation Summary"

print_summary_line "Total files" "$total_files"
print_summary_line "Schema validation failed" "$schema_failed" "$RED"
print_summary_line "Integrity checks failed" "$integrity_failed" "$RED"
print_summary_line "Passed with warnings" "$warnings" "$YELLOW"
print_summary_line "Passed" "$passed" "$GREEN"

# Show files with warnings
if [ "$warnings" -gt 0 ] && [ ${#warning_files[@]} -gt 0 ]; then
    echo ""
    echo "Files with warnings:"
    for file in "${warning_files[@]}"; do
        print_warning "$file"
    done
fi

# Show failed files
total_failed=$((schema_failed + integrity_failed))
if [ "$total_failed" -gt 0 ] && [ ${#failed_files[@]} -gt 0 ]; then
    echo ""
    echo "Failed files:"
    for file in "${failed_files[@]}"; do
        print_error "$file"
    done
fi

echo ""

# Exit with error if any validation failed
total_failed=$((schema_failed + integrity_failed))
if [ $total_failed -gt 0 ]; then
    exit 1
fi

exit 0
