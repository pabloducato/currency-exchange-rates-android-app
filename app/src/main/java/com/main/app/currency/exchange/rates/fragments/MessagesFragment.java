package com.main.app.currency.exchange.rates.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.main.app.currency.exchange.rates.R;
import com.main.app.currency.exchange.rates.adapters.InternetExceptionAdapter;
import com.main.app.currency.exchange.rates.adapters.MessageAdapter;
import com.main.app.currency.exchange.rates.items.InternetExceptionItem;
import com.main.app.currency.exchange.rates.items.MessageItem;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MessagesFragment extends Fragment implements MessageAdapter.OnItemClickListener {

    private final ArrayList<MessageItem> messageList = new ArrayList<>();
    private final ArrayList<InternetExceptionItem> internetExceptionList = new ArrayList<>();
    public List<String> list = new ArrayList<>();
    SimpleDateFormat time_format = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_notifications, container, false);
        RecyclerView recyclerView = fragmentView.findViewById(R.id.message_recycler_view);
        AnimationDrawable animationDrawable = (AnimationDrawable) recyclerView.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

        if (messageList != null || messageList.size() > 0) {
            messageList.clear();
        }

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                parseXml(list);
            } catch (IOException | SAXException | ParserConfigurationException e) {
                e.printStackTrace();
            }
        }

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        if (isNetworkAvailable(Objects.requireNonNull(getContext()))) {
            try {
                for (int i = 0; i < list.size(); ) {
                    int posT = list.get(i + 3).indexOf("T");
                    int posDot = list.get(i + 3).indexOf(".");
                    int posImgEnd = list.get(i + 2).indexOf(">");
                    messageList.add(new MessageItem(R.drawable.ic_baseline_fiber_new_24, list.get(i + 3).substring(0, posT), list.get(i + 3).substring(posT + 1, posDot), "Copyright: TVN", list.get(i), list.get(i + 2).substring(posImgEnd + 6, list.get(i + 2).length() - 10), list.get(i + 1)));
                    i = i + 4;
                }
                MessageAdapter adapter = new MessageAdapter(messageList, this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            internetExceptionList.add(new InternetExceptionItem(R.drawable.ic_baseline_info_24, formatter.format(Calendar.getInstance().getTime()), time_format.format(Calendar.getInstance().getTime()), "Brak połączenia z Internetem"));
            InternetExceptionAdapter adapter = new InternetExceptionAdapter(internetExceptionList, this::onItemClick);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }

        super.onCreate(savedInstanceState);
        return fragmentView;
    }

    public void parseXml(List<String> list) throws IOException, SAXException, ParserConfigurationException {

        String messageURLString = "https://tvn24.pl/najnowsze.xml";
        URL url = new URL(messageURLString);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(url.openStream());

        document.getDocumentElement().normalize();

        Element root = document.getDocumentElement();
        System.out.println(root.getNodeName());

        NodeList nList = document.getElementsByTagName("item");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                list.add(eElement.getElementsByTagName("title").item(0).getTextContent());
                list.add(eElement.getElementsByTagName("link").item(0).getTextContent());
                list.add(eElement.getElementsByTagName("description").item(0).getTextContent());
                list.add(eElement.getElementsByTagName("pubDate").item(0).getTextContent());
            }
        }

    }

    @Override
    public void onItemClick(int position) {
        if (list.size() > 0) {
            String url = list.get((position * 4) + 1);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

}
