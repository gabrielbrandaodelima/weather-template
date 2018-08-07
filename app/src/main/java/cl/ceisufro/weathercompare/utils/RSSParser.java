//package cl.ceisufro.weathercompare.utils;
//
//import com.prof.rssparser.Article;
//import com.prof.rssparser.Parser;
//
//import java.util.ArrayList;
//
//import cl.ceisufro.weathercompare.network.OnYahooSuccessCallback;
//
//public class RSSParser {
//    public RSSParser() {
//
//    }
//
//
//    public void getRSSFeed(String url, final OnYahooSuccessCallback callback) {
//
////url of RSS feed
////        String urlString = "http://www.androidcentral.com/feed";
//        Parser parser = new Parser();
//        parser.execute(url);
//        parser.onFinish(new Parser.OnTaskCompleted() {
//
//            @Override
//            public void onTaskCompleted(ArrayList<Article> list) {
//                //what to do when the parsing is done
//                //the Array List contains all article's data. For example you can use it for your adapter.
//                callback.onSuccess(list);
//
//            }
//
//            @Override
//            public void onError() {
//                //what to do in case of error
//            }
//        });
//
//
//    }
//}
