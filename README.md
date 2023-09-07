# 概要
HTTPレスポンス処理の実装となります。
ハンター情報の取得・登録・更新・削除の処理を、テキストファイルの読み書きで実装しました。
# 実行手順・動作確認
## 1. 準備
プログラムの処理対象であるテキストファイル【hunterInfo.txt】は、プロジェクトのルートディレクトリ直下に格納されています。プログラムがテキストファイルを読み込む際、絶対パスで指定をしているため、実行環境によってパスの指定を変更する必要があります。 \
そのためテキストファイルを読み込む処理を行っている【HunterRegistrationController.java】のコードを環境に合わせて適切なパスに変更します。
| ![テキストファイル絶対パス](https://github.com/Kito-Java2307/Java-7thTheme/assets/141001192/483939d8-5221-48a7-84f4-da2b581cf883)|
|:--|

プロジェクトを自環境に配置した後、【hunterInfo.txt】の絶対パスをnew File()の引数として書き換えてください。

## 2. 実行
   1. **プログラム起動** \
      IDEで格納したプロジェクトを開き、【ManageHunterApplication】を実行します。 \
      IDEのログに「Started ManageHunterApplication in ~」が表示されることを確認します。
      | ![プログラム起動確認](https://github.com/Kito-Java2307/Java-7thTheme/assets/141001192/9b21d34d-c00c-482b-b8aa-1fcb3fa21de1)|
      |:--|
   3. **Postman起動** \
      Postmanを起動し、HTTPリクエスト実行画面を表示させます。
      
   5. **HTTPリクエスト実行**
      1. *GET* \
         テキストファイル【hunterInfo.txt】に記載されている全てのHunter情報を読み込み、JSON形式でレスポンスします。 \
         ■URL：http://localhost:8080/hunters \
         ■cURLコマンド \
         curl --location 'http://localhost:8080/hunters' \
         ■レスポンス \
         | ![GET](https://github.com/Kito-Java2307/Java-7thTheme/assets/141001192/dfc935bf-ee66-4dd5-8d41-14a4bd0f3bb4)|
         |:--|
      3. *GET（パス変数追加）* \
         指定IDのHunter情報を、JSON形式でレスポンスします。 \
         ■URL：http://localhost:8080/hunters/1 \
         ■cURLコマンド \
         curl --location 'http://localhost:8080/hunters/1' \
         ■レスポンス \
         | ![GETpath](https://github.com/Kito-Java2307/Java-7thTheme/assets/141001192/b41be989-2e4b-4d4d-86a9-66285d38f134)|
         |:--|
      5. *POST* \
         新規のHunter情報を登録し、成功メッセージをJSON形式でレスポンスします。 \
         ■URL：http://localhost:8080/hunters \
         ■cURLコマンド \
         curl --location 'http://localhost:8080/hunters' \
         --header 'Content-Type: application/json' \
         --data '{
         "name": "Kurapika",
         "sex": "M",
         "age": "18"
         }' \
         ■レスポンス \
         | ![POST01](https://github.com/Kito-Java2307/Java-7thTheme/assets/141001192/c96fad12-8e62-4759-83ea-ef1daea19c83)|
         |:--|
         | ![POST02](https://github.com/Kito-Java2307/Java-7thTheme/assets/141001192/2f31c231-d5fa-4763-9c0b-17269056f17e)|
         |:--|
         ＜反映の確認＞ \
         |![POST03](https://github.com/Kito-Java2307/Java-7thTheme/assets/141001192/c7d5b91d-f8d0-491c-8468-59cc8e4278df)|
         |:--|
      7. *PATCH* \
         指定IDのHunter情報を更新し、成功メッセージをJSON形式でレスポンスします。 \
         ■URL：http://localhost:8080/hunters/3 \
         ■cURLコマンド \
         curl --location --request PATCH 'http://localhost:8080/hunters/3' \
         --header 'Content-Type: application/json' \
         --data '{
          "name": "Leorio",
          "sex": "M",
          "age": "20"
         }' \
         ■レスポンス \
         | ![PATCH01](https://github.com/Kito-Java2307/Java-7thTheme/assets/141001192/fea94c16-a037-4f6e-a226-8c65b2bead49)|
         |:--|
         ＜反映の確認＞ \
         | ![PATCH02](https://github.com/Kito-Java2307/Java-7thTheme/assets/141001192/9089443f-45f8-47a0-bbc1-04d36b399be7)|
         |:--|
      9. *DELETE* \
         指定IDのHunter情報を削除し、成功メッセージをJSON形式でレスポンスします。
         ■URL：http://localhost:8080/hunters/1 \
         ■cURLコマンド \
         curl --location --request DELETE 'http://localhost:8080/hunters/3' \
         ■レスポンス \
         | ![DELETE01](https://github.com/Kito-Java2307/Java-7thTheme/assets/141001192/88a0c6dc-77fc-4dfe-95c6-27fdffb93d28)|
         ＜反映の確認＞ \
         | ![DELETE02](https://github.com/Kito-Java2307/Java-7thTheme/assets/141001192/fcbc8021-ad36-48cd-8bf8-68e16ae881ef)|
         |:--|


         
         
 
