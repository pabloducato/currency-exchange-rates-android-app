package com.android.app.currency.exchange.rates.fragments;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app.currency.exchange.rates.R;
import com.android.app.currency.exchange.rates.adapters.AuthorAdapter;
import com.android.app.currency.exchange.rates.items.AuthorItem;

import java.util.ArrayList;

public class InfoFragment extends Fragment implements AuthorAdapter.OnItemClickListener {

    private final ArrayList<AuthorItem> authorList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_info, container, false);
        RecyclerView recyclerView = fragmentView.findViewById(R.id.info_recycler_view);
        AnimationDrawable animationDrawable = (AnimationDrawable) recyclerView.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

        if (authorList != null || authorList.size() > 0) {
            authorList.clear();
        }

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        authorList.add(new AuthorItem(R.drawable.ic_baseline_policy_24, "Informacje prawne",
                "Kursy walut i informacje © 2020 Paweł Zbigniew Kocan. Wszelkie prawa zastrzeżone.", "https://www.linkedin.com/in/paweł-k-597919142"));
        authorList.add(new AuthorItem(R.drawable.ic_baseline_policy_24, "Wspomóż autora darowizną",
                "Jeżeli chcesz wesprzeć autora w działaniach związanych z utrzymaniem i promocją serwisu, a także pomóc w realizacji przyszłych projektów, możesz zrobić to tutaj: https://tipply.pl/u/pabloducato.", "https://tipply.pl/u/pabloducato"));
        authorList.add(new AuthorItem(R.drawable.ic_baseline_policy_24, "Prawa autorskie", "Serwis api.nbp.pl udostępnia publiczne Web API umożliwiające " +
                "klientom HTTP wykonywanie zapytań na zbiorach danych publikowanych przez serwis NBP.PL.", "https://api.nbp.pl/"));
        authorList.add(new AuthorItem(R.drawable.ic_baseline_policy_24, "Deklaracja dostępności", "Narodowy Bank Polski zobowiązuje się" +
                "zapewnić dostępność swojej strony internetowej zgodnie z przepisami ustawy z dnia 4 " +
                "kwietnia 2019 r. o dostępności cyfrowej stron internetowych i aplikacji mobilnych " +
                "podmiotów publicznych (Dz.U. 2019 poz. 848). Oświadczenie w sprawie dostępności ma " +
                "zastosowanie do strony internetowej NBP Web API (api.nbp.pl).", "https://www.nbp.pl/dostepnosc/deklaracja-api.aspx"));
        authorList.add(new AuthorItem(R.drawable.ic_baseline_policy_24, "Prawa autorskie", "Serwis tvn24.pl udostępnia publiczne dane umożliwiające " +
                "klientom HTTP wykonywanie zapytań na zbiorach danych publikowanych przez serwis tvn24.pl. Wszelkie prawa autorskie dotyczące prezentowanych treści należą do TVN S.A.", "https://tvn24.pl/najnowsze.xml"));
        authorList.add(new AuthorItem(R.drawable.ic_baseline_policy_24, "CoinCap.io, CoinCap Apps - Privacy Policy", "Privacy Policy", "https://static.coincap.io/documents/privacy_policy.pdf"));
        authorList.add(new AuthorItem(R.drawable.ic_baseline_policy_24, "CoinCap.io, CoinCap Apps - Terms of Service", "Terms of Service", "https://static.coincap.io/documents/terms_of_service.pdf"));
        authorList.add(new AuthorItem(R.drawable.ic_baseline_policy_24, "Copyright (C) 2012-2018 ZXing authors, Journey Mobile", "Licensed under the Apache License, " +
                "Version 2.0 (the \"License\"); " +
                "you may not use this file except in compliance with the License. " +
                "You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0" +
                "Unless required by applicable law or agreed to in writing, software " +
                "distributed under the License is distributed on an \"AS IS\" BASIS, " +
                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. " +
                "See the License for the specific language governing permissions and " +
                "limitations under the License.", "https://www.apache.org/licenses/LICENSE-2.0"));

        AuthorAdapter adapter = new AuthorAdapter(authorList, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        super.onCreate(savedInstanceState);
        return fragmentView;
    }

    @Override
    public void onItemClick(int position) {

    }
}
