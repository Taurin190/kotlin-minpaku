# Minpaku Reservation System
![](https://github.com/Taurin190/kotlin-minpaku/workflows/App%20Test%20CI/badge.svg)
[![codecov](https://codecov.io/gh/Taurin190/kotlin-minpaku/branch/main/graph/badge.svg?token=MONFN7U85C)](https://codecov.io/gh/Taurin190/kotlin-minpaku)

Serverside Kotlin練習用のプロジェクト。
ホテル（民泊）の予約管理システム。

## 仕様
- 最大4泊5日まで利用可能 
- 1部屋に2人まで宿泊可能（ベット数2）
- 貸出単位は部屋毎
- 1予約者につき1部屋を割り当てられる
- 支払いは利用当日に前払い（1部屋1泊2000円）
- ベッド数以上の人数の利用は不可

### 必要な機能
- 予約登録
- 利用者の名前/住所/電話番号の管理
- これまでの月毎の売り上げ金額の確認

## Setup
```
# Start DB and Redis
$ docker-compose up -d
$ ./gradlew bootRun
```

## 技術情報
- Java Open JDK 11.0.2
- Kotlin 1.5.0-RC
- Gradle wrapper 6.8.3
- Springboot 2.5.0-SNAPSHOT
- MySQL 8.0
- Redis

## 素材
写真素材利用元

フリー素材ぱくたそ（www.pakutaso.com）

## メモ
### Book entityのDateをutilでなくsql.dateにした理由
この要件では、宿泊日の重複は起こらないため、Uniqueの制約を入れて、
Databaseで重複を防ぐ仕組みにしたかった。

utilのDateはcreated_datetimeなどでも使っているが、 時間まで入るため使わなかった。
また、Stringなどで管理するとIndexでBetweenが効かないという問題もあるため、
datetimeを使いつつ日付のみを管理する事にした。

### RepositoryとDatasourceについて
DDDでは、Domain層にRepositoryのInterfaceを設定して、データ層とドメイン層の依存関係を逆転させるが、
JpaRepositoryを拡張させたinterfaceも同様にSpring的にRepositoryとなっていてややこしくなるため、
DataSourceと異なる名前をつけて独自のドメインモデルを返り値を持つInterfaceを返すことを強制させる。