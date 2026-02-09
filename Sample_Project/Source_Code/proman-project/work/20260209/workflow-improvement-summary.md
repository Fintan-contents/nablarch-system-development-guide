# ワークフロー改善提案まとめ

## 改善対象ファイル

1. `.claude/skills/nabledge-6/SKILL.md`
2. `.claude/skills/nabledge-6/workflows/keyword-search.md`
3. `.claude/skills/nabledge-6/workflows/section-judgement.md`

---

## 問題点の整理

### 現在の状況
- Skillツール呼び出し → SKILL.mdの説明が表示されるだけ
- ワークフローは**自動実行されない**
- Claudeが手動でファイルを読んで検索する必要がある
- しかし、**具体的な実行手順が不明確**

### 根本原因
1. **「説明文書」と「実行指示」の混同**
   - "The skill automatically searches..." → 実際は手動実行が必要
   - "This workflow does X" → 誰がどうやって実行するかが不明

2. **ツール呼び出しの不明確さ**
   - jq, grepのコマンド例はあるが、どのツール（Read/Bash/Grep）で実行するか不明
   - 具体的なツール呼び出し形式が示されていない

3. **判断基準の曖昧さ**
   - "High relevance" の定義はあるが、Claude が実際にどう判断すべきか不明
   - 質問形式ではないため、自己判断しにくい

---

## 改善の方針

### 1. SKILL.md: 実行責任の明確化

**追加内容**:
- 「How Claude Code should execute this skill」セクションを追加
- 5ステップの実行フロー（Acknowledge → keyword-search → section-judgement → Answer → Handle missing）
- 使用するツールの明記（Read, Bash+jq, Grep）
- ツール呼び出し数の目安（10-20 calls）

**変更内容**:
- "The skill automatically" → "When Claude Code executes this skill, it will"
- 自動実行の誤解を排除

### 2. keyword-search.md: 実行可能な指示に変換

**各ステップに追加**:
- **Tool**: 使用するツール名
- **Action you must take**: 具体的な実行指示
- **Your checklist**: 完了確認項目
- **Tool call example**: 具体的なツール呼び出し形式

**改善例**:
```markdown
【改善前】
Read the knowledge index:
```bash
cat knowledge/index.toon
```

【改善後】
**Tool**: Read tool
**Action you must take**:
Use Read tool to load knowledge/index.toon

**Your checklist**:
- [ ] Read tool called successfully
- [ ] 93 entries loaded
```

### 3. section-judgement.md: 質問ベースの判断基準

**判断プロセスを質問形式に変換**:
```markdown
【改善前】
High relevance (2 points):
- The section directly answers the user's request
- Reading this section enables implementation

【改善後】
Questions for High relevance:
1. Does this section directly address the user's primary goal? (Yes/No)
2. Can the user implement by reading this section alone? (Yes/No)
3. Does this section contain specific, actionable information? (Yes/No)

If ALL 3 are "Yes" → Assign relevance = 2 (High)
```

**利点**:
- Claudeが自問自答できる明確な基準
- 機械的に判断可能な形式
- 判断の一貫性向上

---

## 改善による効果

### Before（現状）
```
ユーザー: "UniversalDaoでページングは？"
↓
Claude: Skillツール呼び出し → SKILL.md表示
↓
Claude: （どうすればいいか分からない）
↓
Claude: 手動でindex.toon読む → universal-dao.json全体読む
↓
Claude: 回答作成（ワークフロー未使用）
```

### After（改善後）
```
ユーザー: "UniversalDaoでページングは？"
↓
Claude: Skillツール呼び出し → SKILL.md表示
↓
Claude: "keyword-searchワークフローを実行します"
↓
Step 1: キーワード抽出 ["ページング", "DAO", "per", "page"]
Step 2: Read index.toon → 93エントリ読み込み
Step 3: キーワードマッチング → universal-dao.json (5 hints matched)
Step 4: 候補ファイル選択 → 上位10ファイル
Step 5: Bash+jq .index → pagingセクション発見
Step 6: section-judgementへ移行
  - Bash+jq .sections.paging → 内容読み込み
  - 判断質問: 直接回答する？Yes → High (2)
Step 7: High relevance sections使って回答
↓
Claude: 構造化された回答（pagingセクションの情報のみ使用）
```

---

## 実装の優先順位

### Priority 1: SKILL.md（最重要）

**理由**: Claude が最初に読むファイル。ここで実行指示がなければ何も始まらない

**必須の追加内容**:
- 「How Claude Code should execute this skill」セクション
- 5ステップの実行フロー
- ツールの明記（Read, Bash+jq, Grep）

**実装難易度**: 低（セクション追加のみ）
**効果**: 大（実行の起点となる）

### Priority 2: keyword-search.md

**理由**: 実際の検索処理の中核。具体的な手順がないと実行不可能

**必須の変更**:
- 各ステップに「Tool」「Action」「Checklist」を追加
- Bash+jq の具体的なコマンド例
- Read/Grepツールの使い方

**実装難易度**: 中（既存ステップの書き直し）
**効果**: 大（検索精度の向上）

### Priority 3: section-judgement.md

**理由**: 判断基準の明確化。質問形式にすることで一貫性向上

**必須の変更**:
- 判断基準を質問形式に変換
- Bash+jqの具体的な使用例
- エラーハンドリングの明確化

**実装難易度**: 中（判断基準の再構成）
**効果**: 中（判断の一貫性向上）

---

## 段階的実装プラン

### Phase 1: 最小限の改善（即座に実装可能）

**対象**: SKILL.mdのみ
**内容**: 「How Claude Code should execute this skill」セクションを追加
**時間**: 5-10分
**効果**: Claudeに実行指示を与える

### Phase 2: 実行可能化（1-2日）

**対象**: SKILL.md + keyword-search.md
**内容**:
- SKILL.md: 完全な実行フローを記載
- keyword-search.md: 各ステップをツール呼び出し形式に変更

**時間**: 1-2時間
**効果**: ワークフローが実際に実行可能になる

### Phase 3: 完全版（1週間）

**対象**: 3ファイルすべて
**内容**:
- すべての改善案を適用
- 実際のテスト実行で検証
- 必要に応じて微調整

**時間**: 2-3時間
**効果**: 完全に機能するワークフローシステム

---

## 検証方法

### 改善前後の比較テスト

**テストケース**:
1. "UniversalDaoでページングはどうしたらよい？"
2. "トランザクション管理ハンドラのエラー対処"
3. "バッチでファイルを読み込みたい"

**測定指標**:
- ツール呼び出し回数
- 取得したセクション数
- 回答精度（知識ファイルのみ使用しているか）
- トークン使用量

### 成功基準

- [ ] Claudeが自発的にワークフローを実行する
- [ ] keyword-search → section-judgementの流れが機能する
- [ ] 回答が知識ファイルの内容のみに基づいている
- [ ] トークン使用量が10,000-15,000程度（目標値）
- [ ] 10-20回のツール呼び出しで完了する

---

## 詳細ドキュメント

改善案の詳細は以下のファイルを参照：

1. `skill-md-improvement.md` - SKILL.mdの改善案
2. `keyword-search-improvement-full.md` - keyword-search.mdの完全改善案
3. `section-judgement-improvement.md` - section-judgement.mdの完全改善案

---

## 次のステップ

### 推奨アクション

1. **今すぐ**: SKILL.mdに「How Claude Code should execute this skill」セクションを追加
2. **今日中**: keyword-search.mdの主要ステップをツール呼び出し形式に変更
3. **今週中**: section-judgement.mdを質問ベースに変更
4. **来週**: 実際のテスト実行で検証・調整

### 期待される結果

- ワークフローが実際に機能する
- Claude が自律的に知識検索を実行できる
- トークン効率が向上する（5,000-10,000 tokens/query）
- 回答精度が向上する（知識ファイルのみ使用）
