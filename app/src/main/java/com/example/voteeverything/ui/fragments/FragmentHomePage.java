import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voteeverything.R;
import com.example.voteeverything.model.Post;
import com.example.voteeverything.ui.adapters.PostAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentHomePage extends Fragment {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private List<Post> itemList;
    private PostAdapter adapter;

    public FragmentHomePage() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        // Arama çubuğu ve RecyclerView'ı bul
        searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.recyclerViewHome);

        // RecyclerView için layoutManager ve adapter ayarla
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        itemList = new ArrayList<>(); // Eğer verileriniz varsa onları buraya ekle
        adapter = new PostAdapter(itemList); // YourAdapter yerine kendi adapter sınıfınızı kullanın
        recyclerView.setAdapter(adapter);

        // Arama çubuğuna dinleyici ekle
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Arama metninde değişiklik olduğunda filtreleme işlemini gerçekleştir
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        return view;
    }
}
