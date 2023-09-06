# 概要
HTTPレスポンス処理の実装となります。
ハンター情報の取得・登録・更新・削除の処理を、テキストファイルの読み書きで実装しました。
# 実行手順・動作確認
1. 準備</br>
プログラムの処理対象であるテキストファイル【hunterInfo.txt】は、プロジェクトのルートディレクトリ直下に格納されています。プログラムがテキストファイルを読み込む際、絶対パスで指定をしているため、実行環境によってパスの指定を変更する必要があります。</br>
そのためテキストファイルを読み込む処理を行っている【HunterRegistrationController.java】のコードを環境に合わせて適切なパスに変更します。
![テキストファイル絶対パス](https://github.com/Kito-Java2307/Java-7thTheme/assets/141001192/11793857-735b-4f6b-bf68-8e35df72e508)</br>
プロジェクトを自環境に配置した後、【hunterInfo.txt】の絶対パスをnew File()の引数として書き換えてください。

2. 実行
   1. プログラム起動
      IDEで格納したプロジェクトを開き、【ManageHunterApplication】を実行します。</br>
      IDEのログに「Started ManageHunterApplication in ~」が表示されることを確認します。
      ![プログラム起動確認](https://github.com/Kito-Java2307/Java-7thTheme/assets/141001192/9b21d34d-c00c-482b-b8aa-1fcb3fa21de1)
   2. Postman起動
      Postmanを起動し、HTTPリクエスト実行画面を表示させます。
   3. HTTPリクエスト実行
      1. GET</br>
         ■URL：http://localhost:8080/hunters</br>
         ■レスポンス
         
         
 
