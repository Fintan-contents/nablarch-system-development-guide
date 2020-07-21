-- project_id列で使用するシーケンスについて、GSPで投入したデータと値が重複しないように値を設定する。
ALTER SEQUENCE client.client_client_id_seq RESTART WITH 100
