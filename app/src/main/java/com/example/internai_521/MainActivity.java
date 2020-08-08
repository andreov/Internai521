package com.example.internai_521;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private EditText mTextLog;
    private EditText mTextPass;
    private Button mBtnLog;
    private Button mBtnReg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initBtnLog();
        initBtnReg();
    }
    private void initViews(){
       mTextLog= findViewById(R.id.textLog);
       mTextPass= findViewById(R.id.textPassword);
       mBtnLog= findViewById(R.id.buttonOK);
       mBtnReg= findViewById(R.id.buttonReg);
    }

    private void initBtnLog(){
        mBtnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String log=readLog("log.txt");
                String pass=readLog("pass.txt");
                if(log==null||pass==null){
                    Toast.makeText(MainActivity.this, "Файл не существует", Toast.LENGTH_LONG).show();
                }else {
                    if(mTextLog.getText().toString().equals(log) && mTextPass.getText().toString().equals(pass)){
                        Toast.makeText(MainActivity.this, "Логин и пароль совпадают", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(MainActivity.this, "Вы ввели неправильный логин или пароль", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void initBtnReg(){
        mBtnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTextLog.getText().length()==0||mTextPass.getText().length()==0){
                    Toast.makeText(MainActivity.this, "Введите логин и пароль", Toast.LENGTH_LONG).show();
                }else {
                    writeReg("log.txt",mTextLog);
                    writeReg("pass.txt",mTextPass);
                    Toast.makeText(MainActivity.this, "Логин и пароль зарегистрированы", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void writeReg(String fileName,EditText logPass){
        // Создадим файл и откроем поток для записи данных
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }
        // Обеспечим переход символьных потока данных к байтовым потокам.
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        // Запишем текст в поток вывода данных, буферизуя символы так, чтобы обеспечить эффективную запись отдельных символов.
        BufferedWriter bw = new BufferedWriter(outputStreamWriter);
        // Осуществим запись данных
        try {
            bw.write(logPass.getText().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // закроем поток
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String readLog(String fileName){
        // Получим входные байты из файла которых нужно прочесть.
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = openFileInput(fileName);
        } catch (FileNotFoundException e) {

            //Toast.makeText(MainActivity.this, "Файл не найден", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return null;
        }
        // Декодируем байты в символы
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        // Читаем данные из потока ввода, буферизуя символы так, чтобы обеспечить эффективную запись отдельных символов.
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuilder output = new StringBuilder();
        //String line;
        try {
            output=output.append(reader.readLine());
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}