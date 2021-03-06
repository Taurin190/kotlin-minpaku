# About Kotlin
JetBrains社が開発した静的型付け[^1]のオブジェクトしこうプログラミング言語。

Java言語をもっと簡潔・安全になるように改良した産業利用向け汎用言語で、
Java仮想マシン上でJava言語で書かれたプログラムと同程度に早く動作する。

同様にJava仮想マシン上で動くスクリプト言語のGroovyやScalaの簡易記法（糖衣構文[^2]）を
採用したり、ジェネリクス[^3]の構文などでC#の影響を受けている部分もある。

[^1]:静的型付け言語は、開発者が使用する際に予め型を決めて使用する言語。PHPなど型を決めていなくても動的に処理する動的型付け言語の対義的な言語。スクリプト言語でもTypeScriptなどは静的型付けの言語として広く使われている。
[^2]:読みやすさのために複雑で分かりにくい書き方と同じ意味になる書き方が出来るもののこと。
[^3]:\<\>記号で囲まれたデータ型名を付けて汎用的なクラスやメソッドを作る機能のこと。

出典: https://ja.wikipedia.org/wiki/Kotlin

## 基本的な構文
出典: https://dogwood008.github.io/kotlin-web-site-ja/docs/reference/basic-syntax.html
### 関数の定義
Javaの関数との違いは宣言が`fun`であること、変数、関数の型の宣言が:区切りで後に来る。
```Kotlin
fun sum(a: Int, b: Int): Int {
  return a + b
}
```

値を返さないJavaで言うところの`void`は`Unit`で表現し、
関数の返り値などは省略できる。

また、以下のような単純な関数として書くことが出来る。
```Kotlin
fun sum(a: Int, b: Int) = a + b
```

### ローカル変数の定義
基本的な使い方は以下のようになる。
```Kotlin
val a: Int = 1
val c: Int 
```

ただし、明確な値に関しては型推論が行われる。
```Kotlin
val b = 1   // 明示的に定義していないが`Int`型であると推論される
```

### 文字列テンプレート
ダブルクオートで囲まれた文字列ないのドル記号（$）から始まる部分がテンプレートとして認識される。
```Kotlin
val i = 10
val s = "i = $i" // "i = 10"と評価される

val s = "abc"
val str = "$s.length is ${s.length}" // "abc.length is 3"と評価される
```
$文字を使用する場合は以下のように書く。（バックスラッシュでのエスケープがサポートされていない。）
```Kotlin
val price = """
${'$'}9.99
"""
```

### 条件式
条件式はJavaと比べて{}が省略されている。
```Kotlin
fun max(a: Int, b: Int): Int {
  if (a > b)
    return a
  else
    return b
}
```
さらに三項演算子ような省略が出来る。
```Kotlin
if (a > b) a else b
```

### NULL可能値
Kotlinでは通常の変数はNULLを許容せずコンパイルエラーが発生する。
NULL可能値を開発者に明示的に宣言させることで、
NullPointerExceptionでのException発生と全てにNULLチェックを書くような苦痛を軽減してくれる。

Kotlinでは何も宣言しなければnull許容しないので、
以下のような記述ではコンパイルエラーになる。
```Kotlin
var a: String = "abc"
a = null // compilation error
```

null許容にする場合には、`String?`のような書き方をする。
```Kotlin
var b: String? = "abc"
b = null // ok
```

この例の`b`で以下のような記述をした場合にはエラーが発生する可能性がある。
コンパイラがエラーを出す。
```Kotlin
val l = b.length // error: variable 'b' can be null
```

そのため、記述は以下のように行う。
```Kotlin
// パターン1
val l = if (b != null) b.length else -1

// パターン2
if (b != null && b.length > 0)
    print("String of length ${b.length}")
else
    print("Empty string")

// パターン3
b?.length // bがnullの場合はnullを返す
```
パターン3の場合は、このlengthを受け取る方もnull許容になる必要があり、
null許容が連鎖してしまうため基本的にパターン１,２のように扱うのが良いかもしれない。

#### Elvis Operator
先ほどの例にあったように以下のような記述はエルビス演算子[^4]で次のように記述することができる。
```Kotlin
val l: Int = if (b != null) b.length else -1

// With Elvis Operator
val l = b?.length ?: -1
```
[^4]:"?:"がリーゼントヘアーのエルビスプレスリーに見えることから付けられた名前。三項演算子の二項目が無いものと同様。

#### Exceptionを出すNULL非許容
"!!"演算子を付けて宣言を行うことで、NullPointerExceptionを出す変数の宣言が行える。
NPEをあえて使用したい場合には以下のように記述する。
```Kotlin
val l = b!!.length()
```
#### Safe Casts
以下のように`as?`のように付けると型のキャストが行える。
キャストに失敗した場合には、nullが返るため以下の例では`aInt`は`Int?`となる。
```Kotlin
val aInt: Int? = a as? Int
```
詳細に書かれたブログ: http://kotlindive.hatenablog.jp/entry/2018/02/01/192212

### 型チェック
型チェックには`is`を使う。チェック後は明示的に変数を使用できる。
```Kotlin
fun getStringLength(obj: Any): Int? {
  if (obj is String) {
    // `obj` はこのブランチ内では自動的に`String`へキャストされる
    return obj.length
  }

  // `obj` は型チェックが行われたブランチ外では、まだ`Any`型である
  return null
}
```

### 範囲の指定
`in`演算子を使う。範囲の指定は`..`で行う。
```Kotlin
if (x in 1..100)
  print("OK")
```

### イディオム
詳しいドキュメント: https://dogwood008.github.io/kotlin-web-site-ja/docs/reference/idioms.html

