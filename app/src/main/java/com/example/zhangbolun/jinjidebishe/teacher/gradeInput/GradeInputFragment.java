package com.example.zhangbolun.jinjidebishe.teacher.gradeInput;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhangbolun.jinjidebishe.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class GradeInputFragment extends Fragment {
    @BindView(R.id.fragment_grade_input_text)TextView textView;

    private String TAG="批量导入excel";
    
    private List<File> fileListReturn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_grade_input, container, false);
        ButterKnife.bind(this,view);
        fileListReturn=new ArrayList<>();
        new ImportDataFromExcel().importExcelData();
        fileListReturn=getFilesFromDictionary("/storage/emulated/0/家校通下载的文件/");
        Log.d(TAG, fileListReturn.toString());
        
        return view;
    }

    public class ImportDataFromExcel {
        //将excel文件导入到内存中
        private List<ExcelItem> datas; //需要一个对应excel表中列的类，同recyclerView的Item类一样  2333
        public String importExcelData(){
            datas = new ArrayList<>();
            Workbook workbook = null;
            String fileName ="1304103test.xls";
            try {
                Log.d(TAG, Environment.getExternalStorageDirectory()+"/家校通下载的文件/"+fileName);
                workbook = Workbook.getWorkbook(new File(Environment.getExternalStorageDirectory()+"/家校通下载的文件/"+fileName));
                Sheet sheet = workbook.getSheet(0);
                int rows = sheet.getRows();
                int columns = sheet.getColumns();
                //遍历excel文件的每行每列
                for (int i=0; i < rows ;i++){
                    //遍历行
                    List<String> li = new ArrayList<>();
                    for (int j = 0 ; j < columns ; j++ ){
                        Cell cell = sheet.getCell(j,i);
                        String result = cell.getContents();
                        if (i!=0){
                            li.add(result);
                        }
                    }
                    if (li.size()>0){
                        datas.add(new ExcelItem(li.get(0),li.get(1)));
                    }
                    li=null;
                }
                Gson gson = new Gson();
                Log.d(TAG, gson.toJson(datas));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (BiffException e) {
                e.printStackTrace();
            }
            return "error";
        }
    }


    /**
     * 读取文件
     * @param strFilePath
     * @return
     */
    public String readTxtFile(String strFilePath) {
        String path = strFilePath;
        StringBuilder builder = new StringBuilder();
        //打开文件
        File file = new File(path);
        //如果path是传递过来的参数，可以做一个非目录的判断
        if (file.isDirectory()) {
            Log.d("TestFile", "The File doesn't not exist.");
        } else {
            try {
                InputStream instream = new FileInputStream(file);
                if (instream != null) {
                    InputStreamReader inputreader = new InputStreamReader(instream);
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    String line;
                    //分行读取
                    while ((line = buffreader.readLine()) != null) {
                        builder.append(line + "\n");
                    }
                    instream.close();
                }
            } catch (java.io.FileNotFoundException e) {
                Log.d("TestFile", "The File doesn't not exist.");
            } catch (IOException e) {
                Log.d("TestFile", e.getMessage());
            }
        }
        return builder.toString();
    }

    /**
     * 读取sd卡上指定后缀的所有文件
     * @param files 返回的所有文件
     * @param filePath 路径(可传入sd卡路径)
     * @param suffere 后缀名称 比如 .gif
     * @return
     */
    public List<File> getSuffixFile(List<File> files, String filePath, String suffere) {

        File f = new File(filePath);

        if (!f.exists()) {
            return null;
        }

        File[] subFiles = f.listFiles();
        for (File subFile : subFiles) {
            if(subFile.isFile() && subFile.getName().endsWith(suffere)){
                files.add(subFile);
            } else if(subFile.isDirectory()){
                getSuffixFile(files, subFile.getAbsolutePath(), suffere);
            } else{
                //非指定目录文件 不做处理
            }

        }

        return files;
    }

    /**
     * 读取sd卡上指定目录下所有文件
     * created by zhangbolun 2017/5/21 00:10
     * @param sPath 要查找的目录文件路径
     * @return fileList 返回的文件名列表
     */
    public List<File> getFilesFromDictionary(String sPath){
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        List<File> fileList=new ArrayList<>();
//        Log.d(TAG, sPath);
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
//        Log.d(TAG, sPath);
        File dirFile = new File(sPath);
//        Log.d(TAG, dirFile.toString());
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            Toast.makeText(getContext(), "文件夹不存在", Toast.LENGTH_SHORT).show();
            return null;
        }
        File[] subFiles=dirFile.listFiles();
        for(File subFile:subFiles){
            Log.d(TAG, subFile.getName());
            fileList.add(subFile);
        }
        return fileList;
    }

}
