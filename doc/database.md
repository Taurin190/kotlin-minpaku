# Springbootでのデータベースとスキーマ管理について
## Hibernate ORMについて
`spring.jpa.hibernate.ddl-auto=update`
という設定をapplication.propertiesに追加するとアプリケーション起動時に
テーブルを作成してくれる。

Entityに`@table`で対応するテーブルを指定する。

細かいスキーマの設定が慣れないと面倒かもしれない。

参考: https://qiita.com/rubytomato@github/items/6781e91bcf7ab8a68c40

springのpluginを使うと自動で作られるスキーマのDDLを表示してくれる。

## Tips
### unique key制約の付け方
`@Column(unique=true)`を付ける。

### 外部キーについて
試した範囲での記載。

`@OneToOne`や`@OneToMany`といったテーブルの関係を記載するアノテーションを付けることで、
テーブルの関係を表すことが出来る。

外部キーを持つテーブルに
`@JoinColumn(name = "user_id")`のようなアノテーションを付与すると
外部キー制約を付け、テーブル削除や対応する行を参照元を気にせずなどを行った時にエラーが出る。

このコードにあるProfileのように宿泊ユーザに情報を付与してもらい、
adminには必要ない場合では、user_idを外部キーにしながらprimary keyにしたい場合の方法は
探した範囲では見つかっていない。

暫定対応として、Profileテーブルにも別のidキーを設定した。

## 他の方法
参考: https://spring.pleiades.io/spring-boot/docs/2.0.0.M4/reference/html/howto-database-initialization.html