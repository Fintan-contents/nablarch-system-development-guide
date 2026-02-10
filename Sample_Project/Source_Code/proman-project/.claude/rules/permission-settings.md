# Permission Settings

Claude Code permissions are controlled by `.claude/settings.json`.

## Design Policy

| Category | Purpose | Description |
|------|------|------|
| **allow** | Permit frequently used commands/tools | Reduce user confirmation (ask) and improve development efficiency |
| **deny** | Restrict allow | Protect host, protect confidential information, prevent destructive actions |
| **ask** | Everything else | User decides individually |

## Protection Perspectives for deny (Restrictions on allow)

| Perspective | Rule | Reason |
|------|--------|------|
| **Exceptions to allow** | Prohibit `git clone http*`, `git clone git@*` within `git *`<br>Prohibit `rm -rf /`, `rm -rf /*` within `rm *` | Prevent downloading malicious code via network<br>Prevent system root deletion |
| **Confidential Information Protection** | Prohibit `.env`, `.aws`, `.ssh` in `Read/Edit/Write` | Prevent leakage of credentials, private keys, environment variables |
| **Host Protection** | Prohibit `/mnt/*`, `*.exe` in all allowed commands/tools | Prevent unauthorized access to Windows host areas from WSL environment |

※ Commands not in allow (`sudo`, `dd`, `curl`, `reboot`, etc.) automatically become ask and require user judgment

Refer to `.claude/settings.json` for specific settings.
