# Spring Boot with Kotlinのテストについて
以下、KotlinでのSpring Bootチュートリアル。

参考:https://spring.pleiades.io/guides/tutorials/spring-boot-kotlin/

Spring Bootでは、JUnit 5がデフォルトで適用される。

## JUnit 5の特徴
- キャメルケースだけでなく、バッククォートで囲み、より詳細にテスト名を書ける。
- コンストラクタとメソッドのパラメータをインジェクトできる。

以下はチュートリアルの例そのまま。
`restTemplate`を用いて、リクエストする形のテスト。
```Kotlin
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests(@Autowired val restTemplate: TestRestTemplate) {

  @Test
  fun `Assert blog page title, content and status code`() {
    val entity = restTemplate.getForEntity<String>("/")
    assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(entity.body).contains("<h1>Blog</h1>")
  }

}
```

## ユーザ認証について
E2Eテストでなくユーザ認証のテストと認可された状態のユーザのテストを行いたい。
以下の記事で詳しくまとめられているので参考にする。
https://qiita.com/a-pompom/items/c51869f80d55c4fe4e9c
DBのデータをmockに置き換える方法は以下にまとめられている。
https://qiita.com/kurukuruz/items/a3c09f818ae41e59f1b5

## MockMVCについて
以下ドキュメントで勉強する。
https://terasolunaorg.github.io/guideline/5.4.1.RELEASE/ja/UnitTest/ImplementsOfUnitTest/UsageOfLibraryForTest.html#usageoflibraryfortestmockmvcoverview

## Testのアノテーションについて
`@RunWith`について
https://qiita.com/key_sleep/items/7ff4a5ca036ec7adb3a5
SpringBootのテストを行う時にRunnerクラスの指定を行う事ができる。
`SpringRunner.class`はDIを行う時に使う。`@MockBean`で書いたものを勝手にDIで置き換えてくれる。
基本的なクラスに関してはこれを使うので良いのかなと思う。

`MockitoJUnitRunner.class`は`@Mock`でモックして手動で入れるのかな？

`@TestExecutionListeners`これもDBMockの時に使っていた。


