# Formの実装とvalidationについて
## 実装の大まかな流れ
lombokの`@Data`アノテーションでgetter, setterを用意したFormクラスを
ControllerのmavにFormをObjectとして追加する。

viewでは、`th:object`で指定して、
form内では`th:feild`で各要素を取り出す。

POST先のControllerのinputにFormのクラスを指定すると、
クラスにformで入力した値を入れて関数内で使用することができる。

## validationについて
validationはFormクラス内に`javax.validation.constraints.*`の
annotationで指定できる。

`@NotNull`や`@NotBlank`は良く使いそうで、
違いは以下のドキュメントに詳しく説明されている。
https://qiita.com/NagaokaKenichi/items/67a63c91a7db8717fc7d

Intの値については、`@Range`や`@Min`,`@Max`などが使える。
以下がannotationの種類については詳しくされている。
https://b1tblog.com/2020/02/02/spring-boot-4/
