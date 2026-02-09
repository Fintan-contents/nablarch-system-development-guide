#!/bin/bash

input=$(cat)

cwd=$(echo "$input" | jq -r '.workspace.current_dir // .cwd')
model=$(echo "$input" | jq -r '.model.display_name // empty')
context_used=$(echo "$input" | jq -r '.context_window.used_percentage // empty')

# Git branch
git_branch=""
if git -C "$cwd" rev-parse --git-dir > /dev/null 2>&1; then
  branch=$(git -C "$cwd" -c core.useBuiltinFSMonitor=false -c core.fsmonitor=false branch --show-current 2>/dev/null)
  [ -n "$branch" ] && git_branch=" ($branch)"
fi

# Context info
context_info=""
[ -n "$context_used" ] && context_info=" [ctx: ${context_used}%]"

# Model info
model_info=""
[ -n "$model" ] && model_info=" [${model}]"

# Output
cwd_name=$(basename "$cwd")
printf "\033[01;34m%s\033[00m%s%s%s" "$cwd_name" "$git_branch" "$model_info" "$context_info"
