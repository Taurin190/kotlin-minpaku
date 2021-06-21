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

## Testで使用するアノテーションについて
`@RunWith`について
https://qiita.com/key_sleep/items/7ff4a5ca036ec7adb3a5
SpringBootのテストを行う時にRunnerクラスの指定を行う事ができる。
`SpringRunner.class`はDIを行う時に使う。`@MockBean`で書いたものを勝手にDIで置き換えてくれる。
基本的なクラスに関してはこれを使うので良いのかなと思う。

`MockitoJUnitRunner.class`は`@Mock`でモックして手動で入れるのかな？

`@TestExecutionListeners`これもDBMockの時に使っていた。

`@ExtendWith`について
以下のブログ参照
https://www.m3tech.blog/entry/2018/12/20/junit5

もしかするとRunWithと一緒に使うものでないかもしれない。
今の設定では、junit:4.13.1を使用してて、kotlintest-runner-junit5の3.1.8なので変な状態かもしれない。
どうもkotlintest-runner-junit4, kotlintest-runner-junit5が検索に引っかかるので揃えた方が良いかもしれない。


## JUnit4とJUnit5の違い
どうもRunWith, assertThatはhaJUnit4の書き方っぽい。
2年前の記事で移行期間とあるので、知見はある程度溜まっていてJUnit5でも問題ないかと思う。
https://gist.github.com/wreulicke/762cbad857f44780de63daa7ecbf85c1

assertThatはSpringbootのマニュアルでも記載されているので使えるのかな。

以下、より詳しいドキュメント
https://spring.pleiades.io/spring-framework/docs/current/reference/html/testing.html#testing-introduction
