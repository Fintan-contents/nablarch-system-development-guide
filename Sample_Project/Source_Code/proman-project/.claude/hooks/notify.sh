#!/bin/bash
# Claude Code notification script for WSL
# Sends Windows desktop notifications via PowerShell Toast API
#
# Usage: echo '{"hook_event_name":"Stop",...}' | notify.sh
#
# Hook input (stdin JSON) fields used:
#   hook_event_name  - "Stop" or "Notification"
#   notification_type - "permission_prompt" or "idle_prompt" (Notification only)
#   message          - descriptive message (Notification only)
#   cwd              - working directory
set -euo pipefail

readonly MAX_MESSAGE_LENGTH=200

# Escape string for XML
escape_for_xml() {
  printf '%s' "$1" | sed \
    -e 's/\&/\&amp;/g' \
    -e 's/</\&lt;/g' \
    -e 's/>/\&gt;/g' \
    -e 's/"/\&quot;/g' \
    -e "s/'/\\&apos;/g"
}

# Parse a JSON string value by key (no external dependencies)
parse_json_value() {
  local json="$1"
  local key="$2"
  # Match "key": "value" — handles simple string values
  local value
  value=$(printf '%s' "$json" | sed -n 's/.*"'"$key"'"[[:space:]]*:[[:space:]]*"\([^"]*\)".*/\1/p')
  printf '%s' "$value"
}

# Get repository and branch info
get_repo_info() {
  local dir="$1"
  local repo_name branch_name

  repo_name=$(basename -s .git "$(git -C "$dir" remote get-url origin 2>/dev/null)" 2>/dev/null || basename "$dir")
  branch_name=$(git -C "$dir" rev-parse --abbrev-ref HEAD 2>/dev/null || echo "unknown")

  printf '%s' "${repo_name}:${branch_name}"
}

# Truncate message if it exceeds the maximum length
truncate_message() {
  local msg="$1"
  if [[ ${#msg} -gt $MAX_MESSAGE_LENGTH ]]; then
    msg="${msg:0:$MAX_MESSAGE_LENGTH}..."
  fi
  printf '%s' "$msg"
}

# Send Windows toast notification via PowerShell
send_notification() {
  local title="$1"
  local message="$2"

  if ! command -v powershell.exe &>/dev/null; then
    return 0
  fi

  title=$(escape_for_xml "$title")
  message=$(escape_for_xml "$message")

  powershell.exe -NoProfile -NonInteractive -Command "
\$xml = @'
<toast>
  <visual>
    <binding template=\"ToastGeneric\">
      <text>$title</text>
      <text>$message</text>
    </binding>
  </visual>
  <audio silent=\"true\" />
</toast>
'@
\$XmlDocument = [Windows.Data.Xml.Dom.XmlDocument,Windows.Data.Xml.Dom,ContentType=WindowsRuntime]::New()
\$XmlDocument.LoadXml(\$xml)
\$AppId = 'Microsoft.WindowsTerminal_8wekyb3d8bbwe!App'
[Windows.UI.Notifications.ToastNotificationManager,Windows.UI.Notifications,ContentType=WindowsRuntime]::CreateToastNotifier(\$AppId).Show(\$XmlDocument)
" 2>/dev/null || true
}

notify_main() {
  local input
  input=$(cat)

  local hook_event_name notification_type message cwd
  hook_event_name=$(parse_json_value "$input" "hook_event_name")
  notification_type=$(parse_json_value "$input" "notification_type")
  message=$(parse_json_value "$input" "message")
  cwd=$(parse_json_value "$input" "cwd")

  local dir="${cwd:-.}"
  local repo_info
  repo_info=$(get_repo_info "$dir")

  local title body
  case "$hook_event_name" in
    Stop)
      title="Claude Code - Complete"
      body="[$repo_info] Agent finished"
      ;;
    Notification)
      local detail="${message:-Waiting for your input}"
      title="Claude Code - Action Required"
      body="[$repo_info] $detail"
      ;;
    *)
      title="Claude Code"
      body="[$repo_info] $hook_event_name"
      ;;
  esac

  body=$(truncate_message "$body")
  send_notification "$title" "$body"
}

if [[ "${BASH_SOURCE[0]}" == "$0" ]]; then
  notify_main
fi
