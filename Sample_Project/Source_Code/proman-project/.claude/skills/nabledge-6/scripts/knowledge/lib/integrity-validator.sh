#!/bin/bash

# Integrity validation library
# This script provides functions to check semantic consistency that cannot be validated by JSON schema alone

# Check index-sections consistency
# All IDs in index must exist in sections, and vice versa
check_index_sections_consistency() {
    local json_file="$1"
    local has_error=0

    # Extract index IDs
    local index_ids=$(jq -r '.index[].id' "$json_file" 2>/dev/null | sort)

    # Extract sections keys
    local section_keys=$(jq -r '.sections | keys[]' "$json_file" 2>/dev/null | sort)

    # Check if all index IDs exist in sections
    while IFS= read -r id; do
        if ! echo "$section_keys" | grep -qx "$id"; then
            print_error "Index-sections inconsistency: index id '$id' not found in sections"
            has_error=1
        fi
    done <<< "$index_ids"

    # Check for extra keys in sections (warning only)
    while IFS= read -r key; do
        if ! echo "$index_ids" | grep -qx "$key"; then
            print_warning "Sections has extra key '$key' not in index"
        fi
    done <<< "$section_keys"

    if [ $has_error -eq 0 ]; then
        print_success "Index-sections consistency OK"
    fi

    return $has_error
}

# Check filename-id match
# Filename should match the id field (with special handling for releases)
check_filename_id_match() {
    local json_file="$1"
    local category="$2"
    local has_error=0

    local filename=$(basename "$json_file" .json)
    local id=$(jq -r '.id' "$json_file" 2>/dev/null)

    # Special handling for releases: 6u3.json -> release-6u3
    if [ "$category" = "releases" ]; then
        local expected_id="release-${filename}"
        if [ "$id" != "$expected_id" ]; then
            print_error "Filename-id mismatch: filename '$filename' but id is '$id' (expected '$expected_id')"
            has_error=1
        fi
    else
        # Other categories: filename should match id exactly
        if [ "$id" != "$filename" ]; then
            print_error "Filename-id mismatch: filename '$filename' but id is '$id'"
            has_error=1
        fi
    fi

    if [ $has_error -eq 0 ]; then
        print_success "Filename-id match OK"
    fi

    return $has_error
}

# Check changes numbering (releases only)
# Changes array should have consecutive numbering starting from 1
check_changes_numbering() {
    local json_file="$1"
    local has_warning=0

    # Get all change numbers
    local numbers=$(jq -r '.sections.changes[].no' "$json_file" 2>/dev/null | sort -n)

    if [ -z "$numbers" ]; then
        return 0
    fi

    local prev=0
    while IFS= read -r num; do
        if [ $prev -ne 0 ] && [ $num -ne $((prev + 1)) ]; then
            print_warning "Changes numbering: gap detected (no: $prev → $num)"
            has_warning=1
        fi
        prev=$num
    done <<< "$numbers"

    # Check if starts from 1
    local first=$(echo "$numbers" | head -n 1)
    if [ "$first" -ne 1 ]; then
        print_warning "Changes numbering: does not start from 1 (starts from $first)"
        has_warning=1
    fi

    if [ $has_warning -eq 0 ]; then
        print_success "Changes numbering OK"
    fi

    return $has_warning
}

# Check impact-impact_detail consistency (releases only)
# If impact is "あり*", impact_detail should be non-empty
check_impact_consistency() {
    local json_file="$1"
    local has_error=0

    # Check each change item
    local count=$(jq '.sections.changes | length' "$json_file" 2>/dev/null)

    for ((i=0; i<count; i++)); do
        local impact=$(jq -r ".sections.changes[$i].impact" "$json_file" 2>/dev/null)
        local impact_detail=$(jq -r ".sections.changes[$i].impact_detail // \"\"" "$json_file" 2>/dev/null)
        local no=$(jq -r ".sections.changes[$i].no" "$json_file" 2>/dev/null)

        if [[ "$impact" =~ ^あり ]]; then
            if [ -z "$impact_detail" ] || [ "$impact_detail" = "null" ]; then
                print_error "Impact consistency: change no.$no has impact='$impact' but empty impact_detail"
                has_error=1
            fi
        fi
    done

    if [ $has_error -eq 0 ]; then
        print_success "Impact consistency OK"
    fi

    return $has_error
}

# Check IPA reference pattern (checks only)
# Reference should match pattern: number-(lowercase-roman)(-letter)?
# Examples: 1-(i)-a, 10-(ii), 5-(ix)
check_ipa_reference_pattern() {
    local json_file="$1"
    local has_warning=0

    # Get all references from check_items
    local references=$(jq -r '.sections.check_items[].items[].reference' "$json_file" 2>/dev/null | grep -v '^$' | sort -u)

    if [ -z "$references" ]; then
        return 0
    fi

    while IFS= read -r ref; do
        # Pattern: digit(s)-(lowercase-roman)(-letter)?
        if ! [[ "$ref" =~ ^[0-9]+-\([ivxl]+\)(-[a-z])?$ ]]; then
            print_warning "IPA reference pattern: '$ref' does not match expected pattern (digit)-(roman)(-letter)?"
            has_warning=1
        fi
    done <<< "$references"

    if [ $has_warning -eq 0 ]; then
        print_success "IPA reference pattern OK"
    fi

    return $has_warning
}

# Check class name pattern (handlers, adapters)
# Class name should be fully qualified (nablarch.*)
check_class_name_pattern() {
    local json_file="$1"
    local has_warning=0

    local class_name=$(jq -r '.sections.overview.class_name // ""' "$json_file" 2>/dev/null)

    if [ -z "$class_name" ] || [ "$class_name" = "null" ]; then
        return 0
    fi

    if ! [[ "$class_name" =~ ^nablarch\. ]]; then
        print_warning "Class name pattern: '$class_name' does not start with 'nablarch.'"
        has_warning=1
    fi

    if [ $has_warning -eq 0 ]; then
        print_success "Class name pattern OK"
    fi

    return $has_warning
}

# Check module pattern (libraries, handlers, adapters, tools)
# groupId should be com.nablarch.*, artifactId should be nablarch-*
check_module_pattern() {
    local json_file="$1"
    local has_warning=0

    # Check if modules exists and is an array
    local modules_type=$(jq -r '.sections.modules | type' "$json_file" 2>/dev/null)

    if [ "$modules_type" != "array" ]; then
        # modules is either null, object, or doesn't exist - skip check
        return 0
    fi

    local module_count=$(jq '.sections.modules | length' "$json_file" 2>/dev/null)

    if [ "$module_count" -eq 0 ]; then
        return 0
    fi

    for ((i=0; i<module_count; i++)); do
        local groupId=$(jq -r ".sections.modules[$i].groupId // empty" "$json_file" 2>/dev/null)
        local artifactId=$(jq -r ".sections.modules[$i].artifactId // empty" "$json_file" 2>/dev/null)

        # Skip if groupId or artifactId is empty
        if [ -z "$groupId" ] || [ -z "$artifactId" ]; then
            continue
        fi

        if ! [[ "$groupId" =~ ^com\.nablarch\. ]]; then
            print_warning "Module pattern: groupId '$groupId' does not start with 'com.nablarch.'"
            has_warning=1
        fi

        if ! [[ "$artifactId" =~ ^nablarch- ]]; then
            print_warning "Module pattern: artifactId '$artifactId' does not start with 'nablarch-'"
            has_warning=1
        fi
    done

    if [ $has_warning -eq 0 ]; then
        print_success "Module pattern OK"
    fi

    return $has_warning
}

# Check version consistency (overview vs releases)
# Overview.json's current.version should match existing release files
check_version_consistency() {
    local json_file="$1"
    local knowledge_dir="$2"
    local has_error=0

    # Only run this check for overview.json
    if [[ ! "$json_file" =~ overview\.json$ ]]; then
        return 0
    fi

    local current_version=$(jq -r '.sections.versioning.current.version' "$json_file" 2>/dev/null)

    if [ -z "$current_version" ] || [ "$current_version" = "null" ]; then
        return 0
    fi

    # Check if corresponding release file exists
    local release_file="${knowledge_dir}/releases/${current_version}.json"

    if [ ! -f "$release_file" ]; then
        print_error "Version consistency: current version '$current_version' but no release file 'releases/${current_version}.json' found"
        has_error=1
    else
        print_success "Version consistency OK"
    fi

    return $has_error
}

# Main validation function
# Validates integrity based on category
validate_integrity() {
    local json_file="$1"
    local category="$2"
    local knowledge_dir="$3"
    local has_error=0
    local has_warning=0

    # All categories: check index-sections consistency and filename-id match
    if ! check_index_sections_consistency "$json_file"; then
        has_error=1
    fi

    if ! check_filename_id_match "$json_file" "$category"; then
        has_error=1
    fi

    # Category-specific checks
    case "$category" in
        releases)
            if ! check_changes_numbering "$json_file"; then
                has_warning=1
            fi
            if ! check_impact_consistency "$json_file"; then
                has_error=1
            fi
            ;;
        checks)
            if ! check_ipa_reference_pattern "$json_file"; then
                has_warning=1
            fi
            ;;
        handlers)
            if ! check_class_name_pattern "$json_file"; then
                has_warning=1
            fi
            if ! check_module_pattern "$json_file"; then
                has_warning=1
            fi
            ;;
        adapters)
            if ! check_class_name_pattern "$json_file"; then
                has_warning=1
            fi
            if ! check_module_pattern "$json_file"; then
                has_warning=1
            fi
            ;;
        libraries|tools)
            if ! check_module_pattern "$json_file"; then
                has_warning=1
            fi
            ;;
        overview)
            if ! check_version_consistency "$json_file" "$knowledge_dir"; then
                has_error=1
            fi
            ;;
    esac

    # Return: 0=success, 1=error, 2=warning
    if [ $has_error -eq 1 ]; then
        return 1
    elif [ $has_warning -eq 1 ]; then
        return 2
    else
        return 0
    fi
}
