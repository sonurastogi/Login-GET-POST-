package com.example.mysql;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SigninActivity extends AsyncTask<String, String, String> {
    private TextView statusField,roleField;
    private Context context;
    private int byGetOrPost = 0;

   public SigninActivity(Context context,TextView statusField,TextView roleField,int flag){
       this.context = context;
       this.statusField = statusField;
       this.roleField = roleField;
       byGetOrPost = flag;
   }


    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Toast.makeText(this.context,values[0],Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... arg0) {
        if(byGetOrPost == 0){ //means by Get Method

            try{
                String username = (String)arg0[0];
                String password = (String)arg0[1];
               // Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
               // Toast.makeText(this,"Hi",Toast.LENGTH_SHORT ).show();
//publishProgress("I am in background class");
                String link = "http://webtgroup.online/Mysql/get.php?username="+username+"& password="+password;
                //String link = "http://myphpmysqlweb.hostei.com/login.php?username="+username+"& password="+password;
                publishProgress("I am in background class77777");
                URL url = new URL(link);

                //below code is actual code
               // HttpClient client = new DefaultHttpClient();
                //HttpGet request = new HttpGet();
                //request.setURI(new URI(link));
                //HttpResponse response = client.execute(request);
               // BufferedReader in = new BufferedReader(new
                 //       InputStreamReader(response.getEntity().getContent()));

               //below code is written by me just for exeriment
                URLConnection conn = url.openConnection();
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line="";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                in.close();
                return sb.toString();
            } catch(Exception e){

                publishProgress("I am in background class999999");
                //Toast.makeText(SigninActivity.this,"Exception raised",Toast.LENGTH_SHORT).show();
                return new String("Exception: " + e.getMessage());
            }
        }//End of if statement





        else{
            try{
                String username = (String)arg0[0];
                String password = (String)arg0[1];


                String link="http://webtgroup.online/Mysql/post.php";//only for name its get but actually functionality is of post method
                //String link="http://webtgroup.online/Elearningconn.php";
               // String link="http://myphpmysqlweb.hostei.com/loginpost.php";
                String data  = URLEncoder.encode("username", "UTF-8") + "=" +
                        URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                        URLEncoder.encode(password, "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write( data );
                wr.flush();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                return sb.toString();
            } catch(Exception e){
                return new String("Exception: " + e.getMessage());
               // return new String("Whats the problem");
            }
        }//End of else



   } //doinbackgroundbrace

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        this.statusField.setText("Login Successful");
        this.roleField.setText(result);
    }

    /*    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }*/
}//final brace

