package alexeutuan.myapplication;

import android.os.AsyncTask;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

class RequestTask extends AsyncTask<String, String, String> {

    String arr;
    String response;
    public RequestTask(String arr) {
        this.arr = arr;
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            String url = "http://192.168.0.5:8888/servlet";
            //создаем запрос на сервер
            DefaultHttpClient hc = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            //он у нас будет посылать post запрос
            HttpPost postMethod = new HttpPost(url);
            postMethod.setEntity(new StringEntity(arr));
            response = hc.execute(postMethod, res);
        } catch (Exception e) {
            System.out.println("Excep=" + e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}