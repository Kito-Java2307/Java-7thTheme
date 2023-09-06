package com.kito.managehunter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HunterRegistrationController {

    // テキストの場所を格納したFile作成
    File hunterInfoFile = new File("D:\\RaiseTech\\Java\\7th\\managehunter/hunterInfo.txt");


    // /huntersへのGETリクエストに対し、Hunter一覧を返す
    @GetMapping("/hunters")
    public Map<String, Object> getHunters() {

        // テキストを行ごとに格納するList
        List<String> lineList = new ArrayList<>();

        // Listにテキストの内容を格納
        try {
            // テキストを読み込むための用意
            FileReader fileReader = new FileReader(hunterInfoFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Listにテキストファイルを行ごとに格納
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lineList.add(line);
            }

            // ファイルを閉じる
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (lineList.size() != 0) {

            // HunterIDとHunterインスタンスをJSON形式で返すためのMap
            Map<String, Object> hunterMap = new HashMap<>();

            // Mapに全てのHunterIDとHunterインスタンスを格納
            for (int i = 0; i < lineList.size() - 3; i += 4) {
                hunterMap.put(lineList.get(i),
                        new Hunter(lineList.get(i + 1), lineList.get(i + 2),
                                Integer.parseInt(lineList.get(i + 3))));
            }

            // キーと値を返す（HunterIDとHunterインスタンス）
            return hunterMap;

        } else {

            // 指定ID存在しない旨を返す
            Map<String, Object> errorMessage = new HashMap<>();
            return Map.of("Error Massage", "No hunters are currently registered.");
        }
    }


    // /hunters/{hunterid}へのGETリクエストに対し、IDに紐づいたHunterを返す
    @GetMapping("/hunters/{hunterid}")
    public Map<String, Object> getHunterById(@PathVariable("hunterid") int hunterId) {

        // テキストを行ごとに格納するList
        List<String> lineList = new ArrayList<>();
        // IDのみ格納するIDList
        List<String> idList = new ArrayList<>();
        // HunterIDとHunterインスタンスをJSON形式で返すためのMap
        Map<String, Object> hunterMap = new HashMap<>();

        // テキストを全行Listに格納する
        try {
            // テキストを読み込むための用意
            FileReader fileReader = new FileReader(hunterInfoFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Listにテキストファイルを行ごとに格納
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lineList.add(line);
            }

            // ファイルを閉じる
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // 全行ListからIDのみ抽出しIDListに格納
        for (int i = 0; i < lineList.size() - 3; i += 4) {
            idList.add(lineList.get(i));
        }

        // IDListに指定IDがあるか検索
        boolean judge = false;
        int index = 0;
        for (int i = 0; i < idList.size(); i++) {

            // 指定IDが存在した場合
            if (idList.get(i).equals(Integer.toString(hunterId))) {
                // 判定をTrueにする
                judge = true;
                // 全行Listでのインデックス作成
                index = i * 4;
                break;
            }
        }

        // IdListに指定IDがある場合取得処理を実行、ない場合はエラーメッセージを返答
        // 指定IDがある場合
        if (judge) {

            // Mapに指定IDとHunterインスタンスを格納
            hunterMap.put(
                    lineList.get(index),
                    new Hunter(
                            lineList.get(index + 1),
                            lineList.get(index + 2),
                            Integer.parseInt(lineList.get(index + 3))
                    )
            );

            // キーと値を返す（HunterIDとHunterインスタンス）
            return hunterMap;

            // 指定IDがない場合
        } else {

            // 指定ID存在しない旨を返す
            Map<String, Object> errorMessage = new HashMap<>();
            return Map.of("Error Massage", "The specified ID does not exist.");
        }
    }


    // /huntersへのPOSTリクエストを受け、Hunterを新規登録する
    @PostMapping("/hunters")
    public ResponseEntity<Map<String, String>> create(@RequestBody @Validated CreateForm form, BindingResult result) {

        // 不正入力時の返答
        // Validationでチェックをしエラーがあった場合
        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            // エラー箇所のエラーメッセージを取り出しListへ格納
            for (FieldError error : result.getFieldErrors()) {
                errors.add(error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(Map.of("Error Message", errors.toString()));
        }

        // テキストを行ごとに格納するList
        List<String> lineList = new ArrayList<>();

        // テキストの末尾にHunterを登録
        try {

            // テキストを読み込むための用意
            FileReader fileReader = new FileReader(hunterInfoFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Listにテキストファイルを行ごとに格納
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lineList.add(line);
            }

            // テキストに書き込むための用意
            FileWriter fileWriter = new FileWriter(hunterInfoFile, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // HunterIDとリクエストボディの値をテキスト末尾に追記
            if (lineList.size() != 0) {
                bufferedWriter.newLine();
            }
            bufferedWriter.write(Long.toString(lineList.size() / 4 + 1));
            bufferedWriter.newLine();
            bufferedWriter.write(form.getName());
            bufferedWriter.newLine();
            bufferedWriter.write(form.getSex());
            bufferedWriter.newLine();
            bufferedWriter.write(Integer.toString(form.getAge()));
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // 登録されたHunter情報へのアクセス先を生成
        int hunterId = lineList.size() / 4 + 1;
        URI url = UriComponentsBuilder.fromUriString("http://localhost:8080")
                .path("/hunters/" + hunterId)
                .build()
                .toUri();

        // 登録成功結果、アクセス先、メッセージを返す
        return ResponseEntity.created(url).body(Map.of(
                "Message", "Hunter Registration Success. Your HunterID is [" + hunterId + "]."));
    }


    // /huntersへのPATCHリクエストを受け、既存Hunterを更新する
    @PatchMapping("/hunters/{hunterid}")
    public ResponseEntity<Map<String, String>> update(@PathVariable("hunterid") int hunterId,
                                                      @RequestBody @Validated UpdateForm form, BindingResult result) {
        // 不正入力時の返答
        // Validationでチェックをしエラーがあった場合
        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            // エラー箇所のエラーメッセージを取り出しListへ格納
            for (FieldError error : result.getFieldErrors()) {
                errors.add(error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(Map.of("Error Message", errors.toString()));
        }

        // テキストを行ごとに格納する全行List
        List<String> lineList = new ArrayList<>();
        // IDのみ格納するIDList
        List<String> idList = new ArrayList<>();

        // テキストを全行Listに格納する
        try {

            // テキストを読み込むための用意
            FileReader fileReader = new FileReader(hunterInfoFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Listにテキストファイルを行ごとに格納
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lineList.add(line);
            }

            // ファイルを閉じる
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // 全行ListからIDのみ抽出しIDListに格納
        for (int i = 0; i < lineList.size() - 3; i += 4) {
            idList.add(lineList.get(i));
        }

        // IDListに指定IDがあるか検索
        boolean judge = false;
        int index = 0;
        for (int i = 0; i < idList.size(); i++) {
            // 指定IDが存在した場合
            if (idList.get(i).equals(Integer.toString(hunterId))) {
                // 判定をTrueにする
                judge = true;
                // 全行Listでのインデックス作成
                index = i * 4;
                break;
            }
        }

        // IdListに指定IDがある場合更新処理を実行、ない場合はエラーメッセージを返答
        // 指定IDがある場合
        if (judge) {

            // List内の指定IDのHunter情報を更新
            lineList.set(index + 1, form.getName());
            lineList.set(index + 2, form.getSex());
            lineList.set(index + 3, Integer.toString(form.getAge()));

            // テキストを書き換える
            try {

                // テキストに書き込むための用意
                FileWriter fileWriter = new FileWriter(hunterInfoFile);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                // 全行を書き直し
                for (int i = 0; i < lineList.size(); i++) {
                    bufferedWriter.write(lineList.get(i));
                    if (i != lineList.size() - 1) {
                        bufferedWriter.newLine();
                    }
                }

                // ファイルを閉じる
                bufferedWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            // 更新成功結果、メッセージを返す
            return ResponseEntity.ok(Map.of("Message", "HunterID[" + hunterId + "] Update Success."));

            // 指定IDがない場合
        } else {

            // 指定ID存在しない旨を返す
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("Error Massage", "The specified ID does not exist."));
        }
    }

    @DeleteMapping("/hunters/{hunterid}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable("hunterid") int hunterId) {

        // テキストを行ごとに格納する全行List
        List<String> lineList = new ArrayList<>();
        // IDのみ格納するIDList
        List<String> idList = new ArrayList<>();

        // テキストを全行Listに格納する
        try {

            // テキストを読み込むための用意
            FileReader fileReader = new FileReader(hunterInfoFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // 全行Listにテキストファイルを行ごとに格納
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lineList.add(line);
            }

            // ファイルを閉じる
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // 全行ListからIDのみ抽出しIDListに格納
        for (int i = 0; i < lineList.size() - 3; i += 4) {
            idList.add(lineList.get(i));
        }

        // IDListに指定IDがあるか検索
        boolean judge = false;
        int index = 0;
        for (int i = 0; i < idList.size(); i++) {
            // 指定IDが存在した場合
            if (idList.get(i).equals(Integer.toString(hunterId))) {
                // 判定をTrueにする
                judge = true;
                // 全行Listでのインデックス設定
                index = i * 4;
                break;
            }
        }

        // IdListに指定IDがある場合削除処理を実行、ない場合はエラーメッセージを返答
        // 指定IDがある場合
        if (judge) {

            // List内の指定IDのHunter情報を削除
            for (int i = 0; i < 4; i++) {
                lineList.remove(index);
            }

            try {

                // テキストに書き込むための用意
                FileWriter fileWriter = new FileWriter(hunterInfoFile);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                // 全行を書き直し
                for (int i = 0; i < lineList.size(); i++) {
                    bufferedWriter.write(lineList.get(i));
                    if (i != lineList.size() - 1) {
                        bufferedWriter.newLine();
                    }
                }

                // ファイルを閉じる
                bufferedWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            // 削除成功結果、メッセージを返す
            return ResponseEntity.ok(Map.of("Message", "HunterID[" + hunterId + "] Delete Success."));

            // 指定IDがない場合
        } else {

            // 指定ID存在しない旨を返す
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("Error Massage", "The specified ID does not exist."));
        }
    }
}
