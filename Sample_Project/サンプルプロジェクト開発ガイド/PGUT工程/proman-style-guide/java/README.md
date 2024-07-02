# Javaスタイルガイド

ここではJavaをコーディングする際に役立つコーディング規約やコードフォーマッター、静的検査のガイドを提供しています。
バグを防ぎ、品質の高いコードを書くため、これらの資材を活用してください。

Javaコーディング規約を参照して、より良いコードを書くようにしてください。

- [Javaコーディング規約](./java-style-guide.md)

Javaコーディング規約ではコードフォーマッターや静的検査で機械的にチェックできるものは予め実施しておくことを前提としています。

コードフォーマッターでフォーマットしてください。
すぐに使えるコードフォーマッターを提供しています。

- [Javaコードフォーマッター](./code-formatter.md)

Checkstyleで機械的に検出できる規約違反を解消してください。

- [Checkstyleガイド](./staticanalysis/checkstyle/README.md)

SpotBugsで明らかに問題のあるコードや、後々問題が発生しそうなコードを排除してください。

- [SpotBugsガイド](./staticanalysis/spotbugs/README.md)

必要に応じて使用不許可APIチェックツールで使用するAPIを制限してください。

- [使用不許可APIチェックツール](./staticanalysis/unpublished-api/README.md)

ArchUnitのテストで検出できるアーキテクチャ違反は解消してください。

- [ArchUnitガイド](./staticanalysis/archunit/README.md)