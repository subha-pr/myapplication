package com.evolet.myapplication.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.evolet.myapplication.Activities.ItemActivity;
import com.evolet.myapplication.Activities.NursingActivity;
import com.evolet.myapplication.Adapters.AdminItemAdapter;
import com.evolet.myapplication.Adapters.RecyclerItemClickListener;
import com.evolet.myapplication.Adapters.UserAdapter;
import com.evolet.myapplication.Items.Materials;
import com.evolet.myapplication.Items.ProductItem;
import com.evolet.myapplication.Adapters.ProductsAdapter;
import com.evolet.myapplication.R;
import com.evolet.myapplication.db.SQLiteHandler;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CareFragment extends Fragment {


    DatabaseReference ProductsRef;
    FirebaseDatabase mFirebaseDatabase;
    private RecyclerView recyclerView;
    ListView mListView;
    RecyclerView.LayoutManager layoutManager;
    List<Materials> materialsList;
    ProductItem productItem;
    SQLiteHandler mDbHandler;
    ArrayList<ProductItem> appList;
    public CareFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_care, container, false);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String key = database.getReference("Products").getRef().getKey();
        ProductsRef = FirebaseDatabase.getInstance().getReference( ).child("Products");
        Log.d("Medical Frag","Random Key:  "+key);
        Toast.makeText(getActivity(), key, Toast.LENGTH_SHORT).show();
        materialsList=new ArrayList<>();
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        ProductsRef=mFirebaseDatabase.getReference();


        try {
            mDbHandler=new SQLiteHandler(getActivity());
            appList=new ArrayList<>();
            appList=mDbHandler.getDetails("Nursing");
            productItem=appList.get(0);
            String xName,xPrice,xCategory,xUnit;
            xName=productItem.getProdName();
            xPrice=productItem.getProdPrice();
            xCategory=productItem.getProdCategory();
            xUnit=productItem.getUnit();
            final ArrayList<Object> headerList = new ArrayList<>();
            headerList.add("Product");
            headerList.addAll(appList);

        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }


        RecyclerView medicinesListView = view.findViewById(R.id.recycler_menu);
        AdminItemAdapter adapter = new AdminItemAdapter(getActivity(), appList,mDbHandler);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        medicinesListView.setLayoutManager(llm);
        medicinesListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        adapter.notifyDataSetChanged();
        medicinesListView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView ,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                       /* Intent i=new Intent(getActivity(),ItemActivity.class);
                        String name = appList.get(position).getProdName();
                        Toast.makeText(getActivity(), ""+name, Toast.LENGTH_SHORT).show();
                        i.putExtra("ProdName",name);
                        startActivity(i);*/
                       Intent i = new Intent(getActivity(), NursingActivity.class);
                       String name = appList.get(position).getProdName();
                       String price  = appList.get(position).getProdPrice();
                       Bundle b = new Bundle();
                       b.putString("ProdName",name);
                       b.putString("ProdPrice",price);
                       i.putExtras(b);
                       startActivity(i);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                }));
        return view;

    }

/*public void showData(DataSnapshot dataSnapshot){
        try{


    for (DataSnapshot mDatta:dataSnapshot.getChildren()) {
        Materials materials = new Materials();
        materials.setMaterial_name(mDatta.child("Products").getValue(Materials.class).getMaterial_name());
        materials.setMaterial_category(mDatta.child("Products").getValue(Materials.class).getMaterial_category());
        materials.setMaterial_price(mDatta.child("Products").getValue(Materials.class).getMaterial_price());

        Log.d("TAG", materials.getMaterial_name());
        Log.d("TAG", materials.getMaterial_category());
        Log.d("TAG", materials.getMaterial_price());
        ProductsAdapter mAdapter=new ProductsAdapter(getActivity(), materialsList);
        mListView.setAdapter(mAdapter);
    }
        }catch (Exception e){
            e.printStackTrace();
        }
}*/

   /* @Override
    public void onStart() {
        super.onStart();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference yourRef = rootRef.child("Products");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                materialsList.clear();

      //  for (DataSnapshot mDa: dataSnapshot.getChildren()){
            for (DataSnapshot mDatta:dataSnapshot.getChildren()){
              //  Materials materials=mDatta.getValue(Materials.class);

               // System.out.println(materials);

                //Log.d("TAG", materials.getMaterial_name());
                //materialsList.add(materials);

                Materials materials=new Materials();
                materials.setMaterial_name(mDatta.child("Products").getValue(Materials.class).getMaterial_name());
                materials.setMaterial_category(mDatta.child("Products").getValue(Materials.class).getMaterial_category());
                materials.setMaterial_price(mDatta.child("Products").getValue(Materials.class).getMaterial_price());

                Log.d("TAG", materials.getMaterial_name());
                Log.d("TAG", materials.getMaterial_category());
                Log.d("TAG", materials.getMaterial_price());
                ProductsAdapter mAdapter=new ProductsAdapter(getActivity(), materialsList);
                mListView.setAdapter(mAdapter);
        }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        yourRef.addListenerForSingleValueEvent(eventListener);
    }
*/
/* @Override
    public void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Materials> options =
                new FirebaseRecyclerOptions.Builder<Materials>()
                        .setQuery(ProductsRef, Materials.class)
                        .build();


        FirebaseRecyclerAdapter<Materials, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Materials, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Materials model)
                    {

                        holder.txtProductName.setText(model.getMaterial_name());
                        holder.txtProductDescription.setText(model.getMaterial_id());
                        holder.txtProductPrice.setText("Price = " + model.getMaterial_price() + "Rs");
                        holder.textProductCategory.setText( model.getMaterial_category());
                        Picasso.get().load(model.getmImage()).into(holder.imageView);
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }*/
}
