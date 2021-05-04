# Springbootでのデータベースとスキーマ管理について
## Hibernate ORMについて
`spring.jpa.hibernate.ddl-auto=update`
という設定をapplication.propertiesに追加するとアプリケーション起動時に
テーブルを作成してくれる。

Entityに`@table`で対応するテーブルを指定する。

細かいスキーマの設定が慣れないと面倒かもしれない。

参考: https://qiita.com/rubytomato@github/items/6781e91bcf7ab8a68c40

springのpluginを使うと自動で作られるスキーマのDDLを表示してくれる。
## 他の方法
参考: https://spring.pleiades.io/spring-boot/docs/2.0.0.M4/reference/html/howto-database-initialization.html