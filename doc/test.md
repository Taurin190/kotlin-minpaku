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

WebFluxは、リアクティブ、ノンブロッキング型のフレームワーク。
Nettyを含めた実装になっている。
https://qiita.com/yut_arrows/items/5c56c81b89b1e8ae4bf4
そのようなWebFluxを使用するときは、`org.springframework.mock.http.server.reactive`を使用する。

## Controllerのテストについて
以下のリポジトリに詳しくまとめられていた。
https://github.com/mechero/spring-boot-testing-strategies

Controllerのテストはスコープによって4つに分けられる。
`MockitoJUnitRunner`で走らせるテストはRepositoryなど外部との接続をMockに置き換え比較的狭いスコープで実行される。

HTTP Filter, Controller AdviceなどはTestによって作られる。
filterはリクエスト毎に呼び出されてレスポンスでヘッダーに特定の値をいれるなどで使えそう。
AdviceはExceptionを吐いた時に処理を書いていくもの。

次のようにセットアップして、
`MockMvcBuilders.standaloneSetup(superHeroController)`
`superHeroController`が`@InjectMocks`でMockを入れる対象としてアノテーションで宣言されている。

２つ目は`@SpringBootTest`を用いてmockMvcでテストする方法で、
クラスに付けている`@WebMvcTest(SuperHeroController.class)`でターゲットを宣言している。
SpringBootでContext等を用意させているので、１つ目の方法のようにsetupでビルドするステップはなく、
Autowiredで組み立てられている。
WebMvcTestで指定しているため直接的にControllerが呼び出されているっぽく、
これが次の３つ目のケースとの違いになるのかと思う。

３つ目のケースでは、`@WebMvcTest`のアノテーションが無く、
変わりに、`@SpringBootTest`と`@AutoConfigureMockMvc`が付けられている。
図表の説明によると、これによりWebServerのSpringBeanコンポーネントを介してControllerが呼び出されているようだ。

４つ目のケースでは、`@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)`というように、
ランダムのPORTを起動して、restTemplateでのテストを行う。